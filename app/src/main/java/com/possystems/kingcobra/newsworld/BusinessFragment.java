package com.possystems.kingcobra.newsworld;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.possystems.kingcobra.newsworld.DataModel.DataModel;

import java.util.ArrayList;

/**
 * Created by KingCobra on 04/01/18.
 */



public class BusinessFragment extends Fragment {
    private static BusinessFragment instance;
    VerticalViewPager verticalViewPager;
    private ProgressBar spinner;
    public static BusinessFragment getInstance(){
        if (instance==null)
            instance = new BusinessFragment();
        return instance;
    }
    String TAG = "BusinessFragment";
    CustomAdapter_2 adapter;
    ArrayList<DataModel> d;
    ListView list;
    private SwipePlaceHolderView mSwipeView;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.main_layout_1, container, false);
        context = getActivity();
        //Log.i(TAG, "COntext - >" + context.toString());
        try {

            d = new ArrayList<>();
            /*
//            if(adapter.getCount()<=0)
            adapter = new CustomAdapter_2(d, context);
            Log.i(TAG, "Fragment called"+ "\n adapter size - >" + adapter.getCount());
            //list = (ListView) rootView.findViewById(R.id.list_busi);
            mSwipeView = (SwipePlaceHolderView)rootView.findViewById(R.id.swipeView);
            //list.setAdapter(adapter);


            spinner = (ProgressBar)rootView.findViewById(R.id.progressBar1);
            //spinner.setVisibility(View.GONE);
            spinner.setVisibility(View.VISIBLE);*/



            verticalViewPager = (VerticalViewPager) rootView.findViewById(R.id.verticleViewPager);



        }
        catch (Exception e){
            e.printStackTrace();
        }
        return rootView;
    }

    public void adapterNotify(final ArrayList<DataModel> dataModelList, final Context context) {

        /*for(DataModel dataModel : dataModelList){
            mSwipeView.addView(new TinderCard(context, dataModel, mSwipeView));
        }*/
        Logger.i(TAG, "Setting adapter");
        verticalViewPager.setAdapter(new VerticlePagerAdapter(context,dataModelList));
        /*CustomAdapter_2 customAdapter = new CustomAdapter_2(dataModel, context);
        if(list.getAdapter()!=null) {
            Log.i(TAG, "adapter count " + list.getAdapter().getCount());
            if (list.getAdapter().getCount() < 1) {
                //Log.i(TAG, "adapter count " + list.getAdapter().getCount());
                list.setAdapter(customAdapter);
            }
        }
        else
            list.setAdapter(customAdapter);
        spinner.setVisibility(View.GONE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "1.Position from main--> "  + view.getTag() + "\n id - > " + id);
                Log.i(TAG, "2.Position from main--> "  + position);
                Log.i(TAG, "URL - > " + dataModel.get(position).getUrl());

                NewsWebView newsWebView = new NewsWebView();
                newsWebView.createWebViewForNewsItem(dataModel.get(position).getUrl(), context);

            }
        });
        //this.adapter.notifyDataSetChanged();*/
    }
}