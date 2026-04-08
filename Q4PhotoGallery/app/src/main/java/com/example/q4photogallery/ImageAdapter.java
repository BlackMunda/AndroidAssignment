package com.example.q4photogallery;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    Context context;
    ArrayList<Uri> imageUris;

    public ImageAdapter(Context context,
                        ArrayList<Uri> imageUris) {
        this.context = context;
        this.imageUris = imageUris;
    }

    @Override
    public int getCount() {
        return imageUris.size();
    }

    @Override
    public Object getItem(int position) {
        return imageUris.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position,
                        View convertView,
                        ViewGroup parent) {

        ImageView imageView =
                new ImageView(context);

        imageView.setImageURI(imageUris.get(position));
        imageView.setLayoutParams(
                new ViewGroup.LayoutParams(400, 400)
        );
        imageView.setScaleType(
                ImageView.ScaleType.CENTER_CROP
        );

        return imageView;
    }
}