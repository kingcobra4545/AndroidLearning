package com.possystems.kingcobra.newsworld;

import android.content.Context;
import android.content.Intent;

/**
 * Created by KingCobra on 07/01/18.
 */

public class NewsWebView {

    public NewsWebView (){}


    public void createWebViewForNewsItem(String url, Context context) {
        Intent webActivity = new Intent(context, WebViewActivity.class);
        webActivity.putExtra("url", url);
        webActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(webActivity);

    }
}
