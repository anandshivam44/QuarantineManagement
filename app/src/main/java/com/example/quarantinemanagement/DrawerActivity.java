package com.example.quarantinemanagement;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Arrays;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Initialize();



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
        //setActionBar(mToolbar);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        mToogle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv=findViewById(R.id.global);


    }
}
