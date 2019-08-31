// // package com.aizenangel.myapplication;

// // import android.content.Intent;
// // import android.graphics.Color;
// // import android.graphics.PorterDuff;
// // import android.media.MediaPlayer;
// // import android.net.Uri;
// // import android.os.Bundle;


// // import androidx.annotation.NonNull;
// // import androidx.appcompat.app.AppCompatActivity;
// // import androidx.appcompat.widget.Toolbar;

// // import android.view.MenuItem;
// // import android.view.View;
// // import android.widget.Button;
// // import android.widget.SeekBar;
// // import android.widget.TextView;

// // import java.io.File;
// // import java.io.IOException;
// // import java.util.ArrayList;

// // public class SongPlayer extends AppCompatActivity {
// //     private TextView songLabelName;
// //     private SeekBar songSeekbar;
// //     private Button btnPrev, btnNext, btnPause;

// //     private static MediaPlayer myMediaPlayer;
// //     private String songName;
// //     private int position;
// //     private ArrayList<File> mySongs;
// //     private Thread updateSeekBar;

// //     static{
// //         myMediaPlayer = new MediaPlayer();
// //     }

// //     @Override
// //     protected void onCreate(Bundle savedInstanceState) {
// //         super.onCreate(savedInstanceState);
// //         setContentView(R.layout.activity_song_player);
// //         Toolbar toolbar = findViewById(R.id.toolbar);
// //         setSupportActionBar(toolbar);

// //         songLabelName = findViewById(R.id.song_name);
// //         songSeekbar = findViewById(R.id.seek_bar);
// //         btnPrev = findViewById(R.id.btn_pre);
// //         btnNext = findViewById(R.id.btn_next);
// //         btnPause = findViewById(R.id.btn_pause);

// //         getSupportActionBar().setTitle("Now Playing: ");
// //         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
// //         getSupportActionBar().setDisplayShowHomeEnabled(true);

// //         updateSeekBar = new Thread(){
// //             @Override
// //             public void run() {
// //                 int totalDuration;

// //                 totalDuration = myMediaPlayer.getDuration();

// //                 int currentPosition = 0;


// //                 while(currentPosition < totalDuration){
// //                     try{
// //                         sleep(1000);
// //                         currentPosition = myMediaPlayer.getCurrentPosition();
// //                         songSeekbar.setProgress(currentPosition);
// //                     }catch(InterruptedException e){
// //                         e.printStackTrace();
// //                     }
// //                 }
// //             }
// //         };

// //         if(myMediaPlayer != null){
// //             myMediaPlayer.reset();
// //         }

// //         Intent i = getIntent();
// //         Bundle bundle = i.getExtras();
// //         try{
// //             position = bundle.getInt("position", 0);
// //         }catch(NullPointerException e){
// //             position = 0;
// //         }

// //         mySongs = (ArrayList)bundle.getParcelableArrayList("songs");

// //         songName = mySongs.get(position).toString();

// //         //TODO: Could swap it with bundle.getParcelable("song");?
// //         //String songName = i.getStringExtra("songName");

// //         String[] token = songName.split("/");
// //         songName = token[4];
// // //        System.out.println("**********************************************SONG NAME: " +
// // //                token.toString() + "**********************************************\n");

// //         songLabelName.setText(songName);
// //         //TODO: What happens if i unselect it?
// //         songLabelName.setSelected(true);



// //         Uri u = Uri.parse(mySongs.get(position).toString());

// //         //TODO: can i put SongPlayer.this, or getContent
// //         try {
// //             myMediaPlayer.setDataSource(getApplicationContext(), u);
// //             myMediaPlayer.prepare();
// //         } catch (IOException e) {
// //             e.printStackTrace();
// //         }

// //         myMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
// //             @Override
// //             public void onPrepared(MediaPlayer mediaPlayer) {
// //                 myMediaPlayer.start();
// //             }
// //         });
// //         /*
// //         if(myMediaPlayer.getCurrentPosition() == myMediaPlayer.getDuration()){
// //             myMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
// //                 @Override
// //                 public void onCompletion(MediaPlayer mediaPlayer) {
// //                     myMediaPlayer.stop();
// //                     myMediaPlayer.reset();

// //                     position = (position+1)%mySongs.size();
// //                     Uri u = Uri.parse((mySongs.get(position)).toString());

// //                     setLabelSongName();

