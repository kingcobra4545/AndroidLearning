package com.possystems.kingcobra.newsworld.database;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.possystems.kingcobra.newsworld.Logger;

import java.util.List;


public class DbAsyncTask extends
        AsyncTask<DbAsyncParameter, DbAsyncParameter, DbAsyncParameter> {
    private static final String TAG = "ELE";
    // private DbAction dbAction = null;
    private Context activity = null;
    public static int QUERY_TYPE_UPDATE = 1;
    public static int QUERY_TYPE_CURSOR = 2;
    public static int QUERY_TYPE_BULK_UPDATE = 3;
    public static int QUERY_TYPE_MULTIPLE_CURSOR = 4;
    public static int QUERY_TYPE_SINGLE_QUERY_BULK_UPDATE = 5;
    public static int QUERY_TYPE_WITHOUT_CURSOR = 6;
    public static int QUERY_TYPE_GET_CURSOR = 7;
    public static int QUERY_TYPE_GET_DATA_FROM_DATASOURCE = 8;
    public static int QUERY_TYPE_SAVE_DATA_TO_MASTER = 9;
    public static int QUERY_TYPE_GET_SPECIFIC_FROM_DATASOURCE = 10;
    public static int QUERY_TYPE_INSERT_MASTER_TRX = 11;
    public static int QUERY_TYPE_SINGLE_MULTILINE_SQL_CURSOR = 12;
    public static int QUERY_TYPE_BULK_UPDATE_TABLE = 13;

    private boolean defaultAI = true;
    private View customAI = null;
    private GDatabaseHelper dbHelper = null;
    ProgressDialog pDialog;

    public DbAsyncTask(Context context, boolean tdAI, View cAI) {
        this.activity = context;
        this.defaultAI = tdAI;
        this.customAI = cAI;
    }

    @Override
    protected DbAsyncParameter doInBackground(DbAsyncParameter... params) {
        DbAsyncParameter dbAsyncParm = params[0];
//        dbAsyncParm.getDbAction().execPreDbAction();
//		Log.i(TAG, "==============>>> Inside doInBackground........... " + dbAsyncParm.getQueryType());
        dbHelper = new GDatabaseHelper(this.activity);
        if (dbAsyncParm.getQueryType() == QUERY_TYPE_UPDATE) {
            try {

//				Log.i(TAG, "going to Happen in Update");
                dbHelper.executeUpdateSql(dbAsyncParm);
            } catch (Exception e) {
                // TODO Auto-generated catch block
             Logger.i(TAG, "=============>>Error Happen in Update");
                e.printStackTrace();
            }
        } else if (dbAsyncParm.getQueryType() == QUERY_TYPE_CURSOR) {
            try {
//				Log.i(TAG, "going to Happen in Retrive");
                dbAsyncParm.setQueryCursor(dbHelper.executeSql(dbAsyncParm));
            } catch (Exception e) {
                // TODO Auto-generated catch block
             Logger.i(TAG, "=============>>Error Happen in Retrive");
                e.printStackTrace();
            }
        } else if (dbAsyncParm.getQueryType() == QUERY_TYPE_BULK_UPDATE) {
            try {
                dbHelper.executeBulkUpdateSql(dbAsyncParm);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (dbAsyncParm.getQueryType() == QUERY_TYPE_MULTIPLE_CURSOR) {
            try {
                List<Cursor> listCursors = dbHelper.executeMultipleSql(dbAsyncParm);
                dbAsyncParm.setListCursors(listCursors);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (dbAsyncParm.getQueryType() == QUERY_TYPE_SINGLE_QUERY_BULK_UPDATE) {
            try {
                dbHelper.executeSingleQueryBulkUpdateSql(dbAsyncParm);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (dbAsyncParm.getQueryType() == QUERY_TYPE_WITHOUT_CURSOR) {
            try {
                dbHelper.executeDirectSql(dbAsyncParm);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } /*else if (dbAsyncParm.getQueryType() == QUERY_TYPE_GET_DATA_FROM_DATASOURCE) {
            try {
                //List<Event> events = EleAppExtractor.getInstance(this.activity).extractAllUserData(dbAsyncParm.getDataSourceConfigHashtable());
                dbAsyncParm.setEventList(events);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else if (dbAsyncParm.getQueryType() == QUERY_TYPE_GET_SPECIFIC_FROM_DATASOURCE) {
            try {
                List<Event> events = EleAppExtractor.getInstance(this.activity).extractSpecificDsData(dbAsyncParm.getDataSourceConfigHashtable().get("DATASOURCE"),dbAsyncParm.getItemId());
                dbAsyncParm.setEventList(events);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }*/  else if (dbAsyncParm.getQueryType() == QUERY_TYPE_INSERT_MASTER_TRX) {
            try {
                dbHelper.executeInsertMasterTrx(dbAsyncParm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(dbAsyncParm.getQueryType() == QUERY_TYPE_SINGLE_MULTILINE_SQL_CURSOR) {
            try {
                dbHelper.executeSqlMultilineSQL(dbAsyncParm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       /* else if(dbAsyncParm.getQueryType()==QUERY_TYPE_BULK_UPDATE_TABLE){
            dbHelper.bulkUpdateTable(dbAsyncParm);
        }*/

        return dbAsyncParm;
    }


    @Override
    protected void onProgressUpdate(DbAsyncParameter... params) {
    }

    @Override
    protected void onPostExecute(DbAsyncParameter dbAsyncParm) {
        DbAction dbAction = dbAsyncParm.getDbAction();
        try {
            if (dbAction != null /* && !activity.isFinishing() */) {
                dbAction.execPostDbAction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (dbHelper != null /* && !activity.isFinishing() */) {
            dbHelper.close();
            dbHelper = null;
        }

        if (this.defaultAI && this.pDialog != null /* && !activity.isFinishing() */) {
            this.pDialog.dismiss();
        } else if (this.customAI != null /* && !activity.isFinishing() */) {
            this.customAI.setVisibility(View.GONE);
            this.customAI.setAnimation(null);
        }
    }

    @Override
    protected void onPreExecute() {
        if (this.defaultAI) {
            /*this.progressDialog=new GprogressDialog(activity,R.drawable.progress_spinner);
            this.progressDialog.show();		*/

//            pDialog = new ProgressDialog(activity);
//            pDialog.setMessage("Loading...");
//            pDialog.show();
        } else if (this.customAI != null) {
            this.customAI.setVisibility(View.VISIBLE);
            RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(Animation.INFINITE);
            anim.setDuration(1500);
            this.customAI.setAnimation(anim);
            this.customAI.startAnimation(anim);
        }
        //	this.progressDialog = new ProgressDialog(activity, R.style.gProgressDialog);
        //		this.progressDialog=new GprogressDialog(activity,R.drawable.progress_spinner);

		/*
//		this.progressDialog.setTitle("Database Processing");
//		this.progressDialog.setMessage("Please wait......");
    	this.progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  
		this.progressDialog.setCancelable(false);
		this.progressDialog.setIndeterminate(true);
		 */


        //		this.progressDialog.show();

		/*
         * DbAsyncParameter dbAsyncParm = params[0]; DbAction dbAction =
		 * dbAsyncParm.getDbAction(); if(dbAction != null) {
		 * dbAction.execPreDbAction(); }
		 */
    }

   /* private void updateDataSourceAccessTime(String dsName, int lastInsertedItem, long time) {
        final DbAsyncTask dbATask = new DbAsyncTask(this.activity, false, null);
        DbParameter dbParams = new DbParameter();
        ArrayList<Object> parms = new ArrayList<Object>();
        parms.add(time);
        parms.add(lastInsertedItem);
        parms.add(dsName);
        dbParams.addParamterList(parms);
        dbParams.addParamterList(parms);

        final DbAsyncParameter dbAsyncParam = new DbAsyncParameter(R.string.sql_update_datasource, DbAsyncTask.QUERY_TYPE_UPDATE, dbParams, null);

        DbAction dbAction = new DbAction() {

            @Override
            public void execPreDbAction() {

            }

            @Override
            public void execPostDbAction() {
                android.util.Log.i("ELE", "Updated the EVENT Data Source Update data");

            }
        };

        dbAsyncParam.setDbAction(dbAction);


        try {
            dbATask.execute(dbAsyncParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
