package com.example.lawuna;

import android.app.FragmentTransaction;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lawuna.ui.slideshow.SlideshowFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final String TAG = "MainActivity";
    ProgressDialog progress;
    protected String details = "";
    static String serverUrl = "http://192.168.43.2:5000/signInUser";
    private int wait_time = 0;
    private int SIGN_IN_STATUS = 0;
    private String phone_number;
    // The singleton HTTP client.
    private final OkHttpClient client = new OkHttpClient();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        // To apply the default app language instead of explicitly setting it.
        mAuth.useAppLanguage();
//        create an app token
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w("TAG", "getInstanceId failed", task.getException());
//                            return;
//                        }
//
//                        // Get new Instance ID token
//                        String token = task.getResult().getToken();
////                        onNewToken(token);
//
//                        // Log and toast
////                        String msg = token;
////                        Log.d("TOKEN_TAG", msg);
////                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
//                    }
//                });

        if (mAuth.getCurrentUser() != null) {
            Log.d(TAG, "onCreate: REDIRECT SCREEN");
            Intent intent = new Intent(this, HomeActivity.class);
//                            Clear the Stack and start new Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);

        }else{
            setContentView(R.layout.sign_in);
            TextView register = findViewById(R.id.register_link);
            TextView signInDesc = findViewById(R.id.signDesc);
            TextView disclaimer = findViewById(R.id.disclaimer);
            Button signIn = findViewById(R.id.signIn_button);
            signInDesc.setText(Html.fromHtml("<h2>Welcome to Lawuna</h2>\n"
                    +" Conserving and Rehabilitating the Environment"));
//            disclaimer.setText(Html.fromHtml("By using this application, you agree to our"
//                    +" <u>Terms of Service</u> and <u>Privacy Policy</u>"));

            String termsAndPolicies = "By using this application, you agree to our Terms of Service and Privacy Policy";
            SpannableString  ss = new SpannableString(termsAndPolicies);
            ClickableSpan clickableSpan1 = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent intent = new Intent(MainActivity.this, LegalActivity.class);
                    startActivity(intent);
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.DKGRAY);
//                    ds.setUnderlineText(false);
                }
            };
            ClickableSpan clickableSpan2 = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent intent = new Intent(MainActivity.this, LegalActivity.class);
                    startActivity(intent);

                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.DKGRAY);
//                    ds.setUnderlineText(false);
                }
            };

            ss.setSpan(clickableSpan1, 43,60, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(clickableSpan2, 65,79, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            disclaimer.setText(ss);
            disclaimer.setMovementMethod(LinkMovementMethod.getInstance());

            register.setMovementMethod(LinkMovementMethod.getInstance());
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
            });
            signIn.setMovementMethod(LinkMovementMethod.getInstance());
            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final EditText phoneNumber = findViewById(R.id.user_contact);
                    phone_number = phoneNumber.getText().toString().trim();
                    //Check if the entry fields are not null
                    if(phone_number.length() == 0){
//                  Success in Progress Dialog
                        Toast.makeText(getApplicationContext(),
                                "Please enter registered phone number", Toast.LENGTH_LONG).show();
                    }else{
//                    Create JSON Object for data transfer
                        JSONObject registrationData = new JSONObject();
                        try{
                            registrationData.put("phonenumber", phone_number);
                        }catch (JSONException err){
                            err.printStackTrace();
                        }

                        RequestBody body = RequestBody.create(registrationData.toString(), MediaType.parse("application/json; charset=utf-8"));

                        postRequest(serverUrl, body);
                    }

                }
            });
        }
    }


    public void selectFragment(View view){
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.lawuna_privacy:
            case R.id.lawuna_tc:
            case R.id.lawuna_content:
                //                        Create new Fragment
                fragment = new Fragment();
                replaceFragment(fragment);
                break;
        }
        replaceFragment(fragment);
    }

    //        Replace Fragment
    public void replaceFragment(Fragment someFragment) {
//        Transition
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        Replace whatever is in the Legal fragment view with this fragment and transition to the back stack
        transaction.replace(R.id.child_fragment, someFragment);
        transaction.addToBackStack(null);
//        Commit transition
        transaction.commit();
    }

    //    Post Network Request
    public void postRequest(String postUrl, RequestBody postBody){
        progress = ProgressDialog.show(this, "Signing in",
                "Please Wait...", true);
//        create HTTPClient object
//        client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
                .header("Accept","application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
                Log.d("FAIL", e.getMessage());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "Failed to connect to Server, Please try again", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response){
                try {
                    final String serverResponse = response.body().toString().trim();
                    String string = response.body().string().trim();
                    final String serverR = string.replace("\"", "");
                    final String signed = "success";
                    final String in_use = "active";
                    final String none = "empty";

                    Log.d("RESPONSE:==>",serverR);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (serverR.equals(signed)){
                                new Thread(new Runnable() {
                                    public void run() {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        progress.dismiss();
                                    }
                                }).start();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),
                                                "Logged In Successfully", Toast.LENGTH_LONG).show();
                                    }
                                },950);
                               setSignInStatus(1);

                                Intent intent = new Intent(MainActivity.this, SignInOtp.class);
                                intent.putExtra("phone_number", phone_number);
                                startActivity(intent);
//                                finish();

                            } else if (serverR.equals(in_use)) {
                                new Thread(new Runnable() {
                                    public void run() {
                                        try {
                                            Thread.sleep(700);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        progress.dismiss();
                                    }
                                }).start();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),
                                                "Phone Number already Logged In", Toast.LENGTH_LONG).show();
                                    }
                                },950);


                            } else if (serverR.equals(none)) {
                                Log.d("HERE:==>",serverResponse);
                                new Thread(new Runnable() {
                                    public void run() {
                                        try {
                                            Thread.sleep(700);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        progress.dismiss();
                                    }
                                }).start();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),
                                                "Please Create Account", Toast.LENGTH_LONG).show();
                                    }
                                },950);

                            }else {
                                progress.dismiss();
                                Toast.makeText(getApplicationContext(),
                                        "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    //    Setter for the Sign-in Flag
    public int setSignInStatus(int SIGN_IN_STATUS) {
        return SIGN_IN_STATUS;
    }

    //    Getter for the Sign_in Flag
    public int getSignInStatus(){
        return SIGN_IN_STATUS;
    }
    //    Getter for Singleton httpClient
    public OkHttpClient getHttpClient(){
        return client;
    }



}
