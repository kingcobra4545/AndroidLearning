package com.possystems.kingcobra.newsworld;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import com.onesignal.OneSignal;
import com.possystems.kingcobra.newsworld.DataModel.DataModel;
import com.possystems.kingcobra.newsworld.HTTP_Requests.CustomVolley;
import com.possystems.kingcobra.newsworld.database.AndroidDatabaseManager;
import com.possystems.kingcobra.newsworld.database.GDatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  ActionBar.TabListener{
    String queries="";
    NewsFragment newsFragment;
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = { NewsApiConstants.COVER_STORY, NewsApiConstants.TECHNOLOGY, NewsApiConstants.BUSINESS };
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    Context context;
    CustomAdapter adapter;
    ArrayList<DataModel> d;
    private AsyncTask<Void, Void, Void> sleepTask, initDatabase, sleepTask2, sleepTask3;
    String TAG = "MainActivity";
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
//OneSignal Init Start
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
//OneSignal Init Finish


        //list = (ListView) findViewById(R.id.list);
        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);
        d = new ArrayList<>();
        adapter = new CustomAdapter(d, context);
        //list = (ListView) findViewById(R.id.list);

        //list.setAdapter(adapter);

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        mAdapter.getCount();

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(new ActionBar.TabListener() {
                        @Override
                        public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
                            QueryBuilder queryBuilder = new QueryBuilder();
                            queries = queryBuilder.getQueriesForTabSelected(tab.getText().toString());
                            Log.i(TAG, "1.Tab selected - > " + tab.getText());
                            Log.i(TAG, "2.Tab selected - > " + tab.toString());
                            viewPager.setCurrentItem(tab.getPosition());
                            mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
                            viewPager.setAdapter(mAdapter);
                            Log.i(TAG, "count of items in adapter-> " +viewPager.getAdapter().getCount());
                            makeVolleyRequest(queries);

                        }

                        @Override
                        public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

                        }

                        @Override
                        public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

                        }
                    }));
        }
        sleepTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    GDatabaseHelper gDatabaseHelper = GDatabaseHelper.getInstance(MainActivity.this);
                    Log.i(TAG, "DB being created");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    private void makeVolleyRequest(String queries) {
        CustomVolley customVolley = new CustomVolley(context);
        customVolley.makeRequest(NewsApiConstants.NEWS_API_DEFAULT_END_POINT + queries + NewsApiConstants.NEWS_API_KEY, list, queries);
    }

    private void addOrReplaceFragment(Boolean addOrReplace, Bundle desc) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(!addOrReplace) {// add
            Log.i(TAG, "Adding fragment");
            newsFragment.setArguments(desc);
            ft.add(R.id.news_container, newsFragment, "NewsFrag");
            ft.commit();
        }

        else
            {//replace
            //FragmentTransaction ft = fm.beginTransaction();
                Log.i(TAG, "Replacing fragment");
                newsFragment.setArguments(desc);
                ft.replace(R.id.news_container, newsFragment, "NewsFrag");
                ft.commit();
    }
    }

    /*public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.open_db:
                openDB();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openDB() {
        Intent dbmanager = new Intent(getApplicationContext(), AndroidDatabaseManager.class);
        startActivity(dbmanager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

/*    public void updateUI(final Context context, final ListView list) {


        Handler mainHandler = new Handler(context.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                ArrayList<DataModel> dataModel = new ArrayList<>();
                String queryToSelectTimeLineItems = context.getResources().getString(R.string.sql_query_select_get_items);
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
                        String PUBLISHEDAT = cr.getString(cr.getColumnIndex("PUBLISHEDAT"));
                        dataModel.add(new DataModel(ID, NAME, AUTHOR, TITLE, DESCRIPTION, URL, PUBLISHEDAT));
                    } while (cr.moveToNext());
                    cr.close();
                }
                CustomAdapter adapter = new CustomAdapter(dataModel, context);
                list.setAdapter(adapter);


            }

            // This is your code
        };
        mainHandler.post(myRunnable);
    }*/
public boolean checkPermission(){

    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //Show Information about why you need the permission
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Need Storage Permission");
            builder.setMessage("This app needs storage permission.");
            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else if (permissionStatus.getBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE,false)) {
            //Previously Permission Request was cancelled with 'Dont Ask Again',
            // Redirect to Settings after showing Information about why you need the permission
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Need Storage Permission");
            builder.setMessage("This app needs storage permission.");
            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    sentToSettings = true;
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                    Toast.makeText(getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else {
            //just request the permission
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
        }

        SharedPreferences.Editor editor = permissionStatus.edit();
        editor.putBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE,true);
        editor.commit();
        proceedAfterPermission();


    } else {
        //You already have the permission, just go ahead.
        proceedAfterPermission();
        return true;
    }
    return false;
}

    private void proceedAfterPermission() {
        //We've got the permission, now we can proceed further
        makeVolleyRequest(queries);
        Toast.makeText(getBaseContext(), "We got the Storage Permission", Toast.LENGTH_LONG).show();
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.i(TAG,"on resume");
        adapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.i(TAG,"on pause");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.i(TAG,"on stop");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG,"on Destroy");
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.i(TAG,"on Start");
    }
    @Override
    public void onRestart(){
        super.onRestart();
        Log.i(TAG,"on Restart");
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }
}
