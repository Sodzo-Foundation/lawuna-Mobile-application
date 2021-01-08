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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignInOtp extends MainActivity {
    private static final String TAG = "SignInOtp";
    TextView code_received;
    private ProgressBar progressBar;
    private String verificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    private String phone_number;
    private String serverUrl = "<api-route>";

    MainActivity HTTPClient = new MainActivity();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.fragment_otp);
        Button send_otp = findViewById(R.id.otp_Code);
        TextView otpDesc = findViewById(R.id.otpDesc);
        TextView resend_otp = findViewById(R.id.resend_code);
        code_received = findViewById(R.id.textOTP);
        progressBar = findViewById(R.id.progressbar);
//        User Phone Number
        Intent intent = getIntent();
        phone_number = intent.getStringExtra("phone_number");

        sendVerificationCode(phone_number);
        Log.d(TAG, "onCreate: PHONE "+phone_number);
<<<<<<< HEAD:android/app/src/main/java/com/example/lawuna/SignInOtp.java
//        Send OTP Code
        otpDesc.setText(Html.fromHtml("<h2>We've sent an OTP<br/>"
                +" (One Time Pin) <br/>to</h2>"
=======
        //  Send OTP Code
        otpDesc.setText(Html.fromHtml("<h2>We've SMSed an OTP\n"
                +" (One Time Pin) to</h2>"
>>>>>>> develop:app/src/main/java/com/example/lawuna/SignInOtp.java
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
                sendVerificationCode(phone_number);
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
    // Verify OTP code
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
//                            Update logged in  status
                            userActivated();
                            FirebaseUser user = task.getResult().getUser();
                            String phoneNumber  = user.getPhoneNumber();
                            String uID = user.getUid();
                            Intent intent = new Intent(SignInOtp.this, HomeActivity.class);
<<<<<<< HEAD:android/app/src/main/java/com/example/lawuna/SignInOtp.java
//                            Clear the Stack and start new Activity
=======
                            // Clear the Stack and start new Activity

>>>>>>> develop:app/src/main/java/com/example/lawuna/SignInOtp.java
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("phone_number", phoneNumber);

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



    //    Send OTP to the User
    private void sendVerificationCode(String phone_number){
        Log.d(TAG, "sendVerificationCode: SENDING OTP");
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone_number,
                20,
                TimeUnit.SECONDS,
                SignInOtp.this,
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
            Log.d(TAG, "sendVerificationCode: VERIFY SENT"+s);
            Log.d(TAG, "sendVerificationCode: VERIFY RE-SENT"+forceResendingToken);
            verificationId = s;
            // Save verification ID and resending token so we can use them later
            mResendToken = forceResendingToken;
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
            if(e instanceof FirebaseAuthInvalidCredentialsException){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Invalid Credentials", Toast.LENGTH_LONG).show();
                    }
                },800);
            }else if(e instanceof FirebaseTooManyRequestsException){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "SMS Qouta Exceeded", Toast.LENGTH_LONG).show();
                    }
                },800);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },800);
            }
        }
    };

//    Updates the user sign in status
    void userActivated(){
//       Create JSON Object for data transfer
        JSONObject registrationData = new JSONObject();
        try{
            registrationData.put("phonenumber", phone_number);
        }catch (JSONException err){
            err.printStackTrace();
        }

        RequestBody body = RequestBody.create(registrationData.toString(), MediaType.parse("application/json; charset=utf-8"));

        postRequest(serverUrl, body);
    }

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
                    final String activation_status = "success";
                    Log.d("RESPONSE:==>", serverR);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            If the user data is registered
                            if (serverR.equals(activation_status)){
                                Log.d(TAG, "run: ACCOUNT STATUS ACTIVE");

                            } else {
                                Log.d(TAG, "run: ACTIVATION ERROR");
                                Intent intent = new Intent(SignInOtp.this, MainActivity.class);
                                Toast.makeText(getApplicationContext(),
                                        "Sign in Error", Toast.LENGTH_LONG).show();
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
