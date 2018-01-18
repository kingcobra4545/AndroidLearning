package com.possystems.kingcobra.newsworld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.possystems.kingcobra.newsworld.DataModel.DataModel;

import java.util.ArrayList;

/**
 * Created by KingCobra on 04/01/18.
 */



public class CoverStoryFragment extends Fragment {
    private static CoverStoryFragment instance;

    public static CoverStoryFragment getInstance(){
        if (instance==null)
            instance = new CoverStoryFragment();
        return instance;
    }
    public CoverStoryFragment(){}
    String TAG = "CoverStoryFragment";
    CustomAdapter adapter;
    ArrayList<DataModel> d;
    ListView list;
    Context context;
    private ProgressBar spinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cover_story, container, false);
        context = getActivity();
        //Log.i(TAG, "COntext - >" + context.toString());
        try {

            d = new ArrayList<>();
//            if(adapter==null)
            adapter = new CustomAdapter(d, context);
            Log.i(TAG, "Fragment called"+ "\n adapter size - >" + adapter.getCount());
            list = (ListView) rootView.findViewById(R.id.list_cover);
//            list.setAdapter(adapter);
            spinner = (ProgressBar)rootView.findViewById(R.id.progressBar1);
            //spinner.setVisibility(View.GONE);
            spinner.setVisibility(View.VISIBLE);


        }
        catch (Exception e){
            e.printStackTrace();
        }

        return rootView;
    }

    public void adapterNotify(final ArrayList<DataModel> dataModel, final Context context){
        //list.setAdapter(adapter);
        //this.adapter = adapter;
        CustomAdapter customAdapter = new CustomAdapter(dataModel, context);
        spinner.setVisibility(View.GONE);
        if(list.getAdapter()!=null) {
            Log.i(TAG, "adapter count " + list.getAdapter().getCount());
            if (list.getAdapter().getCount() < 1) {
                //Log.i(TAG, "adapter count " + list.getAdapter().getCount());
                list.setAdapter(customAdapter);
            }
        }
            else
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
    @Override
    public void onResume(){
        super.onResume();
        Log.i(TAG,"on resume");
        adapter.notifyDataSetChanged();
    }

    public class Receiver extends BroadcastReceiver{
        public Receiver(){}
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "On Receive ");

            //adapterNotify((ArrayList<DataModel>) intent.getSerializableExtra("dataModel"), context);
        }
    }
}