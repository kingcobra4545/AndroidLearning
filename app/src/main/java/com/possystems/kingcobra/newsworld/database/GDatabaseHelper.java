package com.possystems.kingcobra.newsworld.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.possystems.kingcobra.newsworld.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "GDatabaseHelper";
    public static final String DATABASE_NAME = "flipkatDB.db";
    public static final int DATABASE_VERSION = 1;
    private Context context = null;
    private static GDatabaseHelper gDbHelper = null;
    private static final String PASSPHRASE = "FlipkatDB";


    // private static DbAsyncTask dbTask = null;

    public static synchronized GDatabaseHelper getInstance(Context context) {
        // dbTask = new DbAsyncTask((Activity) context);
        if (gDbHelper == null) {
            // SQLiteDatabase.loadLibs(context);
            gDbHelper = new GDatabaseHelper(context);
         Log.i(TAG,
                    "Database Path: " + context.getDatabasePath(DATABASE_NAME));

            SQLiteDatabase db = gDbHelper.getWritableDatabase();
            // db.close();
            // db = null;

        }
        return gDbHelper;
    }

    public GDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      /*  Log.d("GDatabaseHelper","inside oncreate of data base helper");
        DbAsyncTask dbTask = new DbAsyncTask( this.context, false, null);
        DbAsyncParameter dbAsyncParam = new DbAsyncParameter(R.string.sql_ddl_1,
                DbAsyncTask.QUERY_TYPE_UPDATE, null, null);
        dbAsyncParam.setDatabase(db);

        try {
            dbTask.execute(dbAsyncParam);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }*/
        String verDdlStrFormat = "sql_ddl_";
        Log.d("GDataBaseHelper","on create  db vesrion"+db.getVersion());
        int sqliteVersion = db.getVersion();
        for (int i = sqliteVersion+1; i <= DATABASE_VERSION; i++) {
            DbAsyncTask dbTask = new DbAsyncTask( this.context, false, null);
            String verDdlStr = verDdlStrFormat + String.valueOf(i);
            int resId = context.getResources().getIdentifier(verDdlStr,
                    "string", context.getPackageName());
            String ResDdlStr = this.context.getResources().getString(resId);
            Log.d("GDataBaseHelper","db vesrion"+ResDdlStr);
            DbAsyncParameter dbAsyncParam = new DbAsyncParameter(resId,
                    DbAsyncTask.QUERY_TYPE_UPDATE, null, null);
            dbAsyncParam.setDatabase(db);
            try {
                dbTask.execute(dbAsyncParam);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }
    }

    /*@Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }*/

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       /* Log.d("GDatabaseHelper","inside onUpgrade" +
                "" +
                " of data base helper"+oldVersion             +newVersion);
        db.beginTransaction();
        Log.d("GDatabaseHelper","db path " +db.getPath() +"db name"+db.getAttachedDbs() +" db is open "+db.isOpen());
        int sqliteVersion = db.getVersion();
        Log.d("GDatabaseHelper","sqllite version "+sqliteVersion);
        String verDdlStrFormat = "sql_ddl_%d";
        for (int i = sqliteVersion+1; i <= newVersion; i++) {
            String verDdlStr = String.format(verDdlStrFormat, i);
            int resId = context.getResources().getIdentifier(verDdlStr,
                    "string", context.getPackageName());
            String ResDdlStr = this.context.getResources().getString(resId);
            String[] ddl = ResDdlStr.split("\n");
            for (String ddlStr : ddl) {
                Log.d("GDatabaseHelper","inside onUpgrade" +
                        "" +
                        " ddl string"+ddlStr + "\nStringResRunning -- > \n--------------------" + ResDdlStr + "\n--------------------");
                try {
                    db.rawQuery(ddlStr,null);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }
        }
        db.endTransaction();*/
        String verDdlStrFormat = "sql_ddl_";
        Log.d("GDataBaseHelper"," on upgrade db vesrion"+db.getVersion());
        int sqliteVersion = db.getVersion();
        for (int i = sqliteVersion+1; i <= newVersion; i++) {
            DbAsyncTask dbTask = new DbAsyncTask( this.context, false, null);
            String verDdlStr = verDdlStrFormat + String.valueOf(i);
            int resId = context.getResources().getIdentifier(verDdlStr,
                    "string", context.getPackageName());
            String ResDdlStr = this.context.getResources().getString(resId);
            Log.d("GDataBaseHelper","db vesrion"+ResDdlStr);
            DbAsyncParameter dbAsyncParam = new DbAsyncParameter(resId,
                    DbAsyncTask.QUERY_TYPE_UPDATE, null, null);
            dbAsyncParam.setDatabase(db);
            try {
                dbTask.execute(dbAsyncParam);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }
    }


    public Cursor executeSql(DbAsyncParameter dbAsyncParm) throws Exception {

        Cursor result = null;
        int sqlResourceId = dbAsyncParm.getSqlResourceId();
        DbParameter dbParameter = dbAsyncParm.getDbParameter();
        String ResDdlStr = this.context.getResources().getString(sqlResourceId);
        String[] ddl = ResDdlStr.split("\n");

        if (ddl.length > 1) {
            throw new Exception();
        }
        GDatabaseHelper dbHelper = this;
        SQLiteDatabase sqliteDb = dbHelper.getReadableDatabase();

        try {
            for (String ddlStr : ddl) {
                Object[] parms = dbParameter.getObjectArrayParameters(0);
                String[] strParms = null;
                int parmsSize = -1;
                if (parms != null) {
                    strParms = new String[parms.length];
                    parmsSize = parms.length;
                }

                for (int i = 0; i < parmsSize; i++) {
                    if (parms[i] instanceof String) {
                        strParms[i] = (String) parms[i];
                    } else {
                        strParms[i] = String.valueOf(parms[i]);
                    }
                }
                result = sqliteDb.rawQuery(ddlStr, strParms);

                dbAsyncParm.setQueryCursor(result);
                dbAsyncParm.setDatabase(sqliteDb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqliteDb = null;
        }
        return result;
    }


    public Cursor executeSqlMultilineSQL(DbAsyncParameter dbAsyncParm) throws Exception {

        Cursor result = null;
        int sqlResourceId = dbAsyncParm.getSqlResourceId();
        DbParameter dbParameter = dbAsyncParm.getDbParameter();
        String ResDdlStr = this.context.getResources().getString(sqlResourceId);
        GDatabaseHelper dbHelper = gDbHelper;
        SQLiteDatabase sqliteDb = dbHelper.getReadableDatabase();

        try {
                String ddlStr = ResDdlStr;
                Object[] parms = dbParameter.getObjectArrayParameters(0);
                String[] strParms = null;
                int parmsSize = -1;
                if (parms != null) {
                    strParms = new String[parms.length];
                    parmsSize = parms.length;
                }

                for (int i = 0; i < parmsSize; i++) {
                    if (parms[i] instanceof String) {
                        strParms[i] = (String) parms[i];
                    } else {
                        strParms[i] = String.valueOf(parms[i]);
                    }
                }
                result = sqliteDb.rawQuery(ddlStr, strParms);

                dbAsyncParm.setQueryCursor(result);
                dbAsyncParm.setDatabase(sqliteDb);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            sqliteDb.close();
//            sqliteDb = null;
        }
        return result;
    }

    public Cursor executeDirectSql(DbAsyncParameter dbAsyncParm) throws Exception {

        Cursor result = null;
        int sqlResourceId = dbAsyncParm.getSqlResourceId();
        //DbParameter dbParameter = dbAsyncParm.getDbParameter();

        String ResDdlStr;
        if (dbAsyncParm.getSqlResourceId() == 0) {
            ResDdlStr = dbAsyncParm.getSqlQueryString();
        } else {
            ResDdlStr = this.context.getResources().getString(sqlResourceId);
        }
        String[] ddl = ResDdlStr.split("\n");

        if (ddl.length > 1) {
            throw new Exception();
        }
        GDatabaseHelper dbHelper = this;
        SQLiteDatabase sqliteDb = dbHelper.getReadableDatabase();

        try {
            for (String ddlStr : ddl) {
                result = sqliteDb.rawQuery(ddlStr, null);
                dbAsyncParm.setQueryCursor(result);
                dbAsyncParm.setDatabase(sqliteDb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqliteDb = null;
        }
        return result;
    }

    public void executeUpdateSql(DbAsyncParameter dbAsyncParm) // throws
    // Exception
    {

        int sqlResourceId = dbAsyncParm.getSqlResourceId();
        DbParameter dbParameter = dbAsyncParm.getDbParameter();
        String ResDdlStr;
        if (sqlResourceId > 0)
            ResDdlStr = this.context.getResources().getString(sqlResourceId);
        else
            ResDdlStr = dbAsyncParm.getSqlQueryString();
        String[] ddl = ResDdlStr.split("\n");
        SQLiteDatabase sqliteDb = gDbHelper.getWritableDatabase();
        //sqliteDb.beginTransaction();
        int index = 0;
        try {
            for (String ddlStr : ddl) {

                Object[] parms;
                parms = (dbParameter != null) ? (Object[]) dbParameter.getObjectArrayParameters(index) : null;
                if (parms == null) {
                    sqliteDb.execSQL(ddlStr);
                } else {
                    sqliteDb.execSQL(ddlStr, parms);
                }
                index++;
            }
            //sqliteDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //sqliteDb.endTransaction();
            sqliteDb = null;
        }
        sqliteDb = null;
    }

    public void executeUpdateSql(String query) {
        SQLiteDatabase sqliteDb = gDbHelper.getWritableDatabase();
        try {
            sqliteDb.beginTransaction();
            sqliteDb.execSQL(query);
            sqliteDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqliteDb.endTransaction();
            sqliteDb = null;
        }

    }

    public void executeBulkUpdateSql(DbAsyncParameter dbAsyncParm) // throws
    // Exception
    {
        int sqlResourceId = dbAsyncParm.getSqlResourceId();
        DbParameter dbParameter = dbAsyncParm.getDbParameter();
        String ResDdlStr = this.context.getResources().getString(sqlResourceId);
        String[] ddl = ResDdlStr.split("\n");
        GDatabaseHelper dbHelper = this;
        SQLiteDatabase sqliteDb = dbHelper.getWritableDatabase();
        sqliteDb.beginTransaction();
        try {
            String ddlStr = null;
            for (int index = 0; index < dbParameter.size(); index++) {
                Object[] parms;
                parms = (dbParameter != null) ? (Object[]) dbParameter
                        .getObjectArrayParameters(index) : null;
                for (int j = 0; j < ddl.length; j++) {
                    ddlStr = ddl[j];
                    if (parms == null) {
                        sqliteDb.execSQL(ddlStr);
                    } else {
                        sqliteDb.execSQL(ddlStr, parms);
                    }
                }
            }
            sqliteDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqliteDb.endTransaction();
        }
        sqliteDb = null;
    }

    public List<Cursor> executeMultipleSql(DbAsyncParameter dbAsyncParm) throws Exception {
        ArrayList<Cursor> result = new ArrayList<Cursor>();
        Cursor tCursor = null;
        int sqlResourceId = dbAsyncParm.getSqlResourceId();
        DbParameter dbParameter = dbAsyncParm.getDbParameter();
        String ResDdlStr = this.context.getResources().getString(sqlResourceId);
        String[] ddl = ResDdlStr.split("\n");
     Logger.i("DB", "No of Queries: " + ddl.length);
        GDatabaseHelper dbHelper = this;
        SQLiteDatabase sqliteDb = dbHelper.getReadableDatabase();

        int index = 0;
        try {
            for (String ddlStr : ddl) {
                ddlStr = ddlStr.trim();
                if (ddlStr.length() == 0) {
                    continue;
                }
             Logger.i("DB", "No of Queries: index " + index);
                Object[] parms = dbParameter.getObjectArrayParameters(index);
                String[] strParms = null;
                int parmsSize = -1;
                if (parms != null) {
                    strParms = new String[parms.length];
                    parmsSize = parms.length;
                }

                for (int i = 0; i < parmsSize; i++) {
                    if (parms[i] instanceof String) {
                        strParms[i] = (String) parms[i];
                    } else {
                        strParms[i] = String.valueOf(parms[i]);
                    }
                }
                tCursor = sqliteDb.rawQuery(ddlStr, strParms);

                result.add(tCursor);
                index++;
            }
            dbAsyncParm.setDatabase(sqliteDb);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqliteDb = null;
        }
        return result;
    }

    public void executeSingleQueryBulkUpdateSql(DbAsyncParameter dbAsyncParm) // throws Exception
    {
        int sqlResourceId = dbAsyncParm.getSqlResourceId();
        DbParameter dbParameter = dbAsyncParm.getDbParameter();
        String ResDdlStr;
        if (sqlResourceId == -1) {
            ResDdlStr = this.context.getResources().getString(sqlResourceId);
        } else {
            ResDdlStr = dbAsyncParm.getSqlQueryString();
        }
        executeSingleQueryUpdate(ResDdlStr, dbParameter);
    }

    public void executeSingleQueryUpdate(String uri, DbParameter dbParameter) // throws Exception
    {
        String[] ddl = uri.split("\n");
        GDatabaseHelper dbHelper = this;
        SQLiteDatabase sqliteDb = dbHelper.getWritableDatabase();
        sqliteDb.beginTransaction();
        // int index = 0;
        try {
            String ddlStr = null;
            for (int index = 0; index < dbParameter.size(); index++) {
                Object[] parms;
                ddlStr = ddl[0];
                parms = (dbParameter != null) ? (Object[]) dbParameter.getObjectArrayParameters(index) : null;
                if (parms == null) {
                    sqliteDb.execSQL(ddlStr);
                } else {
                    sqliteDb.execSQL(ddlStr, parms);
                }
            }
            sqliteDb.setTransactionSuccessful();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            sqliteDb.endTransaction();
            //			sqliteDb.close();
            sqliteDb = null;
        }
        // sqliteDb.close();
        sqliteDb = null;
        // dbHelper.close();
        // dbHelper = null;
    }

    public synchronized void executeInsertMasterTrx(DbAsyncParameter dbAsyncParm) throws Exception {
        int sqlResourceId = dbAsyncParm.getSqlResourceId();
        DbParameter dbParameter = dbAsyncParm.getDbParameter();
        String ResDdlStr;
        ResDdlStr = this.context.getResources().getString(sqlResourceId);

        /*
        if (sqlResourceId == -1) {
            ResDdlStr = this.context.getResources().getString(sqlResourceId);
        } else {
            ResDdlStr = dbAsyncParm.getSqlQueryString();
        }
        */

        if(ResDdlStr == null) {
            throw new Exception("No SQL Statements Found!");
        }
        String[] ddl = ResDdlStr.split("\n");

        if(ddl.length != 2) {
            throw new Exception("Only 2 Insert / Update Statments needed!");
        }

        if(dbParameter.size() < 2) {
            throw new Exception("Need atleast 3 sets of parameters");
        }

        // Insert Master Record

        GDatabaseHelper dbHelper = this;
        SQLiteDatabase sqliteDb = dbHelper.getWritableDatabase();
//        sqliteDb.beginTransaction();
     Logger.i("Ele", "MasterTable SQL: " + ddl[0]);
        sqliteDb.execSQL(ddl[0], dbParameter.getParameters(0).toArray());

        ArrayList<Object> param = dbParameter.getParameters(1);
        if(param == null || param.size() == 0) {
            throw new Exception("Must be valid Table Nane");
        }
        String masterTableName = (String) param.get(0);
     Logger.i("Ele", "MasterTable Name == " + masterTableName);

        long masterID = 0;
        String sqlMasterID = "select last_insert_rowid();"; // "select seq from sqlite_sequence where name=?";
        Cursor cursorMasterID = sqliteDb.rawQuery(sqlMasterID, null); //, new String[] {masterTableName});
        if(cursorMasterID != null && cursorMasterID.moveToFirst()) {
            masterID = cursorMasterID.getLong(0);
         Logger.i("Ele", "MasterTable ID == " + masterID);
            cursorMasterID.close();
            cursorMasterID = null;
        }

        String sqlTrx = ddl[1];
        for(int i = 2; i < dbParameter.size(); i++) {
            ArrayList<Object> dbP = dbParameter.getParameters(i);
            if(dbP != null && dbP.size() > 0) {
                dbP.set(0, new Long(masterID));
             Logger.i("Ele", "MasterTable Reset == " + dbP.get(0));
            }
            sqliteDb.execSQL(sqlTrx, dbP.toArray());
        }
//        sqliteDb.endTransaction();
       // sqliteDb.close();
    }

    /**
     * Method to take care of insertion and updation
     *
     * @param tableName
     * @param columnKeys
     * @param columnValues
     * @param where        - may be null for insert
     * @param dbAction     - can be null if don't need status
     */
    public void insertOrUpdateItem(String tableName, List<String> columnKeys, List<String> columnValues, String where, DbActionListener dbAction) {
        if (columnKeys.size() == columnValues.size()) {
            SQLiteDatabase database = gDbHelper.getWritableDatabase();
            if (where == null) {
                ContentValues contentValues = new ContentValues();
                int size = columnKeys.size();
                for (int i = 0; i < size; i++) {
                    contentValues.put(columnKeys.get(i), columnValues.get(i));
                }
                long insertStatus = database.insert(tableName, null, contentValues);
                if (dbAction != null)
                    if (insertStatus != -1)
                        dbAction.onSuccess();
                    else
                        dbAction.onFailure();
            } else {
                ContentValues contentValues = new ContentValues();
                int size = columnKeys.size();
                for (int i = 0; i < size; i++) {
                    contentValues.put(columnKeys.get(i), columnValues.get(i));
                }
                int updateStatus = database.update(tableName, contentValues, where, null);
                if (dbAction != null)
                    if (updateStatus != -1)
                        dbAction.onSuccess();
                    else
                        dbAction.onFailure();
            }
        }

    }

    /**
     * Method to take care of insertion
     *
     * @param resId
     * @param params - Need to take care of the params for the respective content
     */
    public void insertItem(int resId, ArrayList<Object> params) {
        DbParameter dbParams = new DbParameter();
        dbParams.addParamterList(params);
        final DbAsyncParameter dbAsyncParam = new DbAsyncParameter(resId, DbAsyncTask.QUERY_TYPE_UPDATE, dbParams, null);
        executeUpdateSql(dbAsyncParam);
    }


    /**
     * Method to take care of deletion of any item in a table
     *
     * @param tableName
     * @param where
     * @param dbActionListener -  can be null if don't need status
     */
    public void deleteItem(String tableName, String where, DbActionListener dbActionListener) {
        SQLiteDatabase database = gDbHelper.getWritableDatabase();
        int deleteStatus = database.delete(tableName, where, null);
        if (dbActionListener != null)
            if (deleteStatus != -1)
                dbActionListener.onSuccess();
            else
                dbActionListener.onFailure();

    }

    /*
    public int getMasterId(String id, NEWSAPIDataSourceConfig eleDataSourceConfig) {
        int masterId = -1;
        SQLiteDatabase sqliteDb = this.getReadableDatabase();
        Cursor cursor = sqliteDb.rawQuery(this.context.getString(R.string.sql_check_record_exists, id, eleDataSourceConfig.getDsName()), null);
        if (cursor != null && cursor.moveToFirst()) {
            masterId = cursor.getInt(0);
        }
        return masterId;
    }
    */
    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        if(this.getWritableDatabase().isDbLockedByCurrentThread()){
         Logger.i("DB","------locked-----");
        }

        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
         Logger.i("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

         Logger.i("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

    /*public void bulkUpdateTable(DbAsyncParameter dbAsyncParameter){
        boolean setStatus = false;
        int numOfRows = -1;
        EleSyncUtils eleSyncUtils = new EleSyncUtils(context);

        if(dbAsyncParameter==null){ return;}
        String tableName=dbAsyncParameter.getTableBulkUpdate().getTableName();
        String jobID=dbAsyncParameter.getTableBulkUpdate().getJobID();

        JSONArray tableDataArray=dbAsyncParameter.getTableBulkUpdate().getRows();
        numOfRows = tableDataArray.length();
        Logger.i("DB", " Started to write into Table Name-- > " + tableName );
        //Logger.d3(TAG,"=====> BULK-UPDATE - STARTED - "+tableName+" rows= #"+numOfRows+" at "+System.currentTimeMillis());
        Logger.syncLog("=====> BULK-UPDATE - STARTED - "+tableName+" rows= #"+numOfRows+" at "+System.currentTimeMillis() +" Job Id "+jobID);
        Long startTime = System.currentTimeMillis();

        //Log.d("v2","db insert ====>startTime tableName :: "+tableName);
        // SQLiteDatabase sqliteDatabase=this.getWritableDatabase();
        SQLiteDatabase sqliteDatabase = null;
        try {
             sqliteDatabase = SQLiteDatabase.openDatabase(String.valueOf(context.getDatabasePath(DATABASE_NAME)),
                    null,
                    SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
        }
        catch(Exception e){
            Logger.syncLog("E:====> BULK - UPDATE FAILED STAGE 1 open db exception "+ e.toString());
            Logger.i("SYNC", "Code-1 Failing for table -  > " + tableName + " acking server as - > false\n for jobid0> " + jobID);
            *//*UpdateTablesAfterPush updateTablesAfterPush = new UpdateTablesAfterPush(context);
            updateTablesAfterPush.sendACKFalseForPulledTable(jobID, false);*//*
            eleSyncUtils.ackSuccessOrFailureToServerAndUpdateLocalSessionTable(jobID, "false", tableName);
            Crashlytics.logException(e);
            e.printStackTrace();
        }
        if(sqliteDatabase==null){
            Logger.syncLog("E:====> BULK - UPDATE FAILED STAGE 2 db=null");
            return;
        }
        while(sqliteDatabase.isDbLockedByCurrentThread()){
            Logger.syncLog("E:===> BULK -UPDATE LOCKED BY C THREAD");
        }
        try{
            sqliteDatabase.beginTransaction();
            if(tableDataArray.length() > 0) {
                //start db trx
                for (int j = 0; j < tableDataArray.length(); j++) {
                    JSONObject data=tableDataArray.getJSONObject(j);
                    ArrayList<Object> params = new ArrayList<>();
                    Iterator<?> keys = data.keys();
                    String sql = "INSERT OR REPLACE INTO " + tableName;
                    String fields = "(";
                    String parameters= " VALUES(";
                    while( keys.hasNext() ) {
                        String key = (String)keys.next();

                        if ( data.get(key) instanceof String ) {
                            params.add(data.getString(key));
                            fields += key+",";
                            parameters += "?,";
                        }
                        else{
                            params.add(data.getString(key));
                            fields += key+",";
                            parameters += "?,";
                        }
                    }
                    fields = fields.substring(0, fields.lastIndexOf(",")) + ")";
                    parameters = parameters.substring(0, parameters.lastIndexOf(",")) + ")";
                    sql = sql + fields + parameters;
                    Object[] objectParms = (Object[]) params.toArray();

                    try{
                        //execute the sql statement
                        sqliteDatabase.execSQL(sql,objectParms);
                    }catch (SQLiteException e){
                        Logger.syncLog("E:====> BULK - UPDATE FAILED STAGE 3 execSQL() sqlite exception"+"Exception : "+e.toString());
                        Crashlytics.logException(e);
                        Logger.i("SYNC", "Code-2 Failing for table -  > " + tableName + " acking server as - > false\n for jobid0> " + jobID);
                        *//*UpdateTablesAfterPush updateTablesAfterPush = new UpdateTablesAfterPush(context);
                        updateTablesAfterPush.sendACKFalseForPulledTable(jobID, false);*//*
                        eleSyncUtils.ackSuccessOrFailureToServerAndUpdateLocalSessionTable(jobID, "false", tableName);
                        Logger.i(TAG,sql);
                        Logger.i(TAG,Arrays.toString(params.toArray()));
                        e.printStackTrace();
                    }
                }
                Logger.syncLog("<===== BULK - UPDATE SUCCESSFULL"+ tableName +" rows= #"+numOfRows);
                //end db trx
                sqliteDatabase.setTransactionSuccessful();
                setStatus = true;
                Long endTime = System.currentTimeMillis();
                Long totalTime = endTime - startTime;
                totalTime = totalTime;
                Logger.i("SYNC", "Syncing of table " + tableName + "took " + totalTime +" milli seconds" );
            }
            else setStatus = true;
        }
        catch (Exception e){
            Logger.syncLog("E:====> BULK - UPDATE FAILED STAGE 5 overall exception "+"Exception="+e.toString());
            Crashlytics.logException(e);
            Logger.i("SYNC", "Code-3 Failing for table -  > " + tableName + " acking server as - > false\n for jobid0> " + jobID);
            *//*UpdateTablesAfterPush updateTablesAfterPush = new UpdateTablesAfterPush(context);
            updateTablesAfterPush.sendACKFalseForPulledTable(jobID, false);*//*
            eleSyncUtils.ackSuccessOrFailureToServerAndUpdateLocalSessionTable(jobID, "false", tableName);
            Logger.i("DB", " Failed to write into Table Name-- > " + tableName);
            e.printStackTrace();
        }
        finally {
            try{
                sqliteDatabase.endTransaction();
            //Log.d("v2","db insert ====>endTime tableName:: "+tableName);
                if(setStatus){
                    Logger.i("ACK","ACKING SERVER AS true="+setStatus+" for table - > " + tableName );
                    eleSyncUtils.ackSuccessOrFailureToServerAndUpdateLocalSessionTable(jobID, "true", tableName);
                }
                else {
                    Logger.i("ACK","ACKING SERVER AS false="+setStatus+" for table - > " + tableName );
                    eleSyncUtils.ackSuccessOrFailureToServerAndUpdateLocalSessionTable(jobID, "false", tableName);
                }

            }catch ( Exception e){
                Logger.syncLog("E:====> BULK - UPDATE FAILED STAGE 6 finally() "+ "Exception : "+e.toString());
                Crashlytics.logException(e);
                Logger.i("SYNC", "Code-4 Failing for table -  > " + tableName + " acking server as - > false\n for jobid0> " + jobID);
                *//*UpdateTablesAfterPush updateTablesAfterPush = new UpdateTablesAfterPush(context);
                updateTablesAfterPush.sendACKFalseForPulledTable(jobID, false);*//*
                eleSyncUtils.ackSuccessOrFailureToServerAndUpdateLocalSessionTable(jobID, "false", tableName);
                e.printStackTrace();}
        }
    }*/
    public void executeSQLWithParams(String query,Object[] objects){
        try{
            SQLiteDatabase db=getWritableDatabase();
            Logger.i(TAG,query + Arrays.toString(objects));
            db.execSQL(query,objects);
        }
        catch (SQLException e){
            e.printStackTrace();
            throw e;
        }
    }

}
