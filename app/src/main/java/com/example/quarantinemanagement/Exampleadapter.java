package com.example.quarantinemanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Exampleadapter extends RecyclerView.Adapter<Exampleadapter.ExampleViewHolder> {
    private ArrayList<Example_item> mexampleList;

    public static  class ExampleViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mtextView1;
        public TextView mtextView2;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageview);
            mtextView1 = itemView.findViewById(R.id.textview1);
            mtextView2 = itemView.findViewById(R.id.textview2);

        }
    }
    public Exampleadapter(ArrayList<Example_item> exampleList){
        mexampleList = exampleList;






    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
       ExampleViewHolder evh = new ExampleViewHolder(v);
       return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

        Example_item currentitem = mexampleList.get(position);
        holder.mImageView.setImageResource(currentitem.GetImageResource());
        holder.mtextView1.setText(currentitem.getmText1());
        holder.mtextView2.setText(currentitem.getmText2());



    }

    @Override
    public int getItemCount() {
        return mexampleList.size();
    }
}
