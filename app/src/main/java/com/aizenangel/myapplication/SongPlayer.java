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

    private static MediaPlayer myMediaPlayer;
    private String songName;
    int position;
    ArrayList<File> mySongs;
    Thread updateSeekBar;
    private String sName;

    static{
        myMediaPlayer = new MediaPlayer();
    }

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
                        sleep(1000);
                        currentPosition = myMediaPlayer.getCurrentPosition();
                        songSeekbar.setProgress(currentPosition);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        if(myMediaPlayer != null){
            myMediaPlayer.reset();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        position = bundle.getInt("position", 0);

        mySongs = (ArrayList)bundle.getParcelableArrayList("songs");
        sName = mySongs.get(position).toString();

        songName = mySongs.get(position).toString();

        //TODO: Could swap it with bundle.getParcelable("song");?
        //String songName = i.getStringExtra("songName");

        String[] token = songName.split("/");
        songName = token[4];
//        System.out.println("**********************************************SONG NAME: " +
//                token.toString() + "**********************************************\n");

        songLabelName.setText(songName);
        //TODO: What happens if i unselect it?
        songLabelName.setSelected(true);



        Uri u = Uri.parse(mySongs.get(position).toString());

        //TODO: can i put SongPlayer.this, or getContent
        try {
            myMediaPlayer.setDataSource(getApplicationContext(), u);
            myMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        myMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                myMediaPlayer.start();
            }
        });
        /*
        if(myMediaPlayer.getCurrentPosition() == myMediaPlayer.getDuration()){
            myMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    myMediaPlayer.stop();
                    myMediaPlayer.reset();

                    position = (position+1)%mySongs.size();
                    Uri u = Uri.parse((mySongs.get(position)).toString());

                    setLabelSongName();

                    try {
                        myMediaPlayer.setDataSource(getApplicationContext(),u);
                        myMediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    myMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {

                            myMediaPlayer.start();
                        }
                    });
                }
            });
        }*/

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

                songName = mySongs.get(position).getName();
                songLabelName.setText(songName);

                try {
                    myMediaPlayer.setDataSource(getApplicationContext(),u);
                    myMediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

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

                songName = mySongs.get(position).getName();
                songLabelName.setText(songName);

                try {
                    myMediaPlayer.setDataSource(getApplicationContext(),u);
                    myMediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

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