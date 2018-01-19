package com.possystems.kingcobra.newsworld;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeInDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.mindorks.placeholderview.annotations.swipe.SwipeTouch;
import com.possystems.kingcobra.newsworld.DataModel.DataModel;

import java.text.SimpleDateFormat;
import java.util.Date;

@Layout(R.layout.card_view)
public class TinderCard {

    String TAG = "TinderCard";

    @View(R.id.imageView)
    private ImageView profileImageView;

    @View(R.id.titleTxt)
    private TextView titleTxt;

    @View(R.id.descriptionTxt)
    private TextView descriptionTxt;

    @View(R.id.news_source_and_time)
    private TextView newSourceAndTime;

    private DataModel mDataModel;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public TinderCard(Context context, DataModel dataModel, SwipePlaceHolderView swipeView) {
        mContext = context;
        mDataModel = dataModel;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved(){
        Glide.with(mContext).load(mDataModel.getImageURL()).into(profileImageView);
        titleTxt.setText(mDataModel.getTitle());// + ", " + mDataModel.getAge());
        descriptionTxt.setText(mDataModel.getDescription());
        String authorAndTime = mDataModel.getAuthor() + " • " + getTimeDifference(mDataModel.getPublishedAT());
        newSourceAndTime.setText(authorAndTime);
        Logger.i(TAG, "on Resolved");



    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeTouch
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeOutDirectional
    private void onSwipeOutDirectional(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeInDirectional
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
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