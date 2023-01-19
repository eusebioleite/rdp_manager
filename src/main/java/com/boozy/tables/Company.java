package com.boozy.tables;

public class Company {
    
    private Integer id              = null;
    private String description      = null;

    public Company(Integer id, String description){

        this.id                 = id;
        this.description        = description;

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

}
