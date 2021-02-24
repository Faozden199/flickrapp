package com.example.flickrapp.async;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flickrapp.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {

    private final AppCompatActivity myActivity;
    public AsyncBitmapDownloader(AppCompatActivity mainActivity) {
        myActivity = mainActivity;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        Bitmap bm = null;
        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection(); // Open
            InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // Stream
            bm = BitmapFactory.decodeStream(in);
            in.close();
        }
        catch (MalformedURLException e) { e.printStackTrace(); }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Log.i("JFL", "Image received !");
        Log.i("JFL", bitmap.toString());

        //When our image is download we update our ImageView
        ImageView image = (ImageView) myActivity.findViewById(R.id.imageHolder);
        image.setImageBitmap(bitmap);
    }
}
