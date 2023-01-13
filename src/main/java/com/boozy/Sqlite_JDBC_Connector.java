package com.boozy;

import java.sql.*;

public class Sqlite_JDBC_Connector {
    private static String db_path = "'C:\\projects\\New folder\\demo\\db\\rdp_manager.db'";
    private static String jdbc_string = "jdbc:sqlite:" + db_path;

    public static void create_tables() {

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* create tables if not exist */
            Statement statement = connection.createStatement();

            /* rdp */
            statement.execute("CREATE TABLE rdp (id INTEGER PRIMARY KEY AUTOINCREMENT, description varchar, type_id INTEGER, company_id INTEGER,	connection_info varchar, CONSTRAINT rdp_FK_1 FOREIGN KEY (type_id) REFERENCES types(id), CONSTRAINT rdp_FK_2 FOREIGN KEY (company_id) REFERENCES companies(id))");
            /* connection types */
            statement.execute("CREATE if not exists TABLE type (id INTEGER PRIMARY KEY AUTOINCREMENT, description varchar)");
            /* companies */
            statement.execute("CREATE if not exists TABLE company (id INTEGER PRIMARY KEY AUTOINCREMENT, description varchar)");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void post_rdp(String description, String type_id, String company_id, String connection_info){
        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* post into rdp */
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO rdp (description,type_id,company_id,connection_info) VALUES ('" + String.format("'%s', '%s', '%s', '%s'", description, type_id, company_id , connection_info));
        
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public void post_type(String description){
        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* post into type */
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO type (description) VALUES ('" + String.format("'%s'", description));
            
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public void post_company(String description){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* post into company */
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO company (description) VALUES ('" + String.format("'%s'", description));
            
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public void put_rdp(String id, String description, String type_id, String company_id, String connection_info){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* update rdp query */
            Statement statement = connection.createStatement();
            statement.execute(String.format("update rdp set description = '%s', type_id = '%s', company_id = '%s', connection_info = '%s' where rdp.id = '%s'", description, type_id, company_id, connection_info, id));
            
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public void put_type(String id, String description){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* update type query */
            Statement statement = connection.createStatement();
            statement.execute(String.format("update type set description = '%s' where type.id = '%s'", description, id));
            
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public void put_company(String id, String description){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* update company query */
            Statement statement = connection.createStatement();
            statement.execute(String.format("update company set description = '%s' where company.id = '%s'", description, id));
            
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public void delete_rdp(String id){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* update rdp query */
            Statement statement = connection.createStatement();
            statement.execute(String.format("delete from rdp where rdp.id = '%s'", id));
            
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public void delete_type(String id){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* update type query */
            Statement statement = connection.createStatement();
            statement.execute(String.format("delete from type where type.id = '%s'", id));
            
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public void delete_company(String id){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* update company query */
            Statement statement = connection.createStatement();
            statement.execute(String.format("delete from company where company.id = '%s'", id));
            
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public String get_rdp(){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            String get_all = "";

            /* get all rdp query */
            PreparedStatement prepared_statement = connection.prepareStatement("select * from rdp");
            ResultSet result_set = prepared_statement.executeQuery();
            
            get_all += "{";
            while (result_set.next()) {
                get_all += "{";
                get_all += String.format("id:%s, description:'%s',type_id:'%s',company_id:'%s',connection_info:'%s'", result_set.getInt("id"), result_set.getString("description"), result_set.getString("type_id"), result_set.getString("company_id"), result_set.getString("connection_info"));
                get_all += "}";
                get_all += ",";
            }
            get_all.substring(0, get_all.length()-1);
            get_all += "}";
            
            return get_all;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    public String get_type(){
        try(Connection connection = DriverManager.getConnection(jdbc_string)) {

            String get_all = "";

            /* get all type query */
            PreparedStatement prepared_statement = connection.prepareStatement("select * from type");
            ResultSet result_set = prepared_statement.executeQuery();
            
            get_all += "{";
            while (result_set.next()) {
                get_all += "{";
                get_all += String.format("id:%s, description:'%s',", result_set.getInt("id"), result_set.getString("description"));
                get_all += "}";
                get_all += ",";
            }
            get_all.substring(0, get_all.length()-1);
            get_all += "}";
            
            return get_all;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    public String get_company(){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {

            String get_all = "";

            /* get all company query */
            PreparedStatement prepared_statement = connection.prepareStatement("select * from company");
            ResultSet result_set = prepared_statement.executeQuery();
            
            get_all += "{";
            while (result_set.next()) {
                get_all += "{";
                get_all += String.format("id:%s, description:'%s',", result_set.getInt("id"), result_set.getString("description"));
                get_all += "}";
                get_all += ",";
            }
            get_all.substring(0, get_all.length()-1);
            get_all += "}";
            
            return get_all;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }
}
