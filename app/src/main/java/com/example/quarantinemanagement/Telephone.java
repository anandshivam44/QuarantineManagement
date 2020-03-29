package com.example.quarantinemanagement;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Telephone extends AppCompatActivity implements Exampleadapter.onNoteListener {

    ArrayList<Example_item> exampleList;

    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter madapter;
    private RecyclerView.LayoutManager mlayoutmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephone);

        exampleList = new ArrayList<>();
        exampleList.add(new Example_item(R.drawable.ic_call_black_phone,"Andhra Pradesh","0866-2410978"));
        exampleList.add(new Example_item(R.drawable.ic_call_black_phone,"Arunanchal Pradesh","9436055743"));
        exampleList.add(new Example_item(R.drawable.ic_call_black_phone,"Assam","6913347770"));
        exampleList.add(new Example_item(R.drawable.ic_call_black_phone,"Bihar","104"));
        exampleList.add(new Example_item(R.drawable.ic_call_black_phone,"Chhattisgarh","104"));
        exampleList.add(new Example_item(R.drawable.ic_call_black_phone,"Goa","104"));
        exampleList.add(new Example_item(R.drawable.ic_call_black_phone,"Gujrat","104"));
        exampleList.add(new Example_item(R.drawable.ic_call_black_phone,"Harayana","8558893911"));
        exampleList.add(new Example_item(R.drawable.ic_call_black_phone,"Jharkhand","104"));
        exampleList.add(new Example_item(R.drawable.ic_call_black_phone,"Himanchal Pradesh","104"));
        exampleList.add(new Example_item(R.drawable.ic_call_black_phone,"Karnataka","104"));
        exampleList.add(new Example_item(R.drawable.ic_call_black_phone,"Kerala","0471-2552056"));
        exampleList.add(new Example_item(R.drawable.ic_call_black_phone,"Madhya Pradesh","0755-2527177"));
        exampleList.add(new Example_item(R.drawable.ic_call_black_phone,"Maharashtra","020-26127394"));
        exampleList.add(new Example_item(R.drawable.ic_call_black_phone,"Manipur","3852411668"));
        mRecyclerview = findViewById(R.id.recyl);
        mRecyclerview.setHasFixedSize(true);

        mlayoutmanager = new LinearLayoutManager(this);
        madapter = new Exampleadapter(exampleList,this);
        mRecyclerview.setLayoutManager(mlayoutmanager);
        mRecyclerview.setAdapter(madapter);
    }
    public void helo(View v){

    }

    @Override
    public void onNoteListenre(int position) {
        exampleList.get(position);
        Example_item listitemok = exampleList.get(position);
        String phoneno =listitemok.getmText2();
        Toast.makeText(getApplicationContext(),phoneno,Toast.LENGTH_LONG).show();
        Uri u = Uri.parse("tel:"+phoneno);
        Intent i = new Intent(Intent.ACTION_DIAL, u);
        startActivity(i);
    }
}