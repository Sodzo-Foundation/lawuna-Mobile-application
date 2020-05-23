package com.example.lawuna;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.lawuna.ui.home.HomeFragment;
import com.example.lawuna.ui.legal.ContentFragment;
import com.example.lawuna.ui.legal.LegalFragment;
import com.example.lawuna.ui.legal.PrivacyFragment;
import com.example.lawuna.ui.slideshow.SlideshowFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;
    String phone_number;
    Arrays userDetails;
    private static final String TAG = "HomeActivity";

    MainActivity signIn_status = new MainActivity();
//    int SIGN_IN_STATUS = signIn_status.getSignInStatus();
//    int SIGN_IN_STATUS = 1;
    private String statusUpdateUrl = "http://192.168.43.2:5000/updateStatus";


    public Arrays getUserDetails() {
        return userDetails;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        Intent intent = getIntent();
        phone_number = intent.getStringExtra("phone_number");
        Log.d(TAG, "onCreate: "+phone_number);


//        mViewPager = (ViewPager) findViewById(R.id.frag_view);
////        setup Pager
//        setupViewPager(mViewPager);
//        Access the fragment
//        Fragment fragment = new SlideshowFragment();
////        Transition
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
////        Replace whatever is in the Legal fragment view with this fragment and transition to the back stack
//        transaction.add(R.id.child_fragment, fragment);
////        transaction.setTransition(new LegalFragment().getId());
//
////        transaction.addToBackStack(null);
////        Commit transition
//        transaction.commit();


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



    }

    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SlideshowFragment(), "Legal");
        adapter.addFragment(new LegalFragment(), "Terms of Service");
        adapter.addFragment(new PrivacyFragment(), "Privacy Policy");
        adapter.addFragment(new ContentFragment(), "Content");
        viewPager.setAdapter(adapter);

    }

//    Access fragment
    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logOut:
//                if(phone_number.length() != 0){
                    signOut(phone_number);
//                }
                return true;
//            case R.id.help:
//                showHelp();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut(String phone_number) {

//                    Create JSON Object for data transfer
        JSONObject registrationData = new JSONObject();
        try{
            registrationData.put("phonenumber", phone_number);
        }catch (JSONException err){
            err.printStackTrace();
        }

        RequestBody body = RequestBody.create(registrationData.toString(), MediaType.parse("application/json; charset=utf-8"));

        postRequest(statusUpdateUrl, body);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //    Post Network Request
    public void postRequest(String postUrl, RequestBody postBody){
//        progress = ProgressDialog.show(this, "Registration",
//                "Checking...", true);
        final Request request = new Request.Builder()
                .url(postUrl)
                .put(postBody)
                .header("Accept","application/json")
                .build();

        OkHttpClient client = signIn_status.getHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
                Log.d("FAIL", e.getMessage());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Failed to connect to Server, Please try again", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            try {
                final String serverResponse = response.body().toString().trim();
                final String serverR = response.body().string();
                final String available = "success";
                Log.d("RESPONSE:==>", serverR);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
    //                            If the user data is registered
                            FirebaseAuth.getInstance().signOut();

                            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
//                            Clear the Stack and start new Activity
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);
    //                                finish();

                        Toast.makeText(HomeActivity.this, "Logged Out", Toast.LENGTH_LONG).show();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}
