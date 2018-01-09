package com.possystems.kingcobra.newsworld;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.widget.ListView;

import com.possystems.kingcobra.newsworld.DataModel.DataModel;
import com.possystems.kingcobra.newsworld.database.DataAccessLayer;
import com.possystems.kingcobra.newsworld.database.GDatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by KingCobra on 10/12/17.
 */

public class NewsApiJsonParser {

    final String[] tagsToGetFromJsonObj = {"title", "description",};
    final String[] objectsToGetFromapiGroupsJsonObj = {"apiGroups"};
    final String[] objectsToGetFromAffiliateJsonObj = {"affiliate"};
    final String[] objectsToGetFromapiListingsJsonObj = {"apiListings"};
    final JSONObject[] responseJSONOBJECT = new JSONObject[1];
    final JSONObject[] apiGroupsJSONOBJECT = new JSONObject[1];
    final JSONObject[] affiliateJSONOBJECT = new JSONObject[1];
    final JSONObject[] apiListingsJSONObject = new JSONObject[1];

    Context context;

    JSONArray articlesJsonArray = new JSONArray();
    JSONObject individualSources = new JSONObject();
    String TAG = "NewsApiJsonParser";


    String response;
    JSONObject jsonObject;

    public NewsApiJsonParser(String response) {
        this.response = response;
    }
    public NewsApiJsonParser(JSONObject jsonObject) {
        this.response = jsonObject.toString();
    }
    public void  firstResponseParser(final Context context, ListView list, String queries){
        this.context = context;
        ArrayList<HashMap<String, String>> listOfItemsInACategory = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        final JSONArray articlesArray;
        JSONObject source;

        try {
            responseJSONOBJECT[0] = new JSONObject(response);
            Logger.i(TAG, "Names -- > " + responseJSONOBJECT[0].names());
            String status = (String) responseJSONOBJECT[0].names().get(0);
            map = getItemsInsideResponseJsonObject(responseJSONOBJECT);
            Logger.i(TAG,"Status - > " + status);
            if(map.get("status").equals("ok")){
                Logger.i(TAG, "Status:OK");
                articlesArray= new JSONArray( map.get("articles"));
                Logger.i(TAG, " articles -> "  + articlesArray.get(0));

                //Write into local db
                DataAccessLayer DAL = new DataAccessLayer(context);
                DAL.writeFirstJsonResponseDataToDB(articlesArray, queries);

                //Update UI with obtained data
                updateUI(context, list, queries);

            }




/*
            for (String j :tagsToGetFromJsonObj)
                Logger.i(TAG, j+" - > " + responseJSONOBJECT[0].getString(j));
            apiGroupsJSONOBJECT[0] = responseJSONOBJECT[0].getJSONObject(objectsToGetFromapiGroupsJsonObj[0]);
            affiliateJSONOBJECT[0] = apiGroupsJSONOBJECT[0].getJSONObject(objectsToGetFromAffiliateJsonObj[0]);
            JSONObject object;
            object = affiliateJSONOBJECT[0].getJSONObject("apiListings");
            HashMap<String , String> itemsSingle;
            for(int i=0;i<5;i++) {
                String categories = object.names().get(i).toString();
                ArrayList<JSONObject> availableVariantsObject = getAvailableVariants(object.getJSONObject(categories));
                Logger.i(TAG, "For category - > " + categories);
                //itemsSingle = getItemsInsideAvailableVariants(availableVariantsObject, categories);
                Logger.i(TAG, ".\n\n\n\n\n.");
                //listOfItemsInACategory.add(itemsSingle);
                itemsSingle = new HashMap<>();
            }*/

        }
        catch (Exception e ){
            e.printStackTrace();
        }
        //return listOfItemsInACategory;
    }

    private HashMap<String, String> getItemsInsideResponseJsonObject(JSONObject[] responseJSONOBJECT) throws JSONException {

        HashMap<String, String> map = new HashMap<>();
        ArrayList<String> arrayOfNames = new ArrayList<>();
        for(int i = 0 ; i<responseJSONOBJECT[0].length();i++) arrayOfNames.add((String) responseJSONOBJECT[0].names().get(i)) ;

        for(int p=0 ; p<responseJSONOBJECT[0].length();p++)
            for (String names: arrayOfNames) map.put(names, (String) responseJSONOBJECT[0].getString(names));
        return map;




    }

   /* private HashMap<String, String> getItemsInsideAvailableVariants(ArrayList<JSONObject> availableVariantsObjects, String category) throws JSONException {
        HashMap<String, String> map = new HashMap<>();
        map.put("category", category);
        ArrayList<JSONObject> temp = new ArrayList<>();
        ArrayList<HashMap<String, String>> items = new ArrayList<>();
        for (JSONObject p :availableVariantsObjects) {
            //Logger.i(TAG, "p--> " + p);
            for (int iii = 0; iii < p.length(); iii++) {
                String names = p.names().get(iii).toString();
                if (names != null)
                    if (names.equals(FlippyConstants.getJsonForVersion)){
                        //Logger.i(TAG, "\n\n" + names);
                if (p.get(names) instanceof JSONObject) {
                    //Logger.i(TAG, "into temp - >  "+ p.getJSONObject(names) + " \nfor name - > " + names);
                    temp.add(p.getJSONObject(names));
                }
                else if (p.get(names) instanceof String)
                    Logger.i(TAG, "tags + " + p.getString(names));
            }
            }
            for (JSONObject j : temp) {

                for(int y=0;y<j.length();y++) {
                    String nameInside = j.names().get(y).toString();
                    //Logger.i(TAG, "Being put -- >" +nameInside + " - > " + j.getString(nameInside) + "\n");

                    map.put(nameInside, j.getString(nameInside));
                    map.put("Json_Version", FlippyConstants.getJsonForVersion);

                }
                *//*items.add(map);
                map.clear();*//*
            }
        }
        return map;
    }*/

