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

    // CONSTRUCTOR
    public MyAdapter(Context context){
        this.context = context;
        items = new Vector<String>();
    }

    // ADD FUNCTION FOR THE LIST
    public void add(String string){
        this.items.add(string);
    }

    // BASE ADAPTER METHOD
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
        /* We create the display of our row like in our bitmaplayout **/
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.bitmaplayout, parent, false);
        }
        //Find the image view
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        //Create our RequestQueue instance
        RequestQueue queue = MySingleton.getInstance(image.getContext()).getRequestQueue();
        //Initialise a new ImageRequest
        ImageRequest request = new ImageRequest(getItem(position).toString(),
                (Response.Listener<Bitmap>) image::setImageBitmap, // When our response is catch we display the internal image saved to our image view
                200,
                200,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    //If we catch an error response
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("JFL", "Image Load Error: ");
                    }
        });
        //Add Image request to the RequestQueue
        queue.add(request);
        // returns the view for the current row
        return convertView;
    }
}