package com.example.memeshareapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.drawable.Drawable;

import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;


import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {
    String currurl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        loadMeme();

    }
    private void loadMeme(){
        ImageView image;
        ProgressBar p;
        p=findViewById(R.id.progressBar);
        image=findViewById(R.id.imageView);
        p.setVisibility(View.VISIBLE);
//        final TextView textView = (TextView) findViewById(R.id.text);
    // ...

    // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        currurl ="https://meme-api.herokuapp.com/gimme";

    // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, currurl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        String url= null;
                        try {
                            currurl = response.getString("url");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Glide.with(MainActivity2.this).load(currurl).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                p.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                p.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(image);
                        p.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                p.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity2.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }, 5000);


                    }
                });

    // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    public void nextbtn(View view) {
        loadMeme();
    }

    public void shareMeme(View view) {
        Intent in=new Intent(Intent.ACTION_SEND);
//        Uri ur=Uri.parse(url);
        in.setType("text/plain");
        in.putExtra(Intent.EXTRA_TEXT,"hey check this meme i got from Reddit"+currurl);
        Intent ch=Intent.createChooser(in,"sharing this meme using....");
        startActivity(ch);

    }


}