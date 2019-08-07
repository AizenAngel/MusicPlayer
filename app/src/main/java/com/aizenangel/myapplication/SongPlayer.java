package com.aizenangel.myapplication;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SongPlayer extends AppCompatActivity {
    private TextView songLabelName;
    private SeekBar songSeekbar;
    private Button btnPrev, btnNext, btnPause;
    int currentPosition1;

    private MediaPlayer myMediaPlayer;
    int position;
    ArrayList<File> mySongs;
    Thread updateSeekBar;
    private String sName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_player);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        songLabelName = findViewById(R.id.song_name);
        songSeekbar = findViewById(R.id.seek_bar);
        btnPrev = findViewById(R.id.btn_pre);
        btnNext = findViewById(R.id.btn_next);
        btnPause = findViewById(R.id.btn_pause);

        getSupportActionBar().setTitle("Now Playing: ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        updateSeekBar = new Thread(){
            @Override
            public void run() {
                int totalDuration=0;

                        totalDuration = myMediaPlayer.getDuration();



                int currentPosition = 0;


                while(currentPosition < totalDuration){
                    try{
                       sleep(100);
                       currentPosition = myMediaPlayer.getCurrentPosition();
                       songSeekbar.setProgress(currentPosition);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        if(myMediaPlayer != null){
            myMediaPlayer.stop();
            myMediaPlayer.release();
            myMediaPlayer = null;
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        mySongs = (ArrayList)bundle.getParcelableArrayList("songs");
        sName = mySongs.get(position).toString();

        //TODO: Could swap it with bundle.getParcelable("song");?
        String songName = i.getStringExtra("songName");

        songLabelName.setText(songName);
        //TODO: What happens if i unselect it?
        songLabelName.setSelected(true);

        position = bundle.getInt("position", 0);

        Uri u = Uri.parse(mySongs.get(position).toString());

        //TODO: can i put SongPlayer.this, or getContent
        myMediaPlayer = MediaPlayer.create(getApplicationContext(), u);

        myMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                myMediaPlayer.start();
            }
        });
        songSeekbar.setMax(myMediaPlayer.getDuration());

        updateSeekBar.start();
        songSeekbar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        songSeekbar.getThumb().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);


        songSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                myMediaPlayer.seekTo(seekBar.getProgress());
            }
        });


        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                songSeekbar.setMax(myMediaPlayer.getDuration());

                if(myMediaPlayer.isPlaying()){
                    btnPause.setBackgroundResource(R.drawable.icon_play);
                    myMediaPlayer.pause();
                }else{
                    btnPause.setBackgroundResource(R.drawable.icon_pause);
                    myMediaPlayer.start();
                }

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                myMediaPlayer.stop();
                myMediaPlayer.reset();

                position = (position+1)%mySongs.size();
                Uri u = Uri.parse((mySongs.get(position)).toString());
                songLabelName.setText(mySongs.get(position).toString());

                try {
                    myMediaPlayer.setDataSource(getApplicationContext(),u);
                    myMediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("**************************************" +
                                   myMediaPlayer + "***********************************");

              //  updateSeekBar.start();
                myMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {

                        myMediaPlayer.start();
                    }
                });

            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myMediaPlayer.stop();
                myMediaPlayer.reset();

                position = (position > 0?(position-1):mySongs.size()-1);
                Uri u = Uri.parse(mySongs.get(position).toString());
                songLabelName.setText(mySongs.get(position).toString());

                try {
                    myMediaPlayer.setDataSource(getApplicationContext(),u);
                    myMediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("**************************************" +
                        myMediaPlayer + "***********************************");

            //    updateSeekBar.start();
                myMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        myMediaPlayer.start();
                    }
                });
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
             onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
