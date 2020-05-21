package com.example.lawuna;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PrivacyActivity extends LegalActivity {

    private TextView lawuna_privacy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_privacy_policy);
        //getting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);

        //setting the title
        toolbar.setTitle("Privacy");
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
        lawuna_privacy = findViewById(R.id.privacy_content);
        lawuna_privacy.setText("Privacy Policy");



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
