package com.example.lawuna;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VerifyContact  extends SignUpActivity {
//    Get the username and email
    SignUpActivity registered = new SignUpActivity();
//    String username = registered.getUsername();
//    String email = registered.getEmail();
    private static final String TAG = "VerifyContact";
    private String registerUrl = "<api-route>";
    private String phone_number = "";
    private String username = "";
    private String email = "";

//    Country code Picker
    CountryCodePicker ccp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_verify);
        final EditText user_phone_number = findViewById(R.id.user_phoneNumber);
        Button verify_user = findViewById(R.id.verify_contact);
        TextView disclaimer = findViewById(R.id.disclaimer);
        TextView verifyDesc = findViewById(R.id.verifyDesc);
        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(user_phone_number);
        Log.d("CCP:",ccp.getSelectedCountryCode());


        String termsAndPolicies = "By using this application, you agree to our Terms of Service and Privacy Policy";
        SpannableString ss = new SpannableString(termsAndPolicies);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(VerifyContact.this, LegalActivity.class);
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
                Intent intent = new Intent(VerifyContact.this, LegalActivity.class);
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
        verifyDesc.setText(Html.fromHtml("<h2>Verify your number</h2> \n "
                +" Please enter your phone number to receive an\n (OTP) one time pin"));


        verify_user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                phone_number = ccp.getFullNumberWithPlus();
                Intent intent = getIntent();
                username = intent.getStringExtra("u_name");
                email = intent.getStringExtra("u_email");
                //Check if the entry fields are not null
                if(phone_number.length() == 0){
                    Toast.makeText(getApplicationContext(),
                            "Enter phone Credentials", Toast.LENGTH_LONG).show();
                }else{
//                    Create JSON Object for data transfer
                    JSONObject registrationData = new JSONObject();
                    try{
                        registrationData.put("phonenumber", phone_number);
                    }catch (JSONException err){
                        err.printStackTrace();
                    }

                    RequestBody body = RequestBody.create(registrationData.toString(), MediaType.parse("application/json; charset=utf-8"));

                    postRequest(registerUrl, body);
                }
            }
        });

    }

    @Override
    //    Post Network Request
    public void postRequest(String postUrl, RequestBody postBody){
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
                    final String not_available = "taken";
                    Log.d("RESPONSE-STRING:==>", serverR);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            If the user data is registered
                            if (serverR.equals(available)){
                                Log.d(TAG, "run: VERIFYING NUMBER");
                                Intent intent = new Intent(VerifyContact.this, OtpCodeActivity.class);
                                intent.putExtra("phone_number", phone_number);
                                startActivity(intent);

                            } else if (serverR.equals(not_available)) {
                                Log.d(TAG, "run: DUPLICATE PHONE NUMBER");
                                Intent intent = new Intent(VerifyContact.this, SignUpActivity.class);
//                            Clear the Stack and start new Activity
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),
                                                "Phone Number already registered", Toast.LENGTH_LONG).show();
                                    }
                                },950);

                            }else {
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

}
