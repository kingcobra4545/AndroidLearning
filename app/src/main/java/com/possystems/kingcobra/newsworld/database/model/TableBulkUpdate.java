package com.possystems.kingcobra.newsworld.database.model;

import org.json.JSONArray;

/**
 * Created by Hetermis on 12/13/2016.
 */

public class TableBulkUpdate {

    private String tableName, jobID;
    private JSONArray rows;

    public String getTableName() {
        return tableName;
    }
    public String getJobID() {
        return jobID;
    }
    public void setJObID(String jobID) {
        this.jobID = jobID;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public JSONArray getRows() {
        return rows;
    }

    public void setRows(JSONArray rows) {
        this.rows = rows;
    }

    public TableBulkUpdate(String tableName, JSONArray rows, String jobID){
        this.jobID = jobID;
        this.tableName=tableName;
        this.rows=rows;
    }
}
