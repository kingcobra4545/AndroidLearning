package com.possystems.kingcobra.newsworld.DataModel;

/**
 * Created by KingCobra on 24/11/17.
 */

public class DataModel {

    String title;
    String description;
    String author;
    String url;
    String publishedAT;

//dataModels.add(new DataModel("Apple Pie", "Android 1.0", "1","September 23, 2008"));
    public DataModel(){}
    public  DataModel(String ID, String name, String author, String title, String description, String url, String publishedAT){
        this.title = title;
        this.description = description;
        this.url = url;
        this.publishedAT = publishedAT;
        this.author = author;

    }
   /* public DataModel(String getURL,//"Apple Pie"
                     String jsonVersion,//"Android 1.0"
                    *//* String distanceTravelled,//"1"
                     String dateClicked //"September 23, 2008"*//*
    ) {
        this.getURL = getURL;
        this.jsonVersion = jsonVersion;
      *//*  this.distanceTravelled = distanceTravelled;
        this.dateClicked = dateClicked;*//*

    }
    public DataModel(String getURL,//"Apple Pie"
                     String jsonVersion,//"Android 1.0"
                     String distanceTravelled,//"1"
                     String dateClicked, //"September 23, 2008"
                     String photoLocation
    ) {
        this.getURL = getURL;
        this.jsonVersion = jsonVersion;
       *//* this.distanceTravelled =distanceTravelled;
        this.dateClicked =dateClicked;
        this.photoLocation=photoLocation;*//*

    }*/

    public String getTitle() {
        return title;
    }
    /*public void setGetURL(String getURL) {
        this.getURL = getURL;
    }*/

    public String getAuthor() {return this.author;}
    //public void setJsonVersion(String jsonVersion) {this.jsonVersion = jsonVersion;}

    /*public String getDistanceTravelled() {
        return distanceTravelled;
    }
    public void setDistanceTravelled(String distanceTravelled) {this.distanceTravelled=distanceTravelled;}

    public String getDateClicked() {
        return dateClicked;
    }
    public void setDateClicked(String dateClicked) {this.dateClicked=dateClicked;}*/


    public String getDescription() {return description;}
    //public void setPhotoLocation(String  photoLocation) {this.photoLocation=photoLocation;}
    //public void setPhoto(Bitmap photo) {this.photo=photo;}
    //public Bitmap getPhoto() {return photo;}

    public String getUrl() {
        return url;
    }
    public String getPublishedAT() {
        return publishedAT;
    }
    /*public  void setCategory(String category){
        this.category = category;
    }*/
}