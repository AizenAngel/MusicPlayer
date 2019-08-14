package com.aizenangel.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class SongAdapter extends BaseAdapter {

    Context context;
    ArrayList<File> mySongs;

    public SongAdapter(Context context, ArrayList<File> mySongs) {
        this.context = context;
        this.mySongs = mySongs;
    }

    @Override
    public int getCount() {
        return mySongs.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return this.mySongs.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       View rowView = View.inflate(context, R.layout.custom_cell, null);

       TextView nameTxt = rowView.findViewById(R.id.nameTxt);

        if(MainActivity.selectedSong == i){
          nameTxt.setBackgroundColor(Color.RED);
        }

       nameTxt.setText(this.mySongs.get(i).getName());
       return rowView;
    }
}
