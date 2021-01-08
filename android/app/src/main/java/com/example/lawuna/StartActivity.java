package com.example.lawuna;

/*
 * Copyright 2020 The Lawuna Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.firebase.auth.FirebaseAuth;


public class StartActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final String TAG = "StartActivity";
    ProgressDialog progress;
    // Firebase user sign in status
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        if (mAuth.getCurrentUser() != null) {
            Log.d(TAG, "onCreate: REDIRECT SCREEN");
            Intent intent = new Intent(this, HomeActivity.class);
            // Clear the Stack and start new Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);

        }else{
            setContentView(R.layout.start_screen);
            TextView signInDesc = findViewById(R.id.signDesc);
            TextView disclaimer = findViewById(R.id.disclaimer);
            Button signUp = findViewById(R.id.signUp_button);
            Button signIn = findViewById(R.id.signIn_button);
            signInDesc.setText(Html.fromHtml("<h2>Welcome to Lawuna</h2>\n"
                    +" Conserving and Rehabilitating the Environment"));

            String termsAndPolicies = "By using this application, you agree to our Terms of Service and Privacy Policy";
            SpannableString  ss = new SpannableString(termsAndPolicies);
            ClickableSpan clickableSpan1 = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent intent = new Intent(StartActivity.this, LegalActivity.class);
                    startActivity(intent);
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.DKGRAY);
                }
            };
            ClickableSpan clickableSpan2 = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent intent = new Intent(StartActivity.this, LegalActivity.class);
                    startActivity(intent);

                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.DKGRAY);
                }
            };

            ss.setSpan(clickableSpan1, 43,60, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(clickableSpan2, 65,79, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            disclaimer.setText(ss);
            disclaimer.setMovementMethod(LinkMovementMethod.getInstance());

            signUp.setMovementMethod(LinkMovementMethod.getInstance());
            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StartActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
            });
            signIn.setMovementMethod(LinkMovementMethod.getInstance());
            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
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
                // Create new Fragment
                fragment = new Fragment();
                replaceFragment(fragment);
                break;
        }
        replaceFragment(fragment);
    }

    // Replace Fragment
    public void replaceFragment(Fragment someFragment) {
        // Transition
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // Replace whatever is in the Legal fragment view with this fragment and transition to the back stack
        transaction.replace(R.id.child_fragment, someFragment);
        transaction.addToBackStack(null);
        // Commit transition
        transaction.commit();
    }


}
