package com.stayyolo.picasso2glide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    public static final String url = "https://i.kinja-img.com/gawker-media/image/upload/s--SG3wh-0b--/c_fill,fl_progressive,g_north,h_358,q_80,w_636/egzd5m3abkogsqn3xmlw.jpg";

    private static final String tag = MainActivity.class.getSimpleName();

    private ImageView image;

    private ImageView round;

    private ImageView callback;

    private ImageView async;

    private ImageView invalidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGUI();

        loadImages();
    }

    private void initGUI() {
        image = (ImageView) findViewById(R.id.image);
        round = (ImageView) findViewById(R.id.round);
        callback = (ImageView) findViewById(R.id.callback);
        async = (ImageView) findViewById(R.id.async);
        invalidate = (ImageView) findViewById(R.id.invalidate);
    }

    private void loadImages() {
        // this enables logging, so you can see the logs as the requests are made
        Picasso.with(this).setLoggingEnabled(true);

        // this enables the indicators on the images, which informs us from where the image has been loaded
        // for example : network, disk or memory cache
        // for more info refer to the DEBUG INDICATORS section at http://square.github.io/picasso/
        Picasso.with(this).setIndicatorsEnabled(true);

        // plain simple load
        Picasso.with(this)
                .load(url)
                .into(image);

        // loads the image and then transforms it into a circular image
        Picasso.with(this)
                .load(url)
                .transform(new CircleTransform())
                .into(round);

        // calls the onSuccess callback when the image is successfully loaded
        Picasso.with(this)
                .load(url)
                .into(callback, new Callback() {

                    @Override
                    public void onSuccess() {

                        // creates an AsyncTask and loads the image there
                        Async asyncTask = new Async(MainActivity.this, async);
                        asyncTask.execute();

                        // invalidate the image cache
                        Picasso.with(MainActivity.this).invalidate(url);

                        // should load the image from network
                        Picasso.with(MainActivity.this).load(url)
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .into(invalidate);
                    }

                    @Override
                    public void onError() {
                        Log.e(tag, "error!");
                    }
                });


    }
}
