package com.stayyolo.picasso2glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;


public class Async extends AsyncTask<Void, Void, Bitmap> {

    private final Context mContext;

    private final ImageView mImageView;

    public Async(Context context, ImageView imgView) {
        mContext = context;
        mImageView = imgView;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            // this loads the image synchronously, this it can only be called using an AsyncTask
            // which doesn't run on the UI Thread
            return Picasso.with(mContext).load(MainActivity.url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mImageView.setImageBitmap(bitmap);
    }
}
