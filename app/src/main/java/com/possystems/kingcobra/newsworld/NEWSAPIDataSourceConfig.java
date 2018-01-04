package com.possystems.kingcobra.newsworld;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tamilselvankalimuthu on 01/06/16.
 */
public class NEWSAPIDataSourceConfig {

    private String dsName;
    private String contentUri;
    private String permissions;
    private String predicate;
    private String dsTimeFieldName;

    private String dsSourceId;
    private List<String> projectionNames = new ArrayList<>();
    private List<String> projectionIds = new ArrayList<>();

    public String getDsSourceId() {
        return dsSourceId;
    }

    public void setDsSourceId(String dsSourceId) {
        this.dsSourceId = dsSourceId;
    }
    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getContentUri() {
        return contentUri;
    }

    public void setContentUri(String contentUri) {
        this.contentUri = contentUri;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public List<String> getProjectionNames() {
        return projectionNames;
    }

    public void setProjectionNames(List<String> projectionNames) {
        this.projectionNames = projectionNames;
    }

    public List<String> getProjectionIds() {
        return projectionIds;
    }

    public void setProjectionIds(List<String> projectionIds) {
        this.projectionIds = projectionIds;
    }

    public String getDsTimeFieldName() {
        return dsTimeFieldName;
    }

    public void setDsTimeFieldName(String dsTimeFieldName) {
        this.dsTimeFieldName = dsTimeFieldName;
    }
}
