package com.possystems.kingcobra.newsworld;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by KingCobra on 12/12/17.
 */

public class NewsFragment extends Fragment {
    TextView textView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,
                container, false);
        textView = (TextView) view.findViewById(R.id.detailsText);
        //setText("jinggi chaka");
        return view;
    }

    public void setText(String text) {
        TextView view = (TextView) getView().findViewById(R.id.detailsText);
        view.setText(text);
    }
    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);

        Bundle bundle = getArguments();
        textView.setText(bundle.getString("desc"));


    }
}
