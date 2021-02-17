package com.example.flickrapp.async;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flickrapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AsyncFlickrJSONData extends AsyncTask<String , Void , JSONObject> {

    private AppCompatActivity myActivity;

    public AsyncFlickrJSONData(AppCompatActivity mainActivity) {
        myActivity = mainActivity;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject s = null;
        URL url = null;
        try {
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                s = readStream(in);
                Log.i("JFL", "doInBackground");
            }finally{
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        Log.i("JFL", "OnPostExecute");
        Log.i("JFL", jsonObject.toString());
        ListView list = (ListView)myActivity.findViewById(R.id.list);
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(myActivity.getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);

        // Here, you set the data in your ListView
        list.setAdapter(adapter);

        try {
            JSONArray items = jsonObject.getJSONArray("items");
            for (int i = 0; i<items.length(); i++)
            {
                JSONObject flickr_entry = items.getJSONObject(i);
                String urlmedia = flickr_entry.getJSONObject("media").getString("m");
                Log.i("JFL", "URL media: " + urlmedia);
                // Downloading image
                AsyncBitmapDownloader abd = new AsyncBitmapDownloader();
                arrayList.add(urlmedia);
                adapter.notifyDataSetChanged();
                abd.execute(urlmedia);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private JSONObject readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            // Extracting the JSON object from the String
            StringBuilder sb = new StringBuilder();
            sb.append(bo.toString());
            String jsonextracted = sb.substring("jsonFlickrFeed(".length(), sb.length() - 1);
            return new JSONObject(jsonextracted);
        } catch (IOException | JSONException e) {
            return new JSONObject();
        }
    }
}