// //                     try {
// //                         myMediaPlayer.setDataSource(getApplicationContext(),u);
// //                         myMediaPlayer.prepare();
// //                     } catch (IOException e) {
// //                         e.printStackTrace();
// //                     }

// //                     myMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
// //                         @Override
// //                         public void onPrepared(MediaPlayer mediaPlayer) {

// //                             myMediaPlayer.start();
// //                         }
// //                     });
// //                 }
// //             });
// //         }*/

// //         songSeekbar.setMax(myMediaPlayer.getDuration());

// //         updateSeekBar.start();
// //         songSeekbar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
// //         songSeekbar.getThumb().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);


// //         songSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
// //             @Override
// //             public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

// //             }

// //             @Override
// //             public void onStartTrackingTouch(SeekBar seekBar) {

// //             }

// //             @Override
// //             public void onStopTrackingTouch(SeekBar seekBar) {
// //                 myMediaPlayer.seekTo(seekBar.getProgress());
// //             }
// //         });


// //         btnPause.setOnClickListener(new View.OnClickListener() {
// //             @Override
// //             public void onClick(View view) {

// //                 songSeekbar.setMax(myMediaPlayer.getDuration());

// //                 if(myMediaPlayer.isPlaying()){
// //                     btnPause.setBackgroundResource(R.drawable.icon_play);
// //                     myMediaPlayer.pause();
// //                 }else{
// //                     btnPause.setBackgroundResource(R.drawable.icon_pause);
// //                     myMediaPlayer.start();
// //                 }

// //             }
// //         });

// //         btnNext.setOnClickListener(new View.OnClickListener() {
// //             public void onClick(View view) {
// //                 myMediaPlayer.stop();
// //                 myMediaPlayer.reset();

// //                 MainActivity.previousSong.setBackgroundColor(Color.WHITE);

// //                 position = (position+1)%mySongs.size();
// //                 Uri u = Uri.parse((mySongs.get(position)).toString());

// //                 songName = mySongs.get(position).getName();
// //                 songLabelName.setText(songName);

// //                 try {
// //                     myMediaPlayer.setDataSource(getApplicationContext(),u);
// //                     myMediaPlayer.prepare();
// //                 } catch (IOException e) {
// //                     e.printStackTrace();
// //                 }

// //                 myMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
// //                     @Override
// //                     public void onPrepared(MediaPlayer mediaPlayer) {
// //                         myMediaPlayer.start();
// //                     }
// //                 });
// //                 MainActivity.selectedSong = position;
// //                 MainActivity.getViewByPosition(position).setBackgroundColor(Color.RED);
// //                 MainActivity.previousSong = MainActivity.getViewByPosition(position);
// //             }
// //         });


// //         btnPrev.setOnClickListener(new View.OnClickListener() {
// //             @Override
// //             public void onClick(View view) {
// //                 myMediaPlayer.stop();
// //                 myMediaPlayer.reset();

// //                 MainActivity.previousSong.setBackgroundColor(Color.WHITE);

// //                 position = (position > 0?(position-1):mySongs.size()-1);
// //                 Uri u = Uri.parse(mySongs.get(position).toString());

// //                 songName = mySongs.get(position).getName();
// //                 songLabelName.setText(songName);

// //                 try {
// //                     myMediaPlayer.setDataSource(getApplicationContext(),u);
// //                     myMediaPlayer.prepare();
// //                 } catch (IOException e) {
// //                     e.printStackTrace();
// //                 }

// //                 myMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
// //                     @Override
// //                     public void onPrepared(MediaPlayer mediaPlayer) {
// //                         myMediaPlayer.start();
// //                     }
// //                 });

// //                 MainActivity.selectedSong = position;
// //                 MainActivity.getViewByPosition(position).setBackgroundColor(Color.RED);
// //                 MainActivity.previousSong = MainActivity.getViewByPosition(position);
// //             }
// //         });
// //     }

// //     @Override
// //     public boolean onOptionsItemSelected(@NonNull MenuItem item) {

// //         if (item.getItemId() == android.R.id.home) {
// //             onBackPressed();
// //         }
// //         return super.onOptionsItemSelected(item);
// //     }


// // }
// package com.aizenangel.myapplication;

// import android.content.Intent;
// import android.graphics.Color;
// import android.graphics.PorterDuff;
// import android.media.MediaPlayer;
// import android.net.Uri;
// import android.os.Bundle;


