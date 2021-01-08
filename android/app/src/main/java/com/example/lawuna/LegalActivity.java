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

package com.example.lawuna;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.example.lawuna.R.layout.sign_in;

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
                Intent intent = new Intent(LegalActivity.this, PrivacyActivity.class);
                startActivity(intent);

            }
        });

        lawuna_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LegalActivity.this, ContentActivity.class);
                startActivity(intent);
            }
        });

        lawuna_tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LegalActivity.this, TosActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case sign_in:
                onInitBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed(){
        Intent intent = new Intent(LegalActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    public void onInitBackPressed(){
        Intent intent = new Intent(LegalActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
