package com.possystems.kingcobra.newsworld.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.possystems.kingcobra.newsworld.Event;
import com.possystems.kingcobra.newsworld.NEWSAPIDataSourceConfig;

import java.util.Hashtable;
import java.util.List;

public class DbAsyncParameter {
    private static final String TAG = "ELE";
    private DbAction dbAction = null;
    private int sqlResourceId = 0;
    private String sqlQueryString = null;
    private DbParameter dbParameter;
    private GDatabaseHelper dbHelper = null;
    private SQLiteDatabase database;
    private int queryType = 0;
    private int queryResult = 0;
    private int itemId = 0;
    private Cursor queryCursor = null;
    private List<Cursor> listCursors = null;
    private List<Event> eventList = null;
    private Hashtable<String, NEWSAPIDataSourceConfig> dataSourceConfigHashtable;


    // FOR MASTER DATA INSERTION
    private Hashtable<String, String> projectionDatas;
    private Hashtable<String, String> projectionIds;

    //FOR Bulk Update table
    /*private TableBulkUpdate tableBulkUpdate;

    public TableBulkUpdate getTableBulkUpdate() {
        return tableBulkUpdate;
    }

    public void setTableBulkUpdate(TableBulkUpdate tableBulkUpdate) {
        this.tableBulkUpdate = tableBulkUpdate;
    }*/




    public DbAsyncParameter(/* SQLiteDatabase db, */ int sqlResId, int qryType, DbParameter dbParams, DbAction dbAction) {
        this.sqlResourceId = sqlResId;
        this.dbParameter = dbParams;
        this.dbAction = dbAction;
        this.queryType = qryType;
//    	this.database = db;
    }

    public DbAsyncParameter(/* SQLiteDatabase db, */ String sqlQueryString, int qryType, DbParameter dbParams, DbAction dbAction) {
        this.sqlQueryString = sqlQueryString;
        this.dbParameter = dbParams;
        this.dbAction = dbAction;
        this.queryType = qryType;
//    	this.database = db;
    }

    public DbAsyncParameter(int queryType, Hashtable<String, NEWSAPIDataSourceConfig> dataSourceConfigHashtable, DbAction dbAction) {
        this.dataSourceConfigHashtable = dataSourceConfigHashtable;
        this.queryType = queryType;
        this.dbAction = dbAction;
    }
    /*public DbAsyncParameter(int queryType, DbAction dbAction, TableBulkUpdate tableBulkUpdate){
        this.queryType=queryType;
        this.dbAction = dbAction;
        this.tableBulkUpdate=tableBulkUpdate;
    }*/

    public int getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(int queryResult) {
        this.queryResult = queryResult;
    }

    public Cursor getQueryCursor() {
        return queryCursor;
    }

    public void setQueryCursor(Cursor queryCursor) {
        this.queryCursor = queryCursor;
    }

    public int getSqlResourceId() {
        return sqlResourceId;
    }

    public void setSqlResourceId(int sqlResourceId) {
        this.sqlResourceId = sqlResourceId;
    }

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public DbAction getDbAction() {
        return dbAction;
    }

    public void setDbAction(DbAction dbAction) {
        this.dbAction = dbAction;
    }

    public DbParameter getDbParameter() {
        return dbParameter;
    }

    public void setDbParameter(DbParameter dbParameter) {
        this.dbParameter = dbParameter;
    }

    public GDatabaseHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(GDatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public String getSqlQueryString() {
        return sqlQueryString;
    }

    public void setSqlQueryString(String sqlQueryString) {
        this.sqlQueryString = sqlQueryString;
    }

    public List<Cursor> getListCursors() {
        return listCursors;
    }

    public void setListCursors(List<Cursor> listCursors) {
        this.listCursors = listCursors;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public Hashtable<String, NEWSAPIDataSourceConfig> getDataSourceConfigHashtable() {
        return dataSourceConfigHashtable;
    }

    public void setDataSourceConfigHashtable(Hashtable<String, NEWSAPIDataSourceConfig> dataSourceConfigHashtable) {
        this.dataSourceConfigHashtable = dataSourceConfigHashtable;
    }

    public void setSingleDataSourceConfiguration(NEWSAPIDataSourceConfig NEWSAPIDataSourceConfig){
        this.dataSourceConfigHashtable = new Hashtable<>();
        this.dataSourceConfigHashtable.put("DATASOURCE", NEWSAPIDataSourceConfig);
    }
    public Hashtable<String, String> getProjectionDatas() {
        return projectionDatas;
    }

    public void setProjectionDatas(Hashtable<String, String> projectionDatas) {
        this.projectionDatas = projectionDatas;
    }

    public Hashtable<String, String> getProjectionIds() {
        return projectionIds;
    }

    public void setProjectionIds(Hashtable<String, String> projectionIds) {
        this.projectionIds = projectionIds;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
