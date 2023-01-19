package com.boozy.tables.view;

public class RdpView {

    private String id              = null;
    private String description      = null;
    private String types_description            = null;
    private String company_description         = null;
    private String connection_info  = null;

    public RdpView(String id, String description, String types_description, String company_description, String connection_info){

        this.id                 = id;
        this.description        = description;
        this.types_description               = types_description;
        this.company_description            = company_description;
        this.connection_info    = connection_info;

    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the types_description
     */
    public String getTypes_description() {
        return types_description;
    }

    /**
     * @param types_description the types_description to set
     */
    public void setTypes_description(String types_description) {
        this.types_description = types_description;
    }

    /**
     * @return the company_description
     */
    public String getCompany_description() {
        return company_description;
    }

    /**
     * @param company_description the company_description to set
     */
    public void setCompany_description(String company_description) {
        this.company_description = company_description;
    }

    /**
     * @return the connection_info
     */
    public String getConnection_info() {
        return connection_info;
    }

    /**
     * @param connection_info the connection_info to set
     */
    public void setConnection_info(String connection_info) {
        this.connection_info = connection_info;
    }
    
    
}
