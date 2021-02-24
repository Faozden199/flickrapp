 package com.example.flickrapp.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.flickrapp.adapter.MyAdapter;

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

public class AsyncFlickrJSONDataForList extends AsyncTask<String , Void , JSONObject> {

    // This adapter will be link with our list before the task execute
    private final MyAdapter adapter;
    public AsyncFlickrJSONDataForList(MyAdapter adapter) {
        this.adapter =adapter;
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

        try {
            JSONArray items = jsonObject.getJSONArray("items");
            for (int i = 0; i<items.length(); i++)
            {
                Log.i("Notify", i + " : notify changed");
                //We add the object in the Adapter Vector
                adapter.add(items.getJSONObject(i).getJSONObject("media").getString("m"));
                // We update the ListView
                adapter.notifyDataSetChanged();
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
