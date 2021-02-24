package com.example.flickrapp.async;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flickrapp.activity.MainActivity;
import com.example.flickrapp.adapter.MyAdapter;
import com.example.flickrapp.async.AsyncFlickrJSONDataForList;

import org.json.JSONObject;

public class GetImageOnClickListener implements View.OnClickListener {

    private final AppCompatActivity myActivity;

    public GetImageOnClickListener(AppCompatActivity mainActivity) {
        myActivity = mainActivity;
    }

    @Override
    public void onClick(View v) {
        Log.i("JFL", "Je suis dans le OnClick de getImage");
        AsyncTask<String, Void, JSONObject> task = new AsyncFlickrJSONData(myActivity);
        task.execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");
    }
}
