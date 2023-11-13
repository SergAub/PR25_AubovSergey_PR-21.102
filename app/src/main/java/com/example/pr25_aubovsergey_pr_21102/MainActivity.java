package com.example.pr25_aubovsergey_pr_21102;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.annotation.SuppressLint;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity{

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mCacodemonSound, mArchvileSound, mPinkySound, mBaronOfHellSound, mRevenantSound, mMancubusSound;
    private int mStreamID;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ivCacodemon = findViewById(R.id.cacodemon);
        ImageView ivArchvile = findViewById(R.id.archvile);
        ImageView ivPinky = findViewById(R.id.pinky);
        ImageView ivBaronOfHell = findViewById(R.id.baronofhell);
        ImageView ivRevenant = findViewById(R.id.revenant);
        ImageView ivMancubus = findViewById(R.id.mancubus);

        ivCacodemon.setOnClickListener(v -> {playSound(mCacodemonSound);});
        ivArchvile.setOnClickListener(v -> {playSound(mArchvileSound);});
        ivPinky.setOnClickListener(v -> {playSound(mPinkySound);});
        ivBaronOfHell.setOnClickListener((v -> {playSound(mBaronOfHellSound);}));
        ivRevenant.setOnClickListener(v -> {playSound(mRevenantSound);});
        ivMancubus.setOnClickListener(v -> {playSound(mMancubusSound);});
    }

    private void createSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName, Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createSoundPool();

        mAssetManager = getAssets();

        mCacodemonSound = loadSound("cacodemon.mp3");
        mArchvileSound = loadSound("archvile.mp3");
        mPinkySound = loadSound("demon.mp3");
        mBaronOfHellSound = loadSound("baron.mp3");
        mRevenantSound = loadSound("revenant.mp3");
        mMancubusSound = loadSound("mancubus.mp3");

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }
}