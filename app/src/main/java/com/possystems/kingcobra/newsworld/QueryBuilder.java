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
                query = NewsApiConstants.COVER_STORY_QUERY;
                break;
            case NewsApiConstants.TECHNOLOGY:
                query = NewsApiConstants.TECHNOLOGY_QUERY;
                break;
            case NewsApiConstants.BUSINESS:
                query = NewsApiConstants.BUSINESS_QUERY;
                break;

        }
        return query;
    }
}
