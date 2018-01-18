package com.possystems.kingcobra.newsworld;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.possystems.kingcobra.newsworld.DataModel.DataModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.possystems.kingcobra.newsworld.R.id.imageView;
import static com.possystems.kingcobra.newsworld.R.id.options_button_on_image;

/**
 * Created by KingCobra on 24/11/17.
 */

public class CustomAdapter_2 extends ArrayAdapter<DataModel> implements View.OnClickListener{
    String TAG = "CustomAdapter";

    private ArrayList<DataModel> dataSet;
    Context mContext;


    // View lookup cache
    private static class ViewHolder {
        TextView txtTitle, txtDesc, txtAuthor, txtSourceAndTime;
        ImageView info;
        String url;
        Button optionButton;
    }

    /*private static final CustomAdapter instance = new CustomAdapter();

    private CustomAdapter(){}

    public static CustomAdapter getInstance(){
        return instance;
    }*/

    public CustomAdapter_2(ArrayList<DataModel> data, Context context) {

        super(context, R.layout.card_view, data);
        this.dataSet = data;
        this.mContext=context;
        Logger.i(TAG, "Adapter Called" + "\n Data Size - > " + data.size());

    }
    /*@Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
    {

        int pos=(Integer) v.getTag();
        Logger.i(TAG, "pos -- " + pos);
        Logger.i(TAG, "position -- " + position);
        // Get Person "behind" the clicked item
        //Person p = (Person) myListView.getItemAtPosition(position);

        // Logger the fields to check if we got the info we want
        //Logger.i("SomeTag", "Persons name: " + p.name);
        //Logger.i("SomeTag", "Persons id : " + p.person_id);

        // Do something with the data. For example, passing them to a new Activity

        *//*Intent i = new Intent(MainActivity.this, NewActivity.class);
        i.putExtra("person_id", p.person_id);
        i.putExtra("person_name", p.name);

        MainActivity.this.startActivity(i);*//*
    }*/
    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Logger.i(TAG, "pos -- " + position);
        Object object= getItem(position);
        DataModel dataModel=(DataModel)object;

        switch (v.getId())
        {
            case R.layout.card_view:
                //((MainActivity) mContext).openCameraForActivityResultAnotherMethod2((Integer) v.getTag());
                Logger.i(TAG, "tag--" + v.getTag());

                break;
        }
    }

    private int lastPosition = -1;

    public interface OnDataChangeListener{
        public void onDataChanged(DataModel dataModel);
    }

    OnDataChangeListener mOnDataChangeListener;
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }
    private void doButtonOneClickActions(DataModel dataModel) {

        if(mOnDataChangeListener != null){
            mOnDataChangeListener.onDataChanged(dataModel);
        }
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {




        // Get the data item for this position
        final DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        Logger.i(TAG, "get view called" + "\nfrom thread - > " +Thread.currentThread().getId() +
                "\n For position" + position +  " category -- > " + dataModel.getTitle());
        final ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.card_view, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.titleTxt);
            viewHolder.txtDesc = (TextView) convertView.findViewById(R.id.descriptionTxt);
            //viewHolder.txtAuthor = (TextView) convertView.findViewById(R.id.news_author);
            viewHolder.txtSourceAndTime= (TextView) convertView.findViewById(R.id.news_source_and_time);
            viewHolder.info = (ImageView) convertView.findViewById(imageView);
            viewHolder.optionButton = (Button) convertView.findViewById(options_button_on_image);
            viewHolder.url = "";
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

            //Logger.i(TAG, "\nsetting as follows - >  " + dataModel.getTitle() + "\n" + dataModel.getDescription());
        viewHolder.txtTitle.setText(dataModel.getTitle());
        //viewHolder.txtAuthor.setText("- " + dataModel.getAuthor());
        String authorAndTime = dataModel.getAuthor() + " • " + getTimeDifference(dataModel.getPublishedAT());
        Log.i(TAG, "Author and time - > " + authorAndTime);
        viewHolder.txtSourceAndTime.setText(authorAndTime);
        //viewHolder.txtAuthor.setTextColor(Color.BLACK);
        viewHolder.txtDesc.setText(dataModel.getDescription());
        viewHolder.txtDesc.setTextColor(Color.BLACK);
        viewHolder.url = dataModel.getUrl();
        viewHolder.optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.post(new Runnable() {
                    @Override
                    public void run() {
                        showPopupMenu(v);
                    }
                });}

            private void showPopupMenu(View v) {

                PopupMenu menu = new PopupMenu(mContext, v);
                menu.getMenuInflater().inflate(R.menu.option_menu_each_item_in_list, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Log.i(TAG, "item clicked - >   " + item.getTitle());
                        return false;
                    }
                });
                menu.show();
            }
        });

        try {
            Logger.i(TAG, "for image url - >>" + dataModel.getImageURL());
            Picasso.with(mContext).load(dataModel.getImageURL()).into(viewHolder.info);



        }
        catch (Exception e)
        {
            Logger.i(TAG, "for image url - >>" + dataModel.getImageURL());

            e.printStackTrace();
        }
        Logger.i(TAG, "get view finished");
        return convertView;
    }

    private String getTimeDifference(String publishedAT) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date pubDate = null;
        try {
            pubDate = sdf.parse(publishedAT);

            Long pubTimeDiffToNow = - pubDate.getTime() + System.currentTimeMillis();
            Long pubHours = pubTimeDiffToNow/(1000 * 60 * 60);
            Long mins = pubTimeDiffToNow % (1000*60*60);
            if(pubHours<1)
                if (mins>59 || mins<1) {
                    Log.i(TAG, " Hours - " + pubHours + "   mins - " + mins  );
                    return "Sometime Ago";
                }
            else
                return mins.toString()+"m";
            else if (pubHours>24)
            {
                Log.i(TAG, " Hours - " + pubHours + "   mins - " + mins  );
                return String.valueOf(pubHours/24) + "d";
            }
                else
                    return pubHours.toString()+"h";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}