// import androidx.annotation.NonNull;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.appcompat.widget.Toolbar;

// import android.view.MenuItem;
// import android.view.View;
// import android.widget.Button;
// import android.widget.SeekBar;
// import android.widget.TextView;

// import java.io.File;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.concurrent.TimeUnit;

// public class SongPlayer extends AppCompatActivity {
//     private TextView songLabelName, timePassed, totalTime;
//     private SeekBar songSeekbar;
//     private Button btnPrev, btnNext, btnPause;

//     private static MediaPlayer myMediaPlayer;
//     private String songName;
//     private int position;
//     private ArrayList<File> mySongs;
//     private Thread updateSeekBar;
//     public int currentPosition=0;

//     private final int SLEEP_TIME = 500;

//     static{
//         myMediaPlayer = new MediaPlayer();
//     }

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_song_player);
//         Toolbar toolbar = findViewById(R.id.toolbar);
//         setSupportActionBar(toolbar);

//         songLabelName = findViewById(R.id.song_name);
//         songSeekbar = findViewById(R.id.seek_bar);
//         btnPrev = findViewById(R.id.btn_pre);
//         btnNext = findViewById(R.id.btn_next);
//         btnPause = findViewById(R.id.btn_pause);
//         timePassed = findViewById(R.id.SeekBarMinutes);
//         totalTime = findViewById(R.id.MediaPlayerDuration);

//         getSupportActionBar().setTitle("Now Playing: ");
//         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//         getSupportActionBar().setDisplayShowHomeEnabled(true);

//         updateSeekBar = new Thread(){
//             @Override
//             public void run() {
//                 final int totalDuration;

//                 totalDuration = myMediaPlayer.getDuration();

//                 currentPosition = 0;


//                 while(currentPosition < totalDuration){
//                     try{
//                         sleep(SLEEP_TIME);
//                         currentPosition = myMediaPlayer.getCurrentPosition();
//                         songSeekbar.setProgress(currentPosition);

//                         runOnUiThread(new Runnable() {
//                             @Override
//                             public void run() {
//                                 setSongProgress();
//                             }

//                         });

// //                        System.out.println("SeekBar current position: "+currentPosition/1000);
// //                        System.out.println("MediaPlayer total duration: "+totalDuration/1000);
//                     }catch(InterruptedException e){
//                         e.printStackTrace();
//                     }
//                 }
//             }
//         };

//         if(myMediaPlayer != null){
//             myMediaPlayer.reset();
//         }

//         Intent i = getIntent();
//         Bundle bundle = i.getExtras();
//         try{
//             position = bundle.getInt("position", 0);
//         }catch(NullPointerException e){
//             position = 0;
//         }

//         mySongs = (ArrayList)bundle.getParcelableArrayList("songs");

//         songName = mySongs.get(position).toString();

//         //TODO: Could swap it with bundle.getParcelable("song");?
//         //String songName = i.getStringExtra("songName");

//         String[] token = songName.split("/");
//         songName = token[4];
// //        System.out.println("**********************************************SONG NAME: " +
// //                token.toString() + "**********************************************\n");

//         songLabelName.setText(songName);
//         //TODO: What happens if i unselect it?
//         songLabelName.setSelected(true);



//         Uri u = Uri.parse(mySongs.get(position).toString());

//         //TODO: can i put SongPlayer.this, or getContent
//         try {
//             myMediaPlayer.setDataSource(getApplicationContext(), u);
//             myMediaPlayer.prepare();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }

//         myMediaPlayer.start();
//         setDuration();


// //        myMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
// //            @Override
// //            public void onCompletion(MediaPlayer mediaPlayer) {
// //                    setSongProgress();
// //                    System.out.println(currentPosition/1000);
// //            }
// //        });




//         updateSeekBar.start();
// //        songSeekbar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
// //        songSeekbar.getThumb().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);


//         songSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//             @Override
//             public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

//             }

//             @Override
//             public void onStartTrackingTouch(SeekBar seekBar) {

//             }

//             @Override
//             public void onStopTrackingTouch(SeekBar seekBar) {
//                 myMediaPlayer.seekTo(seekBar.getProgress());
//             }
//         });


//         btnPause.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {

//                 songSeekbar.setMax(myMediaPlayer.getDuration());

//                 if(myMediaPlayer.isPlaying()){
//                     btnPause.setBackgroundResource(R.drawable.icon_play);
//                     myMediaPlayer.pause();
//                 }else{
//                     btnPause.setBackgroundResource(R.drawable.icon_pause);
//                     myMediaPlayer.start();
//                 }

//             }
//         });

//         btnNext.setOnClickListener(new View.OnClickListener() {
//             public void onClick(View view) {

//                 myMediaPlayer.stop();
//                 myMediaPlayer.reset();

//                 MainActivity.previousSong.setBackgroundColor(Color.WHITE);

//                 if(updateSeekBar.isAlive())
//                     updateSeekBar.interrupt();

//                 updateSeekBar = new Thread(){
//                     @Override
//                     public void run() {
//                         int totalDuration;

//                         totalDuration = myMediaPlayer.getDuration();

//                         currentPosition = 0;


//                         while(currentPosition < totalDuration){
//                             try{
//                                 sleep(SLEEP_TIME);
//                                 currentPosition = myMediaPlayer.getCurrentPosition();
//                                 songSeekbar.setProgress(currentPosition);

//                                 runOnUiThread(new Runnable() {
//                                     @Override
//                                     public void run() {
//                                         setSongProgress();
//                                     }
//                                 });
//                             }catch(InterruptedException e){
//                                 e.printStackTrace();
//                             }
//                         }
//                     }
//                 };
//                 position = (position+1)%mySongs.size();
//                 Uri u = Uri.parse((mySongs.get(position)).toString());

//                 songName = mySongs.get(position).getName();
//                 songLabelName.setText(songName);

//                 try {
//                     myMediaPlayer.setDataSource(getApplicationContext(),u);
//                     myMediaPlayer.prepare();
//                 } catch (IOException e) {
//                     e.printStackTrace();
//                 }

//                 songSeekbar.setMax(myMediaPlayer.getDuration());
//                 updateSeekBar.start();

//                 try {
//                     Thread.currentThread().sleep(SLEEP_TIME);
//                 } catch (InterruptedException e) {
//                     e.printStackTrace();
//                 }

//                 myMediaPlayer.start();
//                 setDuration();

//                 MainActivity.selectedSong = position;
//                 MainActivity.getViewByPosition(position).setBackgroundColor(Color.RED);
//                 MainActivity.previousSong = MainActivity.getViewByPosition(position);
//             }
//         });


//         btnPrev.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 myMediaPlayer.stop();
//                 myMediaPlayer.reset();

//                 MainActivity.previousSong.setBackgroundColor(Color.WHITE);

//                 if(updateSeekBar.isAlive())
//                     updateSeekBar.interrupt();

//                 updateSeekBar = new Thread(){
//                     @Override
//                     public void run() {
//                         int totalDuration;

//                         totalDuration = myMediaPlayer.getDuration();

//                         currentPosition = 0;


//                         while(currentPosition < totalDuration){
//                             try{
//                                 sleep(SLEEP_TIME);
//                                 currentPosition = myMediaPlayer.getCurrentPosition();
//                                 songSeekbar.setProgress(currentPosition);

//                                 runOnUiThread(new Runnable() {
//                                     @Override
//                                     public void run() {
//                                         setSongProgress();
//                                     }
//                                 });
//                             }catch(InterruptedException e){
//                                 e.printStackTrace();
//                             }
//                         }
//                     }
//                 };

//                 position = (position > 0?(position-1):mySongs.size()-1);
//                 Uri u = Uri.parse(mySongs.get(position).toString());

//                 songName = mySongs.get(position).getName();
//                 songLabelName.setText(songName);

//                 try {
//                     myMediaPlayer.setDataSource(getApplicationContext(),u);
//                     myMediaPlayer.prepare();
//                 } catch (IOException e) {
//                     e.printStackTrace();
//                 }

//                 songSeekbar.setMax(myMediaPlayer.getDuration());
//                 updateSeekBar.start();

//                 try {
//                     Thread.currentThread().sleep(SLEEP_TIME);
//                 } catch (InterruptedException e) {
//                     e.printStackTrace();
//                 }

//                 myMediaPlayer.start();
//                 setDuration();

//                 MainActivity.selectedSong = position;
//                 MainActivity.getViewByPosition(position).setBackgroundColor(Color.RED);
//                 MainActivity.previousSong = MainActivity.getViewByPosition(position);
//             }
//         });
//     }

