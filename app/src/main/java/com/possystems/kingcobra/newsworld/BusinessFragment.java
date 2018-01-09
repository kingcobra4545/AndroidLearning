package com.possystems.kingcobra.newsworld;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.possystems.kingcobra.newsworld.DataModel.DataModel;

import java.util.ArrayList;

/**
 * Created by KingCobra on 04/01/18.
 */



public class BusinessFragment extends Fragment {
    private static BusinessFragment instance;
    public static BusinessFragment getInstance(){
        if (instance==null)
            instance = new BusinessFragment();
        return instance;
    }
    String TAG = "BusinessFragment";
    CustomAdapter adapter;
    ArrayList<DataModel> d;
    ListView list;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_business, container, false);
        context = getActivity();
        //Log.i(TAG, "COntext - >" + context.toString());
        try {

            d = new ArrayList<>();
            adapter = new CustomAdapter(d, context);
            Log.i(TAG, "Fragment called"+ "\n adapter size - >" + adapter.getCount());
            list = (ListView) rootView.findViewById(R.id.list_busi);
            list.setAdapter(adapter);




        }
        catch (Exception e){
            e.printStackTrace();
        }
        return rootView;
    }

    public void adapterNotify(final ArrayList<DataModel> dataModel, final Context context) {
        CustomAdapter customAdapter = new CustomAdapter(dataModel, context);
        list.setAdapter(customAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "1.Position from main--> "  + view.getTag() + "\n id - > " + id);
                Log.i(TAG, "2.Position from main--> "  + position);
                Log.i(TAG, "Title - > " + dataModel.get(position).getUrl());

                NewsWebView newsWebView = new NewsWebView();
                newsWebView.createWebViewForNewsItem(dataModel.get(position).getUrl(), context);

            }
        });
        //this.adapter.notifyDataSetChanged();
    }
}