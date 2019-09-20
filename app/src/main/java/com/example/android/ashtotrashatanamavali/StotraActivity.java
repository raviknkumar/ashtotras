package com.example.android.ashtotrashatanamavali;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class StotraActivity extends AppCompatActivity {



    float f=1.25f;
    float size;
    MediaPlayer mp;
    AudioManager mAudioManager;
    TextView dur,totdur,title;
    int rsrcId;
    SeekBar seekbar;
    private Handler myHandler;

    int forwardTime=10000,backwardTime=10000;
    int startDur=0,finalDur=0;
    int currpos;


    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mp.pause();
                mp.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mp.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stotra);

        mAudioManager = (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);


        ScrollView sc=(ScrollView)findViewById(R.id.scroll);
        sc.setVerticalScrollBarEnabled(false);

        Intent intent=getIntent();
        String stringExtra=intent.getStringExtra("text");
      int x=intent.getIntExtra("abc",0);

        Resources res = getResources();
        TextView tv=(TextView)findViewById(R.id.text2);
        String[] ash = res.getStringArray(R.array.ashtotra);
        tv.setText(ash[x]);
        rsrcId=intent.getIntExtra("audioRsrcId",R.raw.mysong);


        totdur=(TextView)findViewById(R.id.track_tot_dur);
        title=(TextView)findViewById(R.id.track_name);
        title.setText(intent.getStringExtra("title"));





    }

    public void incr(View v)
    {
        TextView tv=(TextView)findViewById(R.id.text2);
        size=tv.getTextSize();
        size+=5;

        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);

    }

    public void decr(View v)
    {
        TextView tv=(TextView)findViewById(R.id.text2);
        int size2=(int)tv.getTextSize();
        int size3=size2-5;
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,size3);

    }

    public void backwardSong(View v)
    {
        currpos=mp.getCurrentPosition();
        if(currpos-backwardTime>=0)
            mp.seekTo(currpos-backwardTime);
        else
            mp.seekTo(0);
    }

    public void pauseSong(View v)
    {
        mp.pause();
    }





    public void playSong(View v)
    {
        if(mp!=null)
        {
                int dur = mp.getCurrentPosition();
                finalDur=mp.getDuration();
                mp.seekTo(dur);
                mp.start();



        }
        else
            {
                    mAudioManager = (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                        mp = MediaPlayer.create(getApplicationContext(), rsrcId);
                        mp.start();

                        totdur.setText(String.format("%02d:%d",
                                TimeUnit.MILLISECONDS.toMinutes((long)mp.getDuration()),
                                TimeUnit.MILLISECONDS.toSeconds((long)mp.getDuration()
                                -TimeUnit.MILLISECONDS.toSeconds(
                                        TimeUnit.MILLISECONDS.toMinutes((long)mp.getDuration())
                                ))));

        }
    }

    public void forwardSong(View v)
    {
        currpos=mp.getCurrentPosition();
        if(currpos+forwardTime<=mp.getDuration())
            mp.seekTo(currpos+forwardTime);
        else
            mp.seekTo(mp.getDuration());
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mp != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mp.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mp= null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            /*if(Build.VERSION.SDK_INT>=26)
            mAudioManager.abandonAudioFocusRequest(mOnAudioFocusChangeListener); */
        }
    }

    public void onStop() {
        super.onStop();

        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }


}