//     @Override
//     public boolean onOptionsItemSelected(@NonNull MenuItem item) {

//         if (item.getItemId() == android.R.id.home) {
//             onBackPressed();
//         }
//         return super.onOptionsItemSelected(item);
//     }

//     void setDuration(){
//         songSeekbar.setMax(myMediaPlayer.getDuration());
//         long minutes = TimeUnit.MILLISECONDS.toMinutes(myMediaPlayer.getDuration());
//         long seconds = TimeUnit.MILLISECONDS.toSeconds((myMediaPlayer.getDuration() - minutes*60*1000));

//         String time = String.format("%d:%d", minutes,seconds);
//         totalTime.setText(time);
//     }

//     void setSongProgress(){
//         long minutes = (currentPosition/1000)/60;
//         long seconds = (currentPosition/1000)%60;
//         String time = String.format("%d:%d",minutes,seconds);

//         timePassed.setText(time);

//     }
// }

package com.aizenangel.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SongPlayer extends AppCompatActivity {
    private TextView songLabelName, timePassed, totalTime;
    private SeekBar songSeekbar;
    private Button btnPrev, btnNext, btnPause;

    private static MediaPlayer myMediaPlayer;
    private String songName;
    private int position;
    private ArrayList<File> mySongs;
    private Thread updateSeekBar;
    public int currentPosition=0;

    private final int SLEEP_TIME = 100;

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
        timePassed = findViewById(R.id.SeekBarMinutes);
        totalTime = findViewById(R.id.MediaPlayerDuration);

        getSupportActionBar().setTitle("Now Playing: ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        updateSeekBar = new Thread(){
            @Override
            public void run() {

               while(true){

                   currentPosition = 0;
                   int totalDuration = (myMediaPlayer.getDuration()/1000)*1000;
                   while (currentPosition <= totalDuration) {
                       try {
                        sleep(SLEEP_TIME);
                        currentPosition = myMediaPlayer.getCurrentPosition();
                        songSeekbar.setProgress(currentPosition);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setSongProgress();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(currentPosition + " " + totalDuration);

                currentPosition = 0;
                myMediaPlayer.seekTo(0);
                myMediaPlayer.pause();
                btnPause.setBackgroundResource(R.drawable.icon_play);
              }
            }
        };

        if(myMediaPlayer != null){
            myMediaPlayer.reset();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        try{
            position = bundle.getInt("position", 0);
        }catch(NullPointerException e){
            position = 0;
        }

        mySongs = (ArrayList)bundle.getParcelableArrayList("songs");

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

        myMediaPlayer.start();
        setDuration();

        updateSeekBar.start();

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

                btnPause.setBackgroundResource(R.drawable.icon_pause);

                myMediaPlayer.stop();
                myMediaPlayer.reset();

                MainActivity.previousSong.setBackgroundColor(Color.WHITE);

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

                songSeekbar.setMax(myMediaPlayer.getDuration());

                myMediaPlayer.start();
                setDuration();

                MainActivity.selectedSong = position;
                MainActivity.getViewByPosition(position).setBackgroundColor(Color.RED);
                MainActivity.previousSong = MainActivity.getViewByPosition(position);
            }
        });


        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPause.setBackgroundResource(R.drawable.icon_pause);

                myMediaPlayer.stop();
                myMediaPlayer.reset();

                MainActivity.previousSong.setBackgroundColor(Color.WHITE);

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

                songSeekbar.setMax(myMediaPlayer.getDuration());

                myMediaPlayer.start();
                setDuration();

                MainActivity.selectedSong = position;
                MainActivity.getViewByPosition(position).setBackgroundColor(Color.RED);
                MainActivity.previousSong = MainActivity.getViewByPosition(position);
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

    void setDuration(){
        songSeekbar.setMax(myMediaPlayer.getDuration());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(myMediaPlayer.getDuration());
        long seconds = TimeUnit.MILLISECONDS.toSeconds((myMediaPlayer.getDuration() - minutes*60*1000));

        String time = String.format("%d:%d", minutes,seconds);
        totalTime.setText(time);
    }

    void setSongProgress(){
        long minutes = (currentPosition/1000)/60;
        long seconds = (currentPosition/1000)%60;
        String time = String.format("%d:%d",minutes,seconds);

        timePassed.setText(time);

    }
}
