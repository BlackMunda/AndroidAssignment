package com.example.q4photogallery;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.GridView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;

import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button selectFolderBtn, captureBtn;
    GridView gridView;

    Uri selectedFolderUri;
    ArrayList<Uri> imageUris = new ArrayList<>();
    ImageAdapter adapter;

    ActivityResultLauncher<Intent> folderPickerLauncher;
    ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectFolderBtn = findViewById(R.id.selectFolderBtn);
        captureBtn = findViewById(R.id.captureBtn);
        gridView = findViewById(R.id.gridView);

        adapter = new ImageAdapter(this, imageUris);
        gridView.setAdapter(adapter);

        folderPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        selectedFolderUri = result.getData().getData();
                        loadImagesFromFolder();
                    }
                }
        );

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK &&
                            result.getData() != null &&
                            selectedFolderUri != null) {

                        Bitmap bitmap = (Bitmap) result.getData()
                                .getExtras()
                                .get("data");

                        saveImageToFolder(bitmap);
                    }
                }
        );

        selectFolderBtn.setOnClickListener(v -> selectFolder());

        captureBtn.setOnClickListener(v -> checkPermission());

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent =
                    new Intent(this, ImageDetailActivity.class);

            intent.putExtra(
                    "imageUri",
                    imageUris.get(position).toString()
            );

            startActivity(intent);
        });
    }

    private void selectFolder() {
        Intent intent =
                new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);

        folderPickerLauncher.launch(intent);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA},
                    101
            );
        }
    }

    private void openCamera() {
        Intent intent =
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        cameraLauncher.launch(intent);
    }

    private void saveImageToFolder(Bitmap bitmap) {
        try {
            DocumentFile folder =
                    DocumentFile.fromTreeUri(
                            this,
                            selectedFolderUri
                    );

            DocumentFile file = folder.createFile(
                    "image/jpeg",
                    "IMG_" + System.currentTimeMillis()
            );

            OutputStream out =
                    getContentResolver()
                            .openOutputStream(file.getUri());

            bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    100,
                    out
            );

            out.close();

            loadImagesFromFolder();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadImagesFromFolder() {
        imageUris.clear();

        DocumentFile folder =
                DocumentFile.fromTreeUri(
                        this,
                        selectedFolderUri
                );

        for (DocumentFile file : folder.listFiles()) {
            if (file.getType() != null &&
                    file.getType().startsWith("image")) {
                imageUris.add(file.getUri());
            }
        }

        adapter.notifyDataSetChanged();
    }
}