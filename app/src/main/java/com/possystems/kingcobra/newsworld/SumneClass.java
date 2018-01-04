package com.possystems.kingcobra.newsworld;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by KingCobra on 13/12/17.
 */

public  class  SumneClass extends IntentService{
    String TAG = "SUmneClass";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SumneClass(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent.getAction().equals("hellow")) Log.i(TAG, "Intent service called" + "\nfrom thread - > " +Thread.currentThread().getId());


    }
}
