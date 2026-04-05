package com.example.q2mediaplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    VideoView videoView;
    EditText urlInput;
    Button openAudioBtn, openUrlBtn, playBtn, pauseBtn, stopBtn, restartBtn;

    MediaPlayer mediaPlayer;
    Uri audioUri;
    Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        urlInput = findViewById(R.id.urlInput);

        openAudioBtn = findViewById(R.id.openAudioBtn);
        openUrlBtn = findViewById(R.id.openUrlBtn);
        playBtn = findViewById(R.id.playBtn);
        pauseBtn = findViewById(R.id.pauseBtn);
        stopBtn = findViewById(R.id.stopBtn);
        restartBtn = findViewById(R.id.restartBtn);

        openAudioBtn.setOnClickListener(v -> openAudioFile());

        openUrlBtn.setOnClickListener(v -> {
            String url = urlInput.getText().toString();
            videoUri = Uri.parse(url);
            videoView.setVideoURI(videoUri);
        });

        playBtn.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.start();
            } else if (videoUri != null) {
                videoView.start();
            }
        });

        pauseBtn.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
            if (videoView.isPlaying()) {
                videoView.pause();
            }
        });

        stopBtn.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            videoView.stopPlayback();
        });

        restartBtn.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
            if (videoUri != null) {
                videoView.setVideoURI(videoUri);
                videoView.start();
            }
        });
    }

    private void openAudioFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            audioUri = data.getData();

            mediaPlayer = MediaPlayer.create(this, audioUri);
        }
    }
}