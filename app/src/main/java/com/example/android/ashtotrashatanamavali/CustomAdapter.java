package com.example.android.ashtotrashatanamavali;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ravi on 08-12-2017.
 */

public class CustomAdapter extends ArrayAdapter<GridClass> {

    ArrayList<GridClass> al;

    public CustomAdapter(Context contxt, ArrayList<GridClass> al)
    {
        super(contxt,0,al);
        this.al =al;
    }

    public View getView(int position, View ConvertView, ViewGroup parent)
    {
        View v=ConvertView;

        if(v==null)
        {
            LayoutInflater layoutInflater=LayoutInflater.from(getContext());
            v=layoutInflater.inflate(R.layout.viewlayout,null);
        }

        GridClass stotra=getItem(position);

        ImageView img1=(ImageView)v.findViewById(R.id.layout_image);

        TextView tc1=(TextView)v.findViewById(R.id.layout_text);

        if(img1!=null)
            img1.setImageResource(stotra.getImgId());

        if(tc1!=null)
            tc1.setText(stotra.getmDesc());

        return v;
    }

    @Override
    public int getCount() {
        return al.size();
    }
}
