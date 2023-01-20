package com.boozy;

import java.sql.*;
import java.util.ArrayList;

import com.boozy.tables.Types;
import com.boozy.tables.view.RdpView;
import com.boozy.tables.Company;
import com.boozy.tables.Credentials;
import com.boozy.tables.Rdp;
import com.boozy.tables.Settings;

public class Sqlite_JDBC_Connector {
    private static String db_path = "C:\\projects\\rdp_manager\\db\\rdp_manager.db";
    private static String jdbc_string = "jdbc:sqlite:" + db_path;

    public static void create_tables() {

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* create tables if not exist */
            Statement statement = connection.createStatement();

            /* rdp */
            statement.execute("CREATE TABLE IF NOT EXISTS rdp (id INTEGER PRIMARY KEY AUTOINCREMENT, description varchar, types_id INTEGER, company_id INTEGER,	connection_info varchar, CONSTRAINT rdp_FK_1 FOREIGN KEY (types_id) REFERENCES types(id), CONSTRAINT rdp_FK_2 FOREIGN KEY (company_id) REFERENCES company(id))");
            
            /* connection types */
            statement.execute("CREATE TABLE IF NOT EXISTS types (id INTEGER PRIMARY KEY AUTOINCREMENT, description varchar)");
            
            /* company */
            statement.execute("CREATE TABLE IF NOT EXISTS company (id INTEGER PRIMARY KEY AUTOINCREMENT, description varchar)");
            
            /* settings */
            statement.execute("CREATE TABLE IF NOT EXISTS settings (id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar, value varchar)");
            statement.execute("CREATE UNIQUE INDEX settings_name_IDX ON settings (name)");
            statement.execute("INSERT INTO settings(name, value) values(\"teamviewer_path\",\"\")");
            statement.execute("INSERT INTO settings(name, value) values(\"anydesk_path\",\"\")");

            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void post_rdp(String description, String types_id, String company_id, String connection_info){
        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            System.out.println("INSERT INTO rdp (description,types_id,company_id,connection_info) VALUES (" + String.format("'%s', %s, %s, '%s'", description, types_id, company_id , connection_info) + ")");
            /* post into rdp */
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO rdp (description,types_id,company_id,connection_info) VALUES (" + String.format("'%s', %s, %s, '%s'", description, types_id, company_id , connection_info) + ")");
            
            statement.close();
            
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public static void post_types(String description){
        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* post into types */
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO types (description) VALUES ('" + String.format("'%s'", description));
            
            statement.close();
            
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public static void post_company(String description){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* post into company */
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO company (description) VALUES ('" + String.format("'%s'", description));
            
            statement.close();
            
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public static void put_rdp(String id, String description, String types_id, String company_id, String connection_info){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* update rdp query */
            Statement statement = connection.createStatement();
            statement.execute(
                String.
                    format(
                    "update rdp set description = '%s', types_id = %s, company_id = %s, connection_info = '%s' where rdp.id = %s", 
                            description, types_id, company_id, connection_info, id
                        )
                );
            
            statement.close();
                
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public static void put_types(String id, String description){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* update types query */
            Statement statement = connection.createStatement();
            statement.execute(String.format("update types set description = '%s' where types.id = '%s'", description, id));
            
            statement.close();
            
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public static void put_company(String id, String description){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* update company query */
            Statement statement = connection.createStatement();
            statement.execute(String.format("update company set description = '%s' where company.id = '%s'", description, id));
            
            statement.close();
            
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public static void delete_rdp(String id){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* update rdp query */
            Statement statement = connection.createStatement();
            statement.execute(String.format("delete from rdp where rdp.id = '%s'", id));
            
            statement.close();
            
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public static void delete_types(String id){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* update types query */
            Statement statement = connection.createStatement();
            statement.execute(String.format("delete from types where types.id = '%s'", id));
            
            statement.close();
            
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public static void delete_company(String id){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* update company query */
            Statement statement = connection.createStatement();
            statement.execute(String.format("delete from company where company.id = '%s'", id));
            
            statement.close();
            
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public static ArrayList<Rdp> get_rdp(){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {

            /* response body */            
            ArrayList<Rdp> response = new ArrayList<Rdp>();

            /* get all rdp query */
            PreparedStatement prepared_statement = connection.prepareStatement("select * from rdp");
            ResultSet result_set = prepared_statement.executeQuery();
            
            while (result_set.next()) {
                
                /* assign data to response body */
                Rdp rdp = new Rdp(
                    result_set.getInt("id"), 
                    result_set.getString("description"), 
                    result_set.getInt("types_id"), 
                    result_set.getInt("company_id"), 
                    result_set.getString("connection_info")
                );
                response.add(rdp);

            }

            /* close connection */
            result_set.close();
            prepared_statement.close();        

            /* return data */
            return response;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    public static ArrayList<RdpView> get_rdpview(){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* response body */
            ArrayList<RdpView> response = new ArrayList<RdpView>();

            /* create query */
            PreparedStatement prepared_statement = 
            connection.prepareStatement(
                "select rdp.id, rdp.description, types.description as 'types_description', company.description as 'company_description', rdp.connection_info from rdp " +
                "inner join types on rdp.types_id = types.id inner join company on rdp.company_id = company.id");
            
            /* execute query */
            ResultSet result_set = prepared_statement.executeQuery();
            
            while (result_set.next()) {

                /* assign values to response body */
                RdpView rdpview = new RdpView(
                    String.valueOf(result_set.getInt("id")), 
                    result_set.getString("description"), 
                    result_set.getString("types_description"),
                    result_set.getString("company_description"),
                    result_set.getString("connection_info")
                );
                response.add(rdpview);

            }

            /* close connection */
            result_set.close();
            prepared_statement.close();        

            /* return data */
            return response;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    public static ArrayList<Types> get_types(){
        try(Connection connection = DriverManager.getConnection(jdbc_string)) {

            /* response body */            
            ArrayList<Types> response = new ArrayList<Types>();
            
            /* create query */
            PreparedStatement prepared_statement = connection.prepareStatement("select * from types");
            
            /* execute query */
            ResultSet result_set = prepared_statement.executeQuery();
            
            
            while (result_set.next()) {

                /* assign data to response body */
                Types types = new Types(
                    result_set.getInt("id"), 
                    result_set.getString("description")
                );
                response.add(types);

            }

            /* close connection */
            result_set.close();
            prepared_statement.close();        
            
            /* return data */
            return response;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    public static ArrayList<Company> get_company(){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {

            /* response body */
            ArrayList<Company> response = new ArrayList<Company>();

            /* create query */
            PreparedStatement prepared_statement = connection.prepareStatement("select * from company");
            
            /* execute query */
            ResultSet result_set = prepared_statement.executeQuery();
            
            while (result_set.next()) {

                /* assign data to response body */
                Company company = new Company(
                    result_set.getInt("id"), 
                    result_set.getString("description")
                    );
                response.add(company);

            }

            /* close connection */
            result_set.close();
            prepared_statement.close();        

            /* return data */
            return response;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    public static ArrayList<RdpView> get_rdpview_by_type(String type){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* response body */
            ArrayList<RdpView> response = new ArrayList<RdpView>();

            /* create query */
            PreparedStatement prepared_statement = 
            connection.prepareStatement(
                "select rdp.id, rdp.description, types.description as 'types_description', company.description as 'company_description', rdp.connection_info from rdp " +
                "inner join types on rdp.types_id = types.id inner join company on rdp.company_id = company.id " +
                String.format("where rdp.types_id = %s", type));
            
            /* execute query */
            ResultSet result_set = prepared_statement.executeQuery();
            
            while (result_set.next()) {

                /* assign values to response body */
                RdpView rdpview = new RdpView(
                    String.valueOf(result_set.getInt("id")), 
                    result_set.getString("description"), 
                    result_set.getString("types_description"),
                    result_set.getString("company_description"),
                    result_set.getString("connection_info")
                );
                response.add(rdpview);

            }

            /* close connection */
            result_set.close();
            prepared_statement.close();        

            /* return data */
            return response;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    public static ArrayList<RdpView> get_rdpview_by_company(String company){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* response body */
            ArrayList<RdpView> response = new ArrayList<RdpView>();

            /* create query */
            PreparedStatement prepared_statement = 
            connection.prepareStatement(
                "select rdp.id, rdp.description, types.description as 'types_description', company.description as 'company_description', rdp.connection_info from rdp " +
                "inner join types on rdp.types_id = types.id inner join company on rdp.company_id = company.id " +
                String.format("where rdp.company_id = %s", company));
            
            /* execute query */
            ResultSet result_set = prepared_statement.executeQuery();
            
            while (result_set.next()) {

                /* assign values to response body */
                RdpView rdpview = new RdpView(
                    String.valueOf(result_set.getInt("id")), 
                    result_set.getString("description"), 
                    result_set.getString("types_description"),
                    result_set.getString("company_description"),
                    result_set.getString("connection_info")
                );
                response.add(rdpview);

            }

            /* close connection */
            result_set.close();
            prepared_statement.close();        

            /* return data */
            return response;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Rdp get_rdp_by_id(Integer id){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {

            /* response body */            
            Rdp rdp = null;

            /* create query */
            PreparedStatement prepared_statement = 
            connection.prepareStatement("select * from rdp where rdp.id = " + id.toString());
            
            /* execute query */
            ResultSet result_set = prepared_statement.executeQuery();
            
            while (result_set.next()) {

                /* assign data to response body */
                rdp = new Rdp(
                    result_set.getInt("id"), 
                    result_set.getString("description"), 
                    result_set.getInt("types_id"), 
                    result_set.getInt("company_id"), 
                    result_set.getString("connection_info")
                );
            
            }

            /* close connection */
            result_set.close();
            prepared_statement.close();        

            /* return data */
            return rdp;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Types get_types_by_id(Integer id){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {

            /* response */
            Types types = null;

            /* create query */
            PreparedStatement prepared_statement = 
            connection.prepareStatement(String.format("select * from types where types.id = %d", id));
            
            /* execute query */            
            ResultSet result_set = prepared_statement.executeQuery();
            
            
            while (result_set.next()) {

                /* assign data to response body */
                types = new Types(
                    result_set.getInt("id"), 
                    result_set.getString("description")
                );
            }

            /* close connection */
            result_set.close();
            prepared_statement.close();        

            /* return data */
            return types;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Settings get_settings_by_id(Integer id){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {

            /* response */
            Settings settings = null;

            /* create query */
            PreparedStatement prepared_statement = 
            connection.prepareStatement(String.format("select * from settings where settings.id = %d", id));
            
            /* execute query */            
            ResultSet result_set = prepared_statement.executeQuery();
            
            
            while (result_set.next()) {

                /* assign data to response body */
                settings = new Settings(
                    result_set.getInt("id"), 
                    result_set.getString("name"),
                    result_set.getString("value")
                );
            }

            /* close connection */
            result_set.close();
            prepared_statement.close();        

            /* return data */
            return settings;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Credentials get_credentials_by_rdp(Integer id){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {

            /* response */
            Credentials credentials = null;

            /* create query */
            PreparedStatement prepared_statement = 
            connection.prepareStatement(String.format("select * from credentials where credentials.rdp_id = %d", id));
            
            /* execute query */            
            ResultSet result_set = prepared_statement.executeQuery();
            
            
            while (result_set.next()) {

                /* assign data to response body */
                credentials = new Credentials(
                    result_set.getInt("id"), 
                    result_set.getString("username"),
                    result_set.getString("password")
                );
            }

            /* close connection */
            result_set.close();
            prepared_statement.close();        

            /* return data */
            return credentials;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Types get_types_by_description(String description){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {

            /* response body */
            Types types = null;

            /* get all types query */
            PreparedStatement prepared_statement = connection.prepareStatement(String.format("select * from types where types.description = '%s'", description));
            
            /* create query */
            ResultSet result_set = prepared_statement.executeQuery();
            
            while (result_set.next()) {

                /* assign data to response body */
                types = new Types(
                    result_set.getInt("id"), 
                    result_set.getString("description")
                );
            
            }

            /* close connection */
            result_set.close();
            prepared_statement.close();        

            /* return data */
            return types;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Company get_company_by_id(Integer id){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {

            /* response body */
            Company company = null;

            /* get all company query */
            PreparedStatement prepared_statement = connection.prepareStatement("select * from company where company.id = " + id.toString());
            
            /* create query */
            ResultSet result_set = prepared_statement.executeQuery();
            
            while (result_set.next()) {

                /* assign data to response body */
                company = new Company(
                    result_set.getInt("id"), 
                    result_set.getString("description")
                );
            
            }

            /* close connection */
            result_set.close();
            prepared_statement.close();        

            /* return data */
            return company;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Company get_company_by_description(String description){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {

            /* response body */
            Company company = null;

            /* get all company query */
            PreparedStatement prepared_statement = connection.prepareStatement(String.format("select * from company where company.description = '%s'", description));
            
            /* create query */
            ResultSet result_set = prepared_statement.executeQuery();
            
            while (result_set.next()) {

                /* assign data to response body */
                company = new Company(
                    result_set.getInt("id"), 
                    result_set.getString("description")
                );
            
            }

            /* close connection */
            result_set.close();
            prepared_statement.close();        

            /* return data */
            return company;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    public static ArrayList<RdpView> get_rdpview_by_string(String string){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* response body */
            ArrayList<RdpView> response = new ArrayList<RdpView>();
            
            /* create query */
            PreparedStatement prepared_statement = 
            connection.prepareStatement(
                "select rdp.id, rdp.description, types.description as 'types_description', company.description as 'company_description', rdp.connection_info from rdp " +
                "inner join types on rdp.types_id = types.id inner join company on rdp.company_id = company.id " +
                "where rdp.description like \"%" + string + 
                "%\" or types.description like \"%" + string + 
                "%\" or company.description like \"%" + string + 
                "%\" or rdp.connection_info like \"%" + string + 
                "%\""
            );
            /* execute query */
            ResultSet result_set = prepared_statement.executeQuery();
            
            while (result_set.next()) {

                /* assign values to response body */
                RdpView rdpview = new RdpView(
                    String.valueOf(result_set.getInt("id")), 
                    result_set.getString("description"), 
                    result_set.getString("types_description"),
                    result_set.getString("company_description"),
                    result_set.getString("connection_info")
                );
                response.add(rdpview);

            }

            /* close connection */
            result_set.close();
            prepared_statement.close();        

            /* return data */
            return response;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

}
