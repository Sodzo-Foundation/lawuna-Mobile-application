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
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lawuna.env.Logger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OtpCodeActivity extends AppCompatActivity {
    private static final String TAG = "OtpCodeActivity";
    private static final Logger LOGGER = new Logger();
    TextView code_received;
    private ProgressBar progressBar;
    private String verificationId;
    private FirebaseAuth mAuth;
    private String phone_number;
    private String user_name;
    private String email;
    // User Session Manager Class
    UserSessionManager session;
    private String registerUrl = "<api-route>";


    MainActivity HTTPClient = new MainActivity();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.fragment_otp);
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());
        Button send_otp = findViewById(R.id.otp_Code);
        TextView otpDesc = findViewById(R.id.otpDesc);
        TextView resend_otp = findViewById(R.id.resend_code);
        code_received = findViewById(R.id.textOTP);
        progressBar = findViewById(R.id.progressbar);
        Intent intent = getIntent();
        phone_number = intent.getStringExtra("phone_number");

        sendVerificationCode(phone_number);
        user_name = intent.getStringExtra("user_name");
        email = intent.getStringExtra("email");
        String number_save = phone_number;
        Log.d(TAG, "onCreate: PHONE "+phone_number);
        // Creating user login session
        session.createUserLoginSession(user_name, email, phone_number);
<<<<<<< HEAD:android/app/src/main/java/com/example/lawuna/OtpCodeActivity.java
//        Send OTP Code
        otpDesc.setText(Html.fromHtml("<h2>We've SMSed an OTP\n"
                +" (One Time Pin) to</h2>"
=======
        // Send OTP Code
        otpDesc.setText(Html.fromHtml("<h2>We've sent an OTP\n"
                +" (One Time Pin) \nto</h2>"
>>>>>>> develop:app/src/main/java/com/example/lawuna/OtpCodeActivity.java
                +phone_number));

        send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = code_received.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    code_received.setError("Enter valid code");
                    code_received.requestFocus();
                    return;

                }
                verifyCode(code);
            }
        });

        resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        // Send OTP Code
                if (phone_number.length() != 0){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Code Resent", Toast.LENGTH_LONG).show();
                        }
                    },950);

                }

            }
        });

    }
    //    Verify OTP code
    private void verifyCode(String code){
        PhoneAuthCredential credentials = PhoneAuthProvider.getCredential(verificationId, code);
    // Allow the user to sign in
        signInWithCredential(credentials);
    }

    private void signInWithCredential(PhoneAuthCredential credentials) {
        mAuth.signInWithCredential(credentials)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if(task.isSuccessful()){
                        // Register User
                            registerUser();
                            Intent intent = new Intent(OtpCodeActivity.this, HomeActivity.class);
                        // Clear the Stack and start new Activity
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("phone_number", phone_number);

                            startActivity(intent);

                        }else{
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),
                                            task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            },800);
                        }
                    }
                });
    }

    //    Register the user
    private void registerUser(){
<<<<<<< HEAD:android/app/src/main/java/com/example/lawuna/OtpCodeActivity.java

//                    Create JSON Object for data transfer
        JSONObject registrationData = new JSONObject();
//                    Get Timestamp
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        LOGGER.d("DATA: ",user_name);
=======
    // Check if the email exists
    // Create JSON Object for data transfer
        JSONObject registrationData = new JSONObject();
    // Get Timestamp
        Date currentTime = Calendar.getInstance().getTime();
        String timestamp = currentTime.toString();
        Log.d("DATA",timestamp);
>>>>>>> develop:app/src/main/java/com/example/lawuna/OtpCodeActivity.java
        try{
            registrationData.put("name", user_name);
            registrationData.put("email", email);
            registrationData.put("phonenumber", phone_number);
            registrationData.put("joined", timestamp);
        }catch (JSONException err){
            err.printStackTrace();
        }

        RequestBody body = RequestBody.create(registrationData.toString(), MediaType.parse("application/json; charset=utf-8"));

        postRequest(registerUrl, body);
    }



    //    Send OTP to the User
    private void sendVerificationCode(String phone_number){
        Log.d(TAG, "sendVerificationCode: SENDING OTP");
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone_number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    // Callback instance
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        // Called when code is sent s= verification Id sent by the SMS
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken){
            super.onCodeSent(s, forceResendingToken);
            Log.d(TAG, "sendVerificationCode: VERIFY SENT");
            verificationId = s;
        }

        // Called when the verification is complete, gets code automatically
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            Log.d(TAG, "onVerificationCompleted: RECEIVED OTP "+code);
            if(code != null){
                code_received.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(final FirebaseException e) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            e.getMessage(), Toast.LENGTH_LONG).show();
                }
            },800);
        }
    };

    // Post Network Request
    public void postRequest(String postUrl, RequestBody postBody){
        OkHttpClient client = HTTPClient.getHttpClient();
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
                    Log.d("RESPONSE:==>", serverR);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                    // If the user data is registered
                            if (serverR.equals(available)){
                                Log.d(TAG, "run: REGISTERED SUCCESSFULLY");

                            } else {
                                Log.d(TAG, "run: REGISTRATION ERROR");
                                Toast.makeText(getApplicationContext(),
                                        "Something went wrong.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
                response.body().close();
            }
        });
    }



}
