package com.lehuuquynhnhi.k234111e_mobile;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class FontAndMusicActivity extends AppCompatActivity {

    Button btnPlayAudio1, btnPlayAudio2;
    TextView txtTitle;
    ListView lvFontAndMusic;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_and_music);

        addViews();
        addEvents();
        displayData();
    }

    private void displayData() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            data.add("Item " + i + "\nSub Item " + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        lvFontAndMusic.setAdapter(adapter);
    }

    private void addEvents() {
        btnPlayAudio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic("Music/alexis_gaming_cam-bell-notification-337658.mp3");
            }
        });

        btnPlayAudio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic("Music/universfield-cartoon-fail-trumpet-278822.mp3");
            }
        });
    }

    private void playMusic(String fileName) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            mediaPlayer = new MediaPlayer();
            AssetFileDescriptor afd = getAssets().openFd(fileName);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error playing audio: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addViews() {
        btnPlayAudio1 = findViewById(R.id.btnPlayAudio1);
        btnPlayAudio2 = findViewById(R.id.btnPlayAudio2);
        txtTitle = findViewById(R.id.txtTitle);
        lvFontAndMusic = findViewById(R.id.lvFontAndMusic);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
