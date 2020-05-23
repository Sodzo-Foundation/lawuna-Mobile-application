package com.example.lawuna;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lawuna.ui.home.HomeFragment;
import com.example.lawuna.ui.legal.ContentFragment;
import com.example.lawuna.ui.legal.LegalFragment;
import com.example.lawuna.ui.legal.PrivacyFragment;
import com.example.lawuna.ui.slideshow.SlideshowFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class LegalActivity extends AppCompatActivity {

    private Button lawuna_privacy;
    private Button lawuna_content;
    private Button lawuna_tc;
    private LinearLayout view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.legal_layout);
        //getting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        view = (LinearLayout) findViewById(R.id.colorBg);

        view.setBackgroundColor(Color.rgb(237, 230, 231));

        //setting the title
        toolbar.setTitle("Legal");
        toolbar.setTitleTextColor(Color.WHITE);

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
//        Transition
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
////        Replace whatever is in the Legal fragment view with this fragment and transition to the back stack
//        transaction.replace(R.id.child_fragment, new SlideshowFragment());
//
//        transaction.addToBackStack(null);
////        Commit transition
//        transaction.commit();
        lawuna_privacy = findViewById(R.id.lawuna_privacy);
        lawuna_content = findViewById(R.id.lawuna_content);
        lawuna_tc = findViewById(R.id.lawuna_tc);
        lawuna_privacy.setText("Privacy Policy");
        lawuna_content.setText("Content");
        lawuna_tc.setText("Terms of Service");


        //        Text redirect to the Privacy screen
        lawuna_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setContentView(R.layout.fragment_privacy_policy);
                Intent intent = new Intent(LegalActivity.this, PrivacyActivity.class);
                startActivity(intent);

            }
        });

        lawuna_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setContentView(R.layout.fragment_tc_content);
                Intent intent = new Intent(LegalActivity.this, ContentActivity.class);
                startActivity(intent);
            }
        });

        lawuna_tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setContentView(R.layout.fragment_terms_of_service);
                Intent intent = new Intent(LegalActivity.this, TosActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


}
