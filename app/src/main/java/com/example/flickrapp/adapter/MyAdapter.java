package com.example.flickrapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.flickrapp.R;
import com.example.flickrapp.singleton.MySingleton;

import java.util.Vector;

public class MyAdapter extends BaseAdapter {
    private final Context context; //context
    private final Vector<String> items; //data source of the list adapter

    public MyAdapter(Context context){
        this.context = context;
        items = new Vector<String>();
    }

    public void add(String string){
        this.items.add(string);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.bitmaplayout, parent, false);
        }
        /*// get the TextView for item name and item description
        TextView textViewItemName = (TextView) convertView.findViewById(R.id.text_view_item_name);
        //sets the text for item name and item description from the current item object
        textViewItemName.setText(getItem(position).toString());*/

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        RequestQueue queue = MySingleton.getInstance(image.getContext()).getRequestQueue();

        ImageRequest request = new ImageRequest(getItem(position).toString(),
                (Response.Listener<Bitmap>) image::setImageBitmap, 200, 200, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("JFL", "Image Load Error: ");
            }
        });
        queue.add(request);
        // returns the view for the current row
        return convertView;
    }
}


/**

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

 */