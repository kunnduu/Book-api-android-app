package com.example.book;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Bookreport {

    public Bookreport(Context context) {
        this.context = context;
    }
    Context context;
    public interface GetDetail{
        void onError(String message);
        void onResponse(List<details> details); //The response that we want


    }
    public void getbook(String k, GetDetail dd){
        List<details> detail = new ArrayList<>();
        String url = "https://www.googleapis.com/books/v1/volumes?q="+k+"+intitle";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray item = response.getJSONArray("items");
                    for (int i = 0; i < item.length(); i++) {
                        details title = new details(); // calling the details class
                        JSONObject it1 = item.getJSONObject(i);
                        JSONObject vol = it1.getJSONObject("volumeInfo");
                        title.setTitle(vol.getString("title"));
                        detail.add(title); // adding the object in the arraylist
                    }
                    dd.onResponse(detail);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Net not connected /Something wrong", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(req);
    }

}