    private ArrayList<JSONObject> getAvailableVariants(JSONObject string) throws JSONException {
        ArrayList<JSONObject> temp = new ArrayList<>();

        for(int p =0 ; p<string.length();p++) {
            String names = string.names().get(p).toString();
            if(string.get(names) instanceof JSONObject)
                temp.add(string.getJSONObject(names));
            //Logger.i(TAG, "name " + names + "json object" +string.getJSONObject(names));
        }
        Logger.i(TAG, "temp size - > " + temp.size());
        return temp;

    }
    private TabsPagerAdapter mAdapter;

    public void updateUI(final Context context2, final ListView list, final String queries) {


        Handler mainHandler = new Handler(context.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                ArrayList<DataModel> dataModel = new ArrayList<>();
                String queryToSelectTimeLineItems = context.getResources().getString(R.string.sql_query_select_get_items);
                queryToSelectTimeLineItems = String.format(queryToSelectTimeLineItems, queries);
                queryToSelectTimeLineItems = queryToSelectTimeLineItems + "LIMIT " + String.valueOf(NewsApiConstants.NO_OF_ARTICLES_TO_FETCH);
                Logger.i(TAG, "Query to fetch news from local db - >" + queryToSelectTimeLineItems);

                GDatabaseHelper gHelper = GDatabaseHelper.getInstance(context);
                ArrayList<Cursor> cursorList1 = gHelper.getData(queryToSelectTimeLineItems);
                Cursor cr = cursorList1.get(0);
                if (cr != null && cr.moveToFirst()) {
                    //`ID` TEXT , `NAME` TEXT, `AUTHOR` TEXT, `TITLE` TEXT , `DESCRIPTION` TEXT , `URL` TEXT, `URLTOIMAGE` TEXT, `PUBLISHEDAT` TEXT


                    do {
                        String ID = cr.getString(cr.getColumnIndex("ID"));
                        String NAME = cr.getString(cr.getColumnIndex("NAME"));
                        String AUTHOR = cr.getString(cr.getColumnIndex("AUTHOR"));
                        String TITLE = cr.getString(cr.getColumnIndex("TITLE"));
                        String DESCRIPTION = cr.getString(cr.getColumnIndex("DESCRIPTION"));
                        String URL = cr.getString(cr.getColumnIndex("URL"));
                        String URLTOIMAGE = cr.getString(cr.getColumnIndex("URLTOIMAGE"));
                        String PUBLISHEDAT = cr.getString(cr.getColumnIndex("PUBLISHEDAT"));
                        dataModel.add(new DataModel(ID, NAME, AUTHOR, TITLE, DESCRIPTION, URL,URLTOIMAGE, PUBLISHEDAT));
                    } while (cr.moveToNext());
                    cr.close();
                }
                //CustomAdapter adapter = new CustomAdapter(dataModel, context);
                //adapter.notifyDataSetChanged();

                CoverStoryFragment c = CoverStoryFragment.getInstance();
                BusinessFragment b = BusinessFragment.getInstance();
                TechnologyFragment t = TechnologyFragment.getInstance();
                if(NewsApiConstants.COVER_STORY_QUERY.equals(queries))
                    c.adapterNotify(dataModel, context);
                else
                    if (NewsApiConstants.TECHNOLOGY_QUERY.equals(queries))
                        t.adapterNotify(dataModel, context);
                else
                    if(NewsApiConstants.BUSINESS_QUERY.equals(queries))
                        b.adapterNotify(dataModel, context);

                //Logger.i(TAG, "Volley call finish" + "\nAdapter size - >" + adapter.getCount());
                /*CoverStoryFragment c = new CoverStoryFragment();
                c.adapterNotify( adapter);*/

                //sendBroadCastCustom(dataModel);

                /*adapter.setOnDataChangeListener(new CustomAdapter.OnDataChangeListener() {
                    @Override
                    public void onDataChanged(DataModel dataModel) {

                    }
                });*/
                // list.setAdapter(adapter);


            }

            // This is your code
        };
        mainHandler.post(myRunnable);
    }

    private void sendBroadCastCustom(ArrayList<DataModel> dataModel) {

       /* Intent intent=new Intent(context,CoverStoryFragment.Receiver.class);
        intent.putExtra("dataModel", (Serializable) dataModel);
        context.sendBroadcast(intent);*/
    }

    /*public void firstResponseGsonParser(Context context, ListView list, String queries) {

        Gson gson = new Gson();
        List<>
        gson.fromJson(response, type);


    }*/
}
