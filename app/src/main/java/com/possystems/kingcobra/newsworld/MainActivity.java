package com.possystems.kingcobra.newsworld;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.possystems.kingcobra.newsworld.DataModel.DataModel;
import com.possystems.kingcobra.newsworld.HTTP_Requests.CustomVolley;
import com.possystems.kingcobra.newsworld.database.AndroidDatabaseManager;
import com.possystems.kingcobra.newsworld.database.GDatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    NewsFragment newsFragment;
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
        list = (ListView) findViewById(R.id.list);
        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);
        d = new ArrayList<>();
        adapter = new CustomAdapter(d, context);
        list.setAdapter(adapter);
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
        String newAPI = NewsApiConstants.NEWS_API;
        if(checkPermission())
        if(!haveNetworkConnection())
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
        else {
            CustomVolley customVolley = new CustomVolley(context);
            customVolley.makeRequest(NewsApiConstants.NEWS_API, list);
        }
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataModel dataModel= (DataModel) list.getItemAtPosition(position);
                //DataModel dataModel = new DataModel();
                Toast.makeText(context, "--> " + dataModel.getAuthor(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("desc", dataModel.getDescription());
                newsFragment = new NewsFragment();
                newsFragment.setArguments(bundle);
                Boolean flag = false;
                /*NewsFragment fragment = (NewsFragment) getFragmentManager().
                        findFragmentById(R.id.news_container);*/

                NewsFragment myFragment = (NewsFragment)getFragmentManager().findFragmentByTag("NewsFrag");
                if (myFragment != null && myFragment.isVisible()) {
                    Log.i(TAG, "fragment  in activity");
                    flag = true;
                }
                else {
                    Log.i(TAG, "fragment not in activity");
                    flag = false;
                }
                addOrReplaceFragment(flag, bundle);
            }
        });
        Log.i(TAG, "Mainactivity called" + "\nfrom thread - > " +Thread.currentThread().getId());

        sleepTask2 = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Log.i(TAG, "Async 10000 started");
                    Thread.sleep(10000);
                    Log.i(TAG, "Async 10000 stopped");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();
        sleepTask3 = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Log.i(TAG, "Async  20000 started");
                    Thread.sleep(20000);
                    Log.i(TAG, "Async 20000 stopped");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "thread started");
                Log.i(TAG, "Thread called" + "\nfrom thread - > " +Thread.currentThread().getId());


            }

        }).start();
        Intent is = new Intent("hellow");
        is.setAction("hellow");
        this.startService(is);*/

        //createTimeLineFromDB();


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

    public void updateUI(final Context context, final ListView list) {


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
    }
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


    } else {
        //You already have the permission, just go ahead.
        proceedAfterPermission();
        return true;
    }
    return false;
}

    private void proceedAfterPermission() {
        //We've got the permission, now we can proceed further
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

}
