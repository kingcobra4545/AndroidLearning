package com.possystems.kingcobra.newsworld.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.possystems.kingcobra.newsworld.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by KingCobra on 12/12/17.
 */

public class DataAccessLayer {
    String TAG = "DataAccessLayer";
    Context context;
    public DataAccessLayer(Context context){
        this.context = context;
    }
    public void writeFirstJsonResponseDataToDB(JSONArray articlesArray) throws JSONException {


        Logger.i(TAG, " articles -> "  + articlesArray.get(0));
            for(int u = 0 ;u<articlesArray.length();u++) {
                Logger.i(TAG, "articles array - > " + articlesArray);

            }
                /*articlesJsonArray = (JSONArray) responseJSONOBJECT[0].names().get(1);
                individualSources = (JSONObject) articlesJsonArray.get(0);
                Logger.i(TAG, "1 source - > " + individualSources);*/






        /*for (HashMap<String , String> map:
                mainList) {

            Logger.i(TAG, "key -- > " + map.keySet());
        }*/

        //String querySelectFromFirstResponseTable = context.getResources().getString(R.string.sql_query_select_from_first_response_table);
       GDatabaseHelper gHelper = GDatabaseHelper.getInstance(context);
        SQLiteDatabase db = gHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            //( `ID`	INTEGER PRIMARY KEY AUTOINCREMENT, `DELTA_GET`	TEXT, `_GET`	TEXT, `POST`	TEXT,
            // `CATEGORY`	TEXT NOT NULL, `_DELETE`	TEXT, `RESOURCE_NAME`	TEXT, `PUT` TEXT, `TOP`	INTEGER)

           for (int i = 0 ; i<articlesArray.length();i++) {
               JSONObject newsItem = new JSONObject(articlesArray.get(i).toString());
               for (int u = 1; u<newsItem.names().length();u++) {
                   JSONObject j = newsItem.getJSONObject("source");
                   Logger.i(TAG, "-- > " + newsItem.names().get(u) + " -- > " + newsItem.get((String) newsItem.names().get(u)));
                   String key =  newsItem.names().get(u).toString().toUpperCase();
                   String value = newsItem.get( newsItem.names().get(u).toString()).toString();
                   //deltaGet, get, post, category, delete, resourceName, put, top

                    //values.put("ID", i);
                    //values.put("JSON_VERSION", map.get(FlippyConstants.JSON_VERSION_STRING));
                   //values.put("ID", j.getString("ID"));
                   Logger.i(TAG, "1...Putting value in values map - > " + key + "\n" + value);
                    values.put(key, value);
                   Logger.i(TAG, "1...Now data in  values map - > " + values.toString());

                    //db.insertWithOnConflict("NEWS_FIRST_RESPONSE", null, values, SQLiteDatabase.CONFLICT_REPLACE);

               }
               Logger.i(TAG, "1..Inserting data - > " + values.toString());
               db.insert("NEWS_FIRST_RESPONSE", null, values);
               values.clear();
            }

            db.setTransactionSuccessful();
        }

        catch (Exception e){

            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }

       /* ArrayList<Cursor> cursorList1 = gHelper.getData(querySelectFromFirstResponseTable);
        Cursor cr = cursorList1.get(0);
        if(cr != null && cr.moveToFirst()) {
            do {
                String category =

            }while (cr.moveToNext());
        }*/

    }
    /*public String getURLForSecondJsonResponseForACategory(String category){
        String URL=null;

        String querySelectGETURL = context.getResources().getString(R.string.sql_query_select_get_url);
        querySelectGETURL = String.format(querySelectGETURL, category);
        GDatabaseHelper gHelper = GDatabaseHelper.getInstance(context);
        ArrayList<Cursor> cursorList1 = gHelper.getData(querySelectGETURL);
        Cursor cr = cursorList1.get(0);
        if(cr!=null && cr.moveToFirst()){
            int index = cr.getColumnIndex("_GET");
            do {
                URL = cr.getString(index);
            }while (cr.moveToNext());
        }
        if(cr!=null)
            cr.close();

        return URL;
    }*/
}
