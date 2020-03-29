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
    private onNoteListener monNoteListener;

    public  class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mImageView;
        public TextView mtextView1;
        public TextView mtextView2;
        onNoteListener onNoteListener;


        public ExampleViewHolder(@NonNull View itemView, onNoteListener onNoteListener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageview);
            mtextView1 = itemView.findViewById(R.id.textview1);
            mtextView2 = itemView.findViewById(R.id.textview2);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteListenre(getAdapterPosition());

        }
    }
    public Exampleadapter(ArrayList<Example_item> exampleList, onNoteListener onNoteListener){
        mexampleList = exampleList;




        this.monNoteListener = onNoteListener;

    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v , monNoteListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

        Example_item currentitem = mexampleList.get(position);
        holder.mImageView.setImageResource(currentitem.GetImageResource());
        holder.mtextView1.setText(currentitem.getmText1());
        holder.mtextView2.setText(currentitem.getmText2());
        //holder.itemView.setOnClickListener();



    }

    @Override
    public int getItemCount() {
        return mexampleList.size();
    }

    //Inter face containing method......
    public interface onNoteListener{
        void onNoteListenre(int position);
    }
}
