package com.example.flickrapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.flickrapp.adapter.MyAdapter;
import com.example.flickrapp.async.AsyncFlickrJSONData;
import com.example.flickrapp.async.AsyncFlickrJSONDataForList;
import com.example.flickrapp.async.GetImageOnClickListener;
import com.example.flickrapp.R;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //First button who print all images URL on the first page
        final Button button = findViewById(R.id.getButton);
        button.setOnClickListener(new GetImageOnClickListener(MainActivity.this){
            @Override
            public void onClick(View v) {
                Log.i("JFL", "Je suis dans le OnClick de getImage");
                AsyncTask<String, Void, JSONObject> task = new AsyncFlickrJSONData(MainActivity.this);
                task.execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");
            }
        });

        //Second button who launch our ListActivity
        final Button button2 = findViewById(R.id.getListActivity);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(i);
            }
        });
    }
}