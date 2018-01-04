package com.possystems.kingcobra.newsworld;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.possystems.kingcobra.newsworld.DataModel.DataModel;

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
        Log.i(TAG, "Adapter Called");

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Log.i(TAG, "pos -- " + position);
        Object object= getItem(position);
        DataModel dataModel=(DataModel)object;

        switch (v.getId())
        {
            case R.layout.main_row_item:
                //((MainActivity) mContext).openCameraForActivityResultAnotherMethod2((Integer) v.getTag());
                Log.i(TAG, "tag--" + v.getTag());

                break;
        }
    }

    private int lastPosition = -1;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {




        // Get the data item for this position
        DataModel dataModel = getItem(position);
        Log.i(TAG, "get view called" + "\nfrom thread - > " +Thread.currentThread().getId() +
                "\n For position" + position +  " category -- > " + dataModel.getTitle());
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.main_row_item, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.txtDesc = (TextView) convertView.findViewById(R.id.news_desc);
            viewHolder.txtAuthor = (TextView) convertView.findViewById(R.id.news_author);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

            //Log.i(TAG, "\nsetting as follows - >  " + dataModel.getTitle() + "\n" + dataModel.getDescription());
        viewHolder.txtTitle.setText(dataModel.getTitle());
        viewHolder.txtAuthor.setText("- " + dataModel.getAuthor());
        viewHolder.txtAuthor.setTextColor(Color.BLACK);
        viewHolder.txtDesc.setText(dataModel.getDescription());
        viewHolder.txtDesc.setTextColor(Color.BLACK);
        Log.i(TAG, "get view finished");
        return convertView;
    }

    /*private void getThumbNailFromPhotoLocation(final String photoLocation, final ImageView thumbNailImageView) {
        new Thread( new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Compression being");
                final int THUMBSIZE = 64;
                Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(photoLocation), THUMBSIZE, THUMBSIZE);
                Log.i(TAG, "Compression end");
                imageLoader.DisplayImage();
                thumbNailImageView.setImageBitmap(ThumbImage);
            }
        }).start();


    }*/
}