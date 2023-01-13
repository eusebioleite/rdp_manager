package com.boozy;

public class RDPs {

    private String id              = null;
    private String description      = null;
    private String type             = null;
    private String company          = null;
    private String connection_info  = null;

    public RDPs(){

    }

    public RDPs(String id, String description, String type, String company, String connection_info){

        this.id                 = id;
        this.description        = description;
        this.type               = type;
        this.company            = company;
        this.connection_info    = connection_info;

    }
    
    public String getId() {
        return id;
    }

    public void setId(String Id) {
        this.id = Id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getConnectionInfo() {
        return connection_info;
    }

    public void setConnectionInfo(String connection_info) {
        this.connection_info = connection_info;
    }


}
