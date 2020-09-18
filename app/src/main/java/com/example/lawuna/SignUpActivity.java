package com.example.lawuna;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends MainActivity {
//    ViewFlipper flipper;
    private static String name = "";
    private static String emailAddress = "";
    private static String u_name;
    private static String u_email;
    private String username;
    private String email;
    ProgressBar progressBar;
    
    MainActivity HTTPClient = new MainActivity();

    private String serverUrl = "http://192.168.43.2:5000/email_check";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signup);
        Button register = findViewById(R.id.register_button);
        TextView signIn = findViewById(R.id.user_signIn);
        TextView signUpDesc = findViewById(R.id.signup_Desc);
        TextView disclaimer = findViewById(R.id.disclaimer);
        progressBar = findViewById(R.id.progressbar);

        final EditText fullName = findViewById(R.id.userName);
        final EditText userEmail = findViewById(R.id.userEmail);
        String termsAndConditions = getResources().getString(R.string.terms_and_conditions);
        String privacyPolicy = getResources().getString(R.string.privacy_policy);

//        disclaimer.setText(
//                String.format(
//                        getResources().getString(R.string.disclaimer_message),
//                        termsAndConditions,
//                        privacyPolicy)
//        );
        disclaimer.setMovementMethod(LinkMovementMethod.getInstance());
        Pattern termsAndConditionsMatcher = Pattern.compile(termsAndConditions);
        Linkify.addLinks(disclaimer, termsAndConditionsMatcher, "terms:");

        Pattern privacyPolicyMatcher = Pattern.compile(termsAndConditions);
        Linkify.addLinks(disclaimer, privacyPolicyMatcher, "privacy:");


//        String html = "<u>Terms of Service</u> ";
//        Spanned tos = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY);
//        Spanned pp = Html.fromHtml("<u>Privacy Policy</u> ");
        signUpDesc.setText(Html.fromHtml("<h2>Welcome to Lawuna</h2>\n"
                +"Conserving and Rehabilitating the Environment"));

        String termsAndPolicies = "By using this application, you agree to our Terms of Service and Privacy Policy";
        SpannableString ss = new SpannableString(termsAndPolicies);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(SignUpActivity.this, LegalActivity.class);
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
                Intent intent = new Intent(SignUpActivity.this, LegalActivity.class);
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

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                username = fullName.getText().toString().trim();
                email = userEmail.getText().toString().trim();

//                Username && Email Data
                u_name = setUsername(username);
                u_email = setEmail(email);
            //Check if the entry fields are not null
                if(username.length() == 0 || email.length() == 0){
                    Toast.makeText(getApplicationContext(),
                            "Enter All Credentials", Toast.LENGTH_LONG).show();
                }else{
//                    Check if the email exists
//                    Create JSON Object for data transfer
                    JSONObject registrationData = new JSONObject();
                    try{
                        registrationData.put("email", email);
                    }catch (JSONException err){
                        err.printStackTrace();
                    }
                    RequestBody body = RequestBody.create(registrationData.toString(), MediaType.parse("application/json; charset=utf-8"));

                    postRequest(serverUrl, body);
                }
            }
                                    });

        signIn.setMovementMethod(LinkMovementMethod.getInstance());
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
//
//        setContentView(R.layout.fragment_privacy_policy);
//        flipper = (ViewFlipper)findViewById(R.id.details);
//        Button privacy = findViewById(R.id.lawuna_privacy);
//        privacy.setOnClickListener((View.OnClickListener) new flipView());

    }

//    class flipView implements View.OnClickListener{
//        public void onClick(View view){
//            flipper.showNext();
//        }
//    };

    @Override
//    Post Network Request
    public void postRequest(String postUrl, RequestBody postBody){
        progress = ProgressDialog.show(this, "Registration",
                "Checking...", true);
        // The singleton HTTP client.
//        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
                .header("Accept","application/json")
                .build();
        OkHttpClient client = HTTPClient.getHttpClient();

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
                    String string = response.body().string().trim();
                    final String serverR = string.replace("\"", "");
                    final String available = "success";
                    final String email_used = "active";
                    Log.d("RESPONSE:==>", serverR);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (serverR.equals(available)){//Email Available
                                new Thread(new Runnable() {
                                    public void run() {
                                        try {
                                            Thread.sleep(950);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
//                                        progress.setMessage("Checking..."); // Setting Message
//                                        progress.setTitle("Registration"); // Setting Title
                                        progress.dismiss();
                                    }
                                }).start();
                                Intent intent = new Intent(SignUpActivity.this, VerifyContact.class);
                                intent.putExtra("u_name",u_name);
                                intent.putExtra("u_email",u_email);
                                startActivity(intent);
//                                finish();

                            }else if (serverR.equals(email_used)){ //Email Taken
                                new Thread(new Runnable() {
                                    public void run() {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
//                                        progress.setMessage("Checking..."); // Setting Message
//                                        progress.setTitle("Registration"); // Setting Title
                                        progress.dismiss();
                                    }
                                }).start();

//                                progressBar.setVisibility(View.VISIBLE);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),
                                                "Email already used.", Toast.LENGTH_LONG).show();
                                    }
                                },950);
//                                setSignInStatus(1);

                            }else {
                                new Thread(new Runnable() {
                                    public void run() {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        progress.setMessage("Checking..."); // Setting Message
                                        progress.setTitle("Registration"); // Setting Title
                                        progress.dismiss();
                                    }
                                }).start();
                                Toast.makeText(getApplicationContext(),
                                        "Something went wrong.", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    //    Compare Strings
    public static int stringCompare(String str1, String str2)
    {

        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);

        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int)str1.charAt(i);
            int str2_ch = (int)str2.charAt(i);

            if (str1_ch != str2_ch) {
//                return str1_ch - str2_ch;
                return 3;
            }
        }

        // Edge case for strings like
        // String 1="Geeks" and String 2="Geeksforgeeks"
        if (l1 != l2) {
//            return l1 - l2;
            return 1;
        }

        // If none of the above conditions is true,
        // it implies both the strings are equal
        else {
            return 0;
        }
    }

    //    Setter for the Full Names
    public String setUsername(String name) {
        return name;
    }

    //    Getter for the Full Names
    public static String getUsername(){
        return name;
    }

    //    Setter for the Full Names
    public String setEmail(String email) {
        return email;
    }

    //    Getter for the Full Names
    public static String getEmail(){
        return emailAddress;
    }

}