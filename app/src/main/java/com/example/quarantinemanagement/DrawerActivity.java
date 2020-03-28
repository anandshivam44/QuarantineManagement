package com.example.quarantinemanagement;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class DrawerActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    private Toolbar mToolbar;
    String url_global="https://www.worldometers.info/coronavirus/";
    String url_IN="https://www.worldometers.info/coronavirus/country/india/";
    private TextView global_no_of_cases;
    private TextView global_no_of_death;
    private TextView global_no_of_recovered;
    private TextView tv;
    private static final String TAG ="MyTag" ;

    //number picker variables
    private NumberPicker picker_hr;
    private String[] pickerVals_hr;
    private NumberPicker picker_min;
    private String[] pickerVals_min;
    private NumberPicker picker_sec;
    private String[] pickerVals_sec;
    int hr,min,sec;

    //fingerprint variables
    private ImageView fingerprintImage;
    private TextView instruction;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private Cipher cipher;
    private String KEY_NAME="AndroidKey";

    DatabaseReference myRef;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        Log.d(TAG, "UID "+mAuth.getUid()+" \nCurrent User "+mAuth.getCurrentUser()+" \nPhone Number "+mAuth.getCurrentUser().getPhoneNumber());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Initialize();
        startTimer();
        verifyFingerPrint();



        new CountDownTimer(Integer.MAX_VALUE, 10000) {

            public void onTick(long millisUntilFinished) {
                Content content=new Content();
                content.execute();
            }

            public void onFinish() {
                Content content=new Content();
                content.execute();
            }
        }.start();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToogle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    //for Live Update
    private class Content extends AsyncTask<Void,Void,Void> {
        String array_global[];
        String array_IN[];

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog=new ProgressDialog(MainActivity.this);
            //progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String A="Global\n";
            A+=" Total Case    "+ array_global[0]+"\n Death       "+ array_global[1]+"\n Recovered      "+ array_global[2];
            A+="\n\nIndia\nTotal Case    "+ array_IN[0]+"\n Death       "+ array_IN[1]+"\n Recovered      "+ array_IN[2];
            tv.setText(A);
            //progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc_global = Jsoup.connect(url_global).get();
                Document doc_IN = Jsoup.connect(url_IN).get();
                array_global =doc_global.getElementsByClass("maincounter-number").text().split(" ");
                array_IN=doc_IN.getElementsByClass("maincounter-number").text().split(" ");
                Log.d(TAG, Arrays.toString(array_global)+" global");
                Log.d(TAG, Arrays.toString(array_IN)+" India");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    void Initialize(){
        mToolbar=(Toolbar)findViewById(R.id.nav_action_bar);
        myRef= database.getReference("Quarantine Management");

        //setActionBar(mToolbar);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        mToogle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();

        fingerprintImage=findViewById(R.id.fingerprintImage);
        instruction=findViewById(R.id.instruction);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv=findViewById(R.id.global);
        picker_hr = findViewById(R.id.numberpicker_main_picker_hr);
        picker_hr.setMaxValue(5);
        picker_hr.setMinValue(0);
        pickerVals_hr  = new String[] {"0", "1", "2", "3", "4","5","6"};

        picker_min = findViewById(R.id.numberpicker_main_picker_min);
        picker_min.setMaxValue(59);
        picker_min.setMinValue(0);
        pickerVals_min  = new String[] {
                "0","1","2","3","4","5","6","7","8","9","10",
                "11","12","13","14","15","16","17","18","19","20",
                "21","22","23","24","25","26","27","28","29","30",
                "31","32","33","34","35","36","37","38","39","40",
                "41","42","43","44","45","46","47","48","49","50",
                "51","52","53","54","55","56","57","58","59"};

        picker_sec = findViewById(R.id.numberpicker_main_picker_sec);
        picker_sec.setMaxValue(59);
        picker_sec.setMinValue(0);
        pickerVals_sec  = new String[] {
                "0","1","2","3","4","5","6","7","8","9","10",
                "11","12","13","14","15","16","17","18","19","20",
                "21","22","23","24","25","26","27","28","29","30",
                "31","32","33","34","35","36","37","38","39","40",
                "41","42","43","44","45","46","47","48","49","50",
                "51","52","53","54","55","56","57","58","59"};

        picker_hr.setDisplayedValues(pickerVals_hr);
        picker_min.setDisplayedValues(pickerVals_min);
        picker_min.setDisplayedValues(pickerVals_sec);




    }
    private void startTimer() {
        new CountDownTimer(7200000, 1000) {

            public void onTick(long millisUntilFinished) {

                long total_seconds=(millisUntilFinished / 1000);
                hr=(int)((total_seconds/60)/60);
                min=(int)((total_seconds/60)-(hr*60));
                sec=(int)(total_seconds-((hr*60*60-min*60)));

                picker_hr.setValue(hr);
                picker_min.setValue(min);
                picker_sec.setValue(sec);
            }

            public void onFinish() {
               // mTextField.setText("done!");
            }
        }.start();
    }


    //verify fingerprint
    private void verifyFingerPrint(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            fingerprintManager= (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            if (!fingerprintManager.isHardwareDetected()){
                instruction.setText("Fingerprint Scanner Not Detected");
            }
            else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
                instruction.setText("Permission not Granted");
            }
            else if(!keyguardManager.isKeyguardSecure()){
                instruction.setText("Add lock to your phone in settings");
            }
            else if(!fingerprintManager.hasEnrolledFingerprints()){
                instruction.setText("Add at least one fingerprint in your device to use this feature");
            }
            else{
                instruction.setText("Place your Finger on FingerprintScanner to use this App");

                generateKey();

                if (cipherInit()){
                    FingerprintManager.CryptoObject cryptoObject= new FingerprintManager.CryptoObject(cipher);
                    FingerprintHandler fingerprintHandler=new FingerprintHandler(this);
                    fingerprintHandler.startAuth(fingerprintManager,cryptoObject);
                }
            }
        }

    }

    //creating crypto object below for better security. No need to worry about code. Just keep work going and ignore the code.
    @TargetApi(Build.VERSION_CODES.M)
    private void generateKey() {

        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();

        } catch (KeyStoreException | IOException | CertificateException
                | NoSuchAlgorithmException | InvalidAlgorithmParameterException
                | NoSuchProviderException e) {

            e.printStackTrace();

        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }


        try {

            keyStore.load(null);

            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);

            cipher.init(Cipher.ENCRYPT_MODE, key);

            return true;

        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }

    }
}
