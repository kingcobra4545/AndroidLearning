package com.possystems.kingcobra.newsworld;

import android.content.Context;

import java.util.Hashtable;

/**
 * Created by apple on 5/8/16.
 */

public class Event {
    private boolean command = false;
    private String uri;
    private String dataSourceName;
    private long capturedTime;
    private long dsTime;
    private Hashtable<String, String> projectionData;
    private Hashtable<String, String> projectionIds;
    private long itemId;
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isCommand() {
        return command;
    }

    public void setCommand(boolean command) {
        this.command = command;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public long getCapturedTime() {
        return capturedTime;
    }

    public void setCapturedTime(long capturedTime) {
        this.capturedTime = capturedTime;
    }

    public Hashtable<String, String> getProjectionData() {
        return projectionData;
    }

    public void setProjectionData(Hashtable<String, String> projectionData) {
        this.projectionData = projectionData;
    }

    public Hashtable<String, String> getProjectionIds() {
        return projectionIds;
    }

    public void setProjectionIds(Hashtable<String, String> projectionIds) {
        this.projectionIds = projectionIds;
    }


    public long getDsTime() {
        return dsTime;
    }

    public void setDsTime(long dsTime) {
        this.dsTime = dsTime;
    }


    /*private long eventTime;
    private String senderId;
    private String receiverId;*/

    /*
    private double longitude;
    private double latitude;
    private int userActivity;
    */
    /*private String subject;
    private String body;

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }*/


    /*
    public int getUserActivity() {
        return userActivity;
    }

    public void setUserActivity(int userActivity) {
        this.userActivity = userActivity;
    }
    */

    /*public long getCapturedTime() {
        return capturedTime;
    }

    public void setCapturedTime(long capturedTime) {
        this.capturedTime = capturedTime;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }*/

    /*
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    */

}
