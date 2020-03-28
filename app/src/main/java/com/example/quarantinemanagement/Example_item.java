package com.example.quarantinemanagement;

public class Example_item {
    private  int mImageResource;
    private String mText1;     // for name of authority
    private String mText2;     // for number of authority in string.

    public Example_item(int imageResource, String text1, String text2){
        mImageResource = imageResource;
        mText1 =text1;
        mText2 = text2;



    }
    public int GetImageResource(){
        return mImageResource;

    }
    public String getmText1(){
        return mText1;

    }

    public String getmText2(){

        return mText2;
    }
}
