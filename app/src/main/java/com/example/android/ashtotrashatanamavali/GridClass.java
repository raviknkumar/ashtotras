package com.example.android.ashtotrashatanamavali;

/**
 * Created by ravi on 08-12-2017.
 */

public class GridClass {
    private int mImgId;
    private String mDesc;
    private int audioResourceId;

    public GridClass(int a, String b ,int c)
    {
        mImgId=a;
        mDesc=b;
        audioResourceId=c;
    }

    public int getImgId()
    {
        return mImgId;
    }

    public String getmDesc() {
        return mDesc;
    }

    public int getAudioResourceId() { return audioResourceId; }
}
