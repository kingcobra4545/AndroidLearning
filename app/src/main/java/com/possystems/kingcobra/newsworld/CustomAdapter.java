package com.possystems.kingcobra.newsworld;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.possystems.kingcobra.newsworld.DataModel.DataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by KingCobra on 24/11/17.
 */

public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{
    String TAG = "CustomAdapter";

    private ArrayList<DataModel> dataSet;
    Context mContext;


    // View lookup cache
    private static class ViewHolder {
        TextView txtTitle, txtDesc, txtAuthor;
        TextView txtType;
        TextView txtVersion, vehicleUsed;
        ImageView info;
        String url;
    }

    /*private static final CustomAdapter instance = new CustomAdapter();

    private CustomAdapter(){}

    public static CustomAdapter getInstance(){
        return instance;
    }*/

    public CustomAdapter(ArrayList<DataModel> data, Context context) {

        super(context, R.layout.main_row_item, data);
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
            case R.layout.main_row_item:
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
    public View getView(int position, View convertView, ViewGroup parent) {




        // Get the data item for this position
        DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        Logger.i(TAG, "get view called" + "\nfrom thread - > " +Thread.currentThread().getId() +
                "\n For position" + position +  " category -- > " + dataModel.getTitle());
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.main_row_item, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.txtDesc = (TextView) convertView.findViewById(R.id.news_desc);
            viewHolder.txtAuthor = (TextView) convertView.findViewById(R.id.news_author);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.imageView);
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
        viewHolder.txtAuthor.setText("- " + dataModel.getAuthor());
        viewHolder.txtAuthor.setTextColor(Color.BLACK);
        viewHolder.txtDesc.setText(dataModel.getDescription());
        viewHolder.txtDesc.setTextColor(Color.BLACK);
        viewHolder.url = dataModel.getUrl();

      /*  if(dataModel.getAuthor()==null)
            viewHolder.txtAuthor.setVisibility(View.GONE);
        if(dataModel.getTitle()==null)
            viewHolder.txtTitle.setVisibility(View.GONE);
        if(dataModel.getDescription()==null)
            viewHolder.txtDesc.setVisibility(View.GONE);*/
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

    /*private void getThumbNailFromPhotoLocation(final String photoLocation, final ImageView thumbNailImageView) {
        new Thread( new Runnable() {
            @Override
            public void run() {
                Logger.i(TAG, "Compression being");
                final int THUMBSIZE = 64;
                Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(photoLocation), THUMBSIZE, THUMBSIZE);
                Logger.i(TAG, "Compression end");
                imageLoader.DisplayImage();
                thumbNailImageView.setImageBitmap(ThumbImage);
            }
        }).start();


    }*/
}