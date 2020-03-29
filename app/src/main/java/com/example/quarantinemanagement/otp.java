package com.example.quarantinemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import java.util.concurrent.TimeUnit;

public class otp extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG ="MyTag" ;
    private PinView pinView;
    private Button next;
    private TextView topText, textU;
    private EditText userName, userPhone;
    private ConstraintLayout first, second;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private Button skip;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getUid()!=null){
            Intent intent = new Intent(otp.this, DrawerActivity.class);
            Log.d(TAG, "Inside OTP\n\n"+"UID " + mAuth.getUid() + " \nCurrent User " + mAuth.getCurrentUser() + " \nPhone Number " + mAuth.getCurrentUser().getPhoneNumber());
            startActivity(intent);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(1);//adjusting full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("quarantine-management");

        myRef.setValue("Hello, World!");

        setContentView(R.layout.activity_otp);
        mAuth = FirebaseAuth.getInstance();
        topText = findViewById(R.id.topText_otp);
        pinView = findViewById(R.id.pinView_otp);
        next = findViewById(R.id.button_otp);
        userPhone = findViewById(R.id.userPhone_otp);
        first = findViewById(R.id.first_step_otp);
        second = findViewById(R.id.secondStep_otp);
        textU = findViewById(R.id.textView_noti_otp);
        first.setVisibility(View.VISIBLE);
        skip=findViewById(R.id.skip);

        next.setOnClickListener(this);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Skip button Clicked");
                Intent intent = new Intent(otp.this, DrawerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "lower button clicked");
        if (next.getText().equals("Let's go!")) {//send otp
            String phone = userPhone.getText().toString();
            Log.d(TAG, "Phone "+phone);

            if (!TextUtils.isEmpty(phone)) {
                next.setText("Verify");
                first.setVisibility(View.GONE);
                second.setVisibility(View.VISIBLE);
                topText.setText("I Still don't trust you.nTell me something that only two of us know.");

                //most important backend is next line
                sendVerificationCode(phone);


            } else {
                Toast.makeText(otp.this, "Please enter the details", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "NAme or phone number is empty");
            }
        } else if (next.getText().equals("Verify")) {//verify otp
            Log.d(TAG, "Entered else if part ");
            String OTP = pinView.getText().toString();
            verifyVerificationCode(OTP);
//            if (OTP.equals("3456")) {
//                pinView.setLineColor(Color.GREEN);
//                textU.setText("OTP Verified");
//                textU.setTextColor(Color.GREEN);
//                next.setText("Next");
//            } else {
//                pinView.setLineColor(Color.RED);
//                textU.setText("X Incorrect OTP");
//                textU.setTextColor(Color.RED);
//            }
        }

    }

    //the method is sending verification code
    //the country id is concatenated
    //you can take the country id as user input as well
    private void sendVerificationCode(String phone) {   // method for getting sms.........
        Log.d(TAG, "Just entered send verification code");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phone,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
        Log.d(TAG, "Completed send verification code");

    }

    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {         //this object is for tracking sms sent or not.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Log.d(TAG, "Just entered onVerificationCompleted");//Log.d(TAG, "Just entered ");

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();                                          //code for auto detection .....
            Toast.makeText(getApplicationContext(), "SMS Reading Done", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Code Received by app code: "+code);

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                Log.d(TAG, "Code Received by app and code is not empty set text in pinVier");
                pinView.setText(code);
                //verifying the code
                verifyVerificationCode(code);                                               //function call for verifying code using autodetected code
            }
            else{
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }
            Log.d(TAG, "Completed onVerificationCompleted");
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.d(TAG, "Verification Failed "+e.getMessage());
            Toast.makeText(otp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            Log.d(TAG, "Entered Code sent");
            super.onCodeSent(s, forceResendingToken);
            Toast.makeText(getApplicationContext(), "CODE SENT s="+s, Toast.LENGTH_SHORT).show();

            //storing the verification id that is sent to the user
            mVerificationId = s;
            Log.d(TAG, "Exit Code sent");
        }
    };


    private void verifyVerificationCode(String code) {  // method for verification called by submit otp button....
        //creating the credential
        Log.d(TAG, "entered verifyVerificationCode ");
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
        Log.d(TAG, "Exit verifyVerificationCode ");
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Log.d(TAG, "entered signInWithPhoneAuthCredential ");
        // code for user signin......
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(otp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signIn Complete ");

                            //verification successful we will start the profile activity
                            Intent intent = new Intent(otp.this, DrawerActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {
                            Log.d(TAG, "sign in failed ");

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

//                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_SHORT);
//                            snackbar.setAction("Dismiss", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                }
//                            });
//                            snackbar.show();
                        }
                    }
                });
        Log.d(TAG, "Exit signInWithPhoneAuthCredential ");
    }

}
