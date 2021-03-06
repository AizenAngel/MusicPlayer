package com.aizenangel.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ListView songList;
    public static int selectedSong=-1;

    public static View previousSong;

    static{
        previousSong = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songList = findViewById(R.id.song_list);
        requestPermissionAtRuntime();
    }

    public void requestPermissionAtRuntime(){

        Dexter.withActivity(this)
              .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
              .withListener(new PermissionListener() {
                  @Override
                  public void onPermissionGranted(PermissionGrantedResponse response) {
                    display();
                  }

                  @Override
                  public void onPermissionDenied(PermissionDeniedResponse response) {
                      Toast.makeText(getApplicationContext(), "App cannot provide you with songs," +
                              "untill you give it permission to do so!", Toast.LENGTH_SHORT).show();
                  }

                  @Override
                  public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    token.continuePermissionRequest();
                  }
              }).check();

    }

    public ArrayList<File> getSongs(File file){
        ArrayList<File> arrayList = new ArrayList<>();
        //TODO: What if no song was found?
        File[] allFiles = file.listFiles();

        for(File singleFile: allFiles){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                arrayList.addAll(getSongs(singleFile));
            }else{
                if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")){
                    arrayList.add(singleFile);
                }
            }
        }

        return arrayList;
    }

    void display(){
        final ArrayList<File> songNames = getSongs(Environment.getExternalStorageDirectory());
        final ArrayList<String> songShortNames = new ArrayList<>();

        for(File f: songNames){
            String[]token = f.getName().split("/");
            String sName = token[0];
            songShortNames.add(sName);
        }

        SongAdapter songAdapter = new SongAdapter(this, songNames);
        songList.setAdapter(songAdapter);

        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            if(previousSong!=null){
               previousSong.setBackgroundColor(Color.WHITE);
            }

            selectedSong = i;
            view.setBackgroundColor(Color.RED);

            Toast.makeText(getApplicationContext(), songShortNames.get(i), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), SongPlayer.class)
                .putExtra("songs", songNames)
                .putExtra("position", i));

            previousSong = view;
            }
        });

    }

    public static View getViewByPosition(int pos) {
        final int firstListItemPosition = songList.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + songList.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return songList.getAdapter().getView(pos, null, songList);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return songList.getChildAt(childIndex);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(songList != null){
            songList = null;
        }
    }
}


