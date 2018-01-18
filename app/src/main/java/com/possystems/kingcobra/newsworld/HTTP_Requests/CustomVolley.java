package com.possystems.kingcobra.newsworld.HTTP_Requests;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.possystems.kingcobra.newsworld.CustomAdapter;
import com.possystems.kingcobra.newsworld.DataModel.DataModel;
import com.possystems.kingcobra.newsworld.NewsApiJsonParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by KingCobra on 10/12/17.
 */

public class CustomVolley {
    ArrayList<DataModel> d = new ArrayList<>();
    Context context;
    CustomAdapter adapter;
    ListView listView;

    public CustomVolley(Context context){
        this.context = context;
    }
    public CustomVolley(Context context, ListView listView){

        this.context = context;
        this.listView = listView;
    }


    public JSONObject makeRequest(String url, final ListView list, final String queries){
        Log.i(TAG, "Making a http req using volley");
        RequestQueue queue = Volley.newRequestQueue(context);

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.i(TAG, "Got the JSON bud" + "\n - >" + response );
                        NewsApiJsonParser affiliateURLJsonParser = new NewsApiJsonParser(response);
                         affiliateURLJsonParser.firstResponseParser(context, list, queries);
                        //affiliateURLJsonParser.firstResponseGsonParser(context, list, queries);



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "No JSON for you");
                NewsApiJsonParser affiliateURLJsonParser = new NewsApiJsonParser();
                affiliateURLJsonParser.noResponseHandler(context, list, queries);
            }
        }){
         /*   @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put(FlippyConstants.fkIDString, FlippyConstants.fkID);
                headers.put(FlippyConstants.fkTokenString, FlippyConstants.fkToken);
                return headers;
            }*/
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        return null;
    }

    private void writeDataToDB(ArrayList<HashMap<String, String>> mainList) {

    }

/*    public String makeRequestForACategory(String url, final String category){
        RequestQueue queue = Volley.newRequestQueue(context);



// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "Got the JSON 2nd time bud");
                        Log.i(TAG, "Response----> " + response);



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "No JSON for " + category);
            }
        }){
            *//*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put(FlippyConstants.fkIDString, FlippyConstants.fkID);
                headers.put(FlippyConstants.fkTokenString, FlippyConstants.fkToken);
                return headers;
            }*//*
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        return null;

    }*/

/*    private void updateUi() {
        // Get a handler that can be used to post to the main thread
        Handler mainHandler = new Handler(context.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
               *//* MainActivity mainActivity = new MainActivity();
                mainActivity.setAdapterForCategories(d, context);*//*
                for (DataModel d1:
                        d) {
                    Log.i(TAG, "d containing --> " + d1.getCategory());
                }
                 MainActivity mainActivity = new MainActivity();
                mainActivity.setAdapterForCategories(d, context);
                adapter = new CustomAdapter(d, context);
                listView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();


            } // This is your code
        };
        mainHandler.post(myRunnable);
    }*/
}
