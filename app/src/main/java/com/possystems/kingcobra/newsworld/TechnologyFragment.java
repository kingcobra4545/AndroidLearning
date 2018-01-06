package com.possystems.kingcobra.newsworld;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.possystems.kingcobra.newsworld.DataModel.DataModel;

import java.util.ArrayList;

/**
 * Created by KingCobra on 04/01/18.
 */


public class TechnologyFragment extends Fragment {
    String TAG = "CoverStoryFragment";
    CustomAdapter adapter;
    ArrayList<DataModel> d;
    ListView list;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_technology, container, false);
       /* d = new ArrayList<>();
      context = getActivity();
        adapter = new CustomAdapter(d, context);
        Log.i(TAG, "Fragment called"+ "\n adapter size - >" + adapter.getCount());
        list = (ListView) rootView.findViewById(R.id.list);
        list.setAdapter(adapter);*/
        return rootView;
    }
}