package com.example.quarantinemanagement;

import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    private Context context;
    public FingerprintHandler(Context context) {
        this.context=context;
    }

    public void startAuth(FingerprintManager fingerprintManager,FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal=new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal ,0,this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("There was an Auth Error"+errString,false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Auth Failed",false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error "+helpString,false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        this.update("You can now use the App",true);
    }

    private void update(String s, boolean b) {
        TextView ins= (TextView)((Activity)context).findViewById(R.id.instruction);
        ImageView img= (ImageView)((Activity)context).findViewById(R.id.fingerprintImage);
        ins.setText(s);
        if(!b){
            ins.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
        }
        else{
            ins.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
            img.setImageResource(R.drawable.tick_mark);
        }
    }

}
