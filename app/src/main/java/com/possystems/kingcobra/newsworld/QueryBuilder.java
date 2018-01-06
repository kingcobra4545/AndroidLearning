package com.possystems.kingcobra.newsworld;

/**
 * Created by KingCobra on 07/01/18.
 */

class QueryBuilder {

    public QueryBuilder(){}



    public String getQueriesForTabSelected(String tabName) {
        String query="";
        switch(tabName){
            case NewsApiConstants.COVER_STORY:
                query = "language=en&country=in&";
                break;
            case NewsApiConstants.TECHNOLOGY:
                query = "category=technology&";
                break;
            case NewsApiConstants.BUSINESS:
                query = "category=business&";
                break;

        }
        return query;
    }
}
