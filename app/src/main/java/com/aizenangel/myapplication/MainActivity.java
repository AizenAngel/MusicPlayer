package com.aizenangel.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView songList;

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
        ArrayAdapter<File> arrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songNames);
        songList.setAdapter(arrayAdapter);

        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String songName = songList.getItemAtPosition(i).toString();

                startActivity(new Intent(getApplicationContext(), SongPlayer.class)
                .putExtra("songs", songNames)
                .putExtra("songName", songName)
                .putExtra("position", i));
            }
        });

    }
}

