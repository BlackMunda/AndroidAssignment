package com.example.q4photogallery;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import java.text.DateFormat;
import java.util.Date;

public class ImageDetailActivity extends AppCompatActivity {

    ImageView detailImageView;
    TextView detailsText;
    Button deleteBtn;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        detailImageView = findViewById(R.id.detailImageView);
        detailsText = findViewById(R.id.detailsText);
        deleteBtn = findViewById(R.id.deleteBtn);

        imageUri = Uri.parse(
                getIntent().getStringExtra("imageUri")
        );

        detailImageView.setImageURI(imageUri);

        DocumentFile file =
                DocumentFile.fromSingleUri(this, imageUri);

        String date =
                DateFormat.getDateTimeInstance()
                        .format(new Date(file.lastModified()));

        detailsText.setText(
                "Name: " + file.getName() +
                        "\nPath: " + imageUri +
                        "\nSize: " + file.length() + " bytes" +
                        "\nDate: " + date
        );

        deleteBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete")
                    .setMessage("Delete this image?")
                    .setPositiveButton("Yes", (d, w) -> {
                        file.delete();
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}