package com.possystems.kingcobra.newsworld.POJO;

import java.util.ArrayList;

/**
 * Created by KingCobra on 22/01/18.
 */

public class News {

    private String status;
    private int totalResults;
    public ArrayList<Articles> articles;


    public String getStatus(){return status;}
    public int getTotalResults(){return totalResults;}


}
