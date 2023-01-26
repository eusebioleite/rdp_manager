package com.boozy.tables;

public class Credentials {

    private Integer id              = null;
    private String username      = null;
    private String password      = null;
    private Integer rdp_id              = null;

    public Credentials(Integer id, String username, String password, Integer rdp_id){

        this.id                 = id;
        this.username        = username;
        this.password        = password;
        this.rdp_id        = rdp_id;

    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the rdp_id
     */
    public Integer getRdp_id() {
        return rdp_id;
    }

    /**
     * @param rdp_id the rdp_id to set
     */
    public void setRdp_id(Integer rdp_id) {
        this.rdp_id = rdp_id;
    }

    
}
