package com.boozy.tables;

public class Rdp {

    private Integer id              = null;
    private String description      = null;
    private Integer types_id            = null;
    private Integer company_id         = null;
    private String connection_info  = null;

    public Rdp(){

    }

    public Rdp(Integer id, String description, Integer types_id, Integer company_id, String connection_info){

        this.id                 = id;
        this.description        = description;
        this.types_id               = types_id;
        this.company_id            = company_id;
        this.connection_info    = connection_info;

    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTypes_id() {
        return types_id;
    }

    public void setTypes_id(Integer types_id) {
        this.types_id = types_id;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public String getConnection_info() {
        return connection_info;
    }

    public void setConnection_info(String connection_info) {
        this.connection_info = connection_info;
    }


}
