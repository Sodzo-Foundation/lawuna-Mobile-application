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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
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

public class SignInOtp extends AppCompatActivity {
    private static final String TAG = "OtpCodeActivity";
    TextView code_received;
    private ProgressBar progressBar;
    private String verificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    private String phone_number;
    private String serverUrl = "<api-route>";


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
        Intent intent = getIntent();
        phone_number = intent.getStringExtra("phone_number");

        sendVerificationCode(phone_number);
        Log.d(TAG, "onCreate: PHONE "+phone_number);
        //  Send OTP Code
        otpDesc.setText(Html.fromHtml("<h2>We've SMSed an OTP\n"
                +" (One Time Pin) to</h2>"
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
                            FirebaseUser user = task.getResult().getUser();
                            String phoneNumber  = user.getPhoneNumber();
                            String uID = user.getUid();
                            Intent intent = new Intent(SignInOtp.this, HomeActivity.class);
                            // Clear the Stack and start new Activity

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


}
