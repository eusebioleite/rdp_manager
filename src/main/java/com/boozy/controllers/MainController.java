package com.boozy.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.util.ArrayList;

import com.boozy.App;
import com.boozy.Sqlite_JDBC_Connector;
import com.boozy.tables.*;
import com.boozy.tables.view.*;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

public class MainController {

    /* menus */
    @FXML
    private MenuItem menu_types = new MenuItem();

    /* search bar */
    @FXML
    private TextField search_bar = new TextField();
    
    /* buttons */
    @FXML
    private Button add_btn = new Button("add");

    @FXML
    private Button del_btn = new Button("del");

    @FXML
    private Button go_btn = new Button("go");

    /* combo boxes */
    @FXML
    private ComboBox<String> filter_type = new ComboBox<String>();
    
    @FXML
    private ComboBox<String> filter_data = new ComboBox<String>();
    
    /* table */
    @FXML
    private TableView<RdpView> data_table = new TableView<RdpView>();

    @FXML
    private TableColumn<RdpView, String> id_column = new TableColumn<>();

    @FXML
    private TableColumn<RdpView, String> description_column = new TableColumn<>();

    @FXML
    private TableColumn<RdpView, String> type_column = new TableColumn<>();

    @FXML
    private TableColumn<RdpView, String> company_column = new TableColumn<>();

    @FXML
    private TableColumn<RdpView, String> connectioninfo_column = new TableColumn<>();

    @FXML
    private void initialize(){

        /* set icons */
        add_btn.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.PLUS));
        del_btn.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.TIMES));
        go_btn.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.ARROW_RIGHT));

        /* load combobox */
        loadFilterType();

        /* load tableview */
        loadTable();

        /* search_bar listener */
        search_bar.textProperty().addListener((object, oldValue, newValue) -> {
            if (newValue.length() > oldValue.length()) {
                
                data_table.getItems().clear();
                
                /* read data from db and insert into tableview */
                ArrayList<RdpView> rdpview = Sqlite_JDBC_Connector.get_rdpview_by_string(object.getValue());

                for(RdpView rdpview_row : rdpview){

                    data_table.getItems().add(
                        new RdpView(
                            rdpview_row.getId(), 
                            rdpview_row.getDescription(), 
                            rdpview_row.getTypes_description(), 
                            rdpview_row.getCompany_description(), 
                            rdpview_row.getConnection_info()
                        )
                    );
                    
                }

            } else {

                data_table.getItems().clear();

                /* read data from db and insert into tableview */
                ArrayList<RdpView> rdpview = Sqlite_JDBC_Connector.get_rdpview_by_string(object.getValue());

                for(RdpView rdpview_row : rdpview){

                    data_table.getItems().add(
                        new RdpView(
                            rdpview_row.getId(), 
                            rdpview_row.getDescription(), 
                            rdpview_row.getTypes_description(), 
                            rdpview_row.getCompany_description(), 
                            rdpview_row.getConnection_info()
                        )
                    );
                    
                }

            }

        });
    
    }

    private void loadFilterType(){
        
        /* load filter_type options */
        filter_type.getItems().add("Company");
        filter_type.getItems().add("Connection Type");
        filter_type.getItems().add("None");
        /* load filter_data with data from company table, since it's the first option */
        ArrayList<Company> company = Sqlite_JDBC_Connector.get_company();   

        for (Company company_row : company) {
        
            filter_data.getItems().add(company_row.getDescription());

        }

    }

    @FXML
    public void actionFilterType(ActionEvent e){

        /* clear combobox */
        filter_data.getItems().clear();

        /* load filter_data with data from company or types based on user selection */
        if (filter_type.getValue().equals("Company")){

            /* load filter_data combobox with data from company table */
            ArrayList<Company> company = Sqlite_JDBC_Connector.get_company();   

            for (Company company_row : company) {
            
                filter_data.getItems().add(company_row.getDescription());

            }

        } else {
            
            /* load filter_data combobox with data from types table */
            ArrayList<Types> types = Sqlite_JDBC_Connector.get_types();   

            for (Types types_row : types) {
            
                filter_data.getItems().add(types_row.getDescription());

            }

        }

    }
    
    @FXML
    public void actionFilterData(ActionEvent e){
        if (filter_type.getSelectionModel().getSelectedItem().toString() == "Connection Type"){

            /* read data from db and insert into tableview */
            ArrayList<RdpView> rdpview = 
            Sqlite_JDBC_Connector.get_rdpview_by_type(
                Sqlite_JDBC_Connector.get_types_by_description(filter_data.getSelectionModel().getSelectedItem().toString()).getId().toString());

            data_table.getItems().clear();
            for(RdpView rdpview_row : rdpview){

                data_table.getItems().add(
                    new RdpView(
                        rdpview_row.getId(), 
                        rdpview_row.getDescription(), 
                        rdpview_row.getTypes_description(), 
                        rdpview_row.getCompany_description(), 
                        rdpview_row.getConnection_info()
                    )
                );

            }
        } else if(filter_type.getSelectionModel().getSelectedItem().toString() == "Company") {

            /* read data from db and insert into tableview */
            ArrayList<RdpView> rdpview = 
            Sqlite_JDBC_Connector.get_rdpview_by_company(
                Sqlite_JDBC_Connector.get_company_by_description(filter_data.getSelectionModel().getSelectedItem().toString()).getId().toString()
            );

            data_table.getItems().clear();
            for(RdpView rdpview_row : rdpview){

                data_table.getItems().add(
                    new RdpView(
                        rdpview_row.getId(), 
                        rdpview_row.getDescription(), 
                        rdpview_row.getTypes_description(), 
                        rdpview_row.getCompany_description(), 
                        rdpview_row.getConnection_info()
                    )
                );

            }
        } else {

            /* read data from db and insert into tableview */
            ArrayList<RdpView> rdpview = Sqlite_JDBC_Connector.get_rdpview();

            for(RdpView rdpview_row : rdpview){

                data_table.getItems().add(
                    new RdpView(
                        rdpview_row.getId(), 
                        rdpview_row.getDescription(), 
                        rdpview_row.getTypes_description(), 
                        rdpview_row.getCompany_description(), 
                        rdpview_row.getConnection_info()
                    )
                );

            }
            
        }

    }

    @FXML
    public void actionMenuTypes(){
        
        try {

            /* load fxml */
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("types.fxml"));

            /* create window */
            Scene scene = new Scene(fxmlLoader.load(), 640, 400);
            Stage stage = new Stage();
            stage.setTitle("Connection Types");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println("error:");
            System.out.println(e.getMessage());


        }

    }

    @FXML
    public void actionMenuCompanies(){

        try {

            /* load fxml */
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("companies.fxml"));

            /* create scene */
            Scene scene = new Scene(fxmlLoader.load(), 640, 400);

            Stage stage = new Stage();
            stage.setTitle("Companies");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println("error:");
            System.out.println(e.getMessage());


        }

    }
    
    @FXML
    public void actionMenuCredentials(){
        
        try {

            /* load fxml */
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("credentials.fxml"));

            /* create scene */
            Scene scene = new Scene(fxmlLoader.load(), 640, 400);

            Stage stage = new Stage();
            stage.setTitle("Credentials");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println("error:");
            System.out.println(e.getMessage());


        }

    }
    
    @FXML
    public void actionMenuSettings(){

        try {

            /* load fxml */
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("settings.fxml"));

            /* create scene */
            Scene scene = new Scene(fxmlLoader.load(), 640, 400);

            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println("error:");
            System.out.println(e.getMessage());


        }

    }
    /* CRUD */
    @FXML
    private void actionAdd(){
        
        /* Insert empty row */
        Sqlite_JDBC_Connector.post_rdp("your description...", "1", "1", "0.0.0.0");
        
        /* clear data_table */
        data_table.getItems().clear();

        /* read data from db and insert into tableview */
        ArrayList<RdpView> rdpview = Sqlite_JDBC_Connector.get_rdpview();

        for(RdpView rdpview_row : rdpview){

            data_table.getItems().add(
                new RdpView(
                    rdpview_row.getId(), 
                    rdpview_row.getDescription(), 
                    rdpview_row.getTypes_description(), 
                    rdpview_row.getCompany_description(), 
                    rdpview_row.getConnection_info()
                )
            );

        }
    }
    
    @FXML
    private void actionDel(){

        Sqlite_JDBC_Connector.delete_rdp(data_table.getSelectionModel().getSelectedItem().getId());

        /* clear data_table */
        data_table.getItems().clear();

        /* read data from db and insert into tableview */
        ArrayList<RdpView> rdpview = Sqlite_JDBC_Connector.get_rdpview();

        for(RdpView rdpview_row : rdpview){

            data_table.getItems().add(
                new RdpView(
                    rdpview_row.getId(), 
                    rdpview_row.getDescription(), 
                    rdpview_row.getTypes_description(), 
                    rdpview_row.getCompany_description(), 
                    rdpview_row.getConnection_info()
                )
            );

        }

    }

    @FXML
    private void actionGo(){
        try {

            /* retrieve necessary data */
            String type_description = data_table.getSelectionModel().getSelectedItem().getTypes_description();
            String connection_info = data_table.getSelectionModel().getSelectedItem().getConnection_info();
            String rdp_id = data_table.getSelectionModel().getSelectedItem().getId();
            String username = Sqlite_JDBC_Connector.get_credentials_by_rdp(Integer.parseInt(rdp_id)).getUsername();
            String password = Sqlite_JDBC_Connector.get_credentials_by_rdp(Integer.parseInt(rdp_id)).getPassword();

            /* retrieve exe paths */
            String anydesk_path = Sqlite_JDBC_Connector.get_settings_by_id(2).getValue();
            String teamviewer_path = Sqlite_JDBC_Connector.get_settings_by_id(1).getValue();
            String putty_path = Sqlite_JDBC_Connector.get_settings_by_id(3).getValue();

            /* build the command */
            String anydesk_command = String.format("cmd /c echo %s | cmd /c \"%s\" %s --with-password", password, anydesk_path, connection_info);
            String teamviewer_command = String.format("cmd /c %s --id %s -p %s", teamviewer_path, connection_info, password);
            String rdp_command = String.format("cmd /c mstsc /f /v:\"%s\" /Prompt", connection_info);
            String putty_command = 
            (password.contains(":")) ? 
            String.format("cmd /c %s -ssh %s@%s -i %s", putty_path, username, connection_info, password) :
            String.format("cmd /c %s -ssh %s@%s -pw %s", putty_path, username, connection_info, password) ;
            
            switch(type_description.trim()){
                
                case "AnyDesk":
                    Process ad = Runtime.getRuntime().exec(anydesk_command);
                    ad.waitFor();
                    break;

                case "TeamViewer":
                    Process tv = Runtime.getRuntime().exec(teamviewer_command);
                    tv.waitFor();
                    break;

                case "RDP":
                    Process rdp = Runtime.getRuntime().exec(rdp_command);
                    rdp.waitFor();
                    break;

                case "PuTTY":
                    Process tty = Runtime.getRuntime().exec(putty_command);
                    tty.waitFor();
                    break;
            }

        } catch(Exception e) {

        }

    }

    @FXML
    public void onEditCommitData_Table(CellEditEvent<?,?> event){

        switch(event.getTableColumn().idProperty().getValue().toString()){

            case "description_column":
                Sqlite_JDBC_Connector.put_rdp(
                    data_table.getSelectionModel().getSelectedItem().getId(), 
                    event.getNewValue().toString(), 
                    Sqlite_JDBC_Connector.get_types_by_description(data_table.getSelectionModel().getSelectedItem().getTypes_description()).getId().toString(), 
                    Sqlite_JDBC_Connector.get_company_by_description(data_table.getSelectionModel().getSelectedItem().getCompany_description()).getId().toString(), 
                    data_table.getSelectionModel().getSelectedItem().getConnection_info()
                );
                break;
            
            case "type_column":
                Sqlite_JDBC_Connector.put_rdp(
                    data_table.getSelectionModel().getSelectedItem().getId(), 
                    data_table.getSelectionModel().getSelectedItem().getDescription(), 
                    Sqlite_JDBC_Connector.get_types_by_description(event.getNewValue().toString()).getId().toString(), 
                    Sqlite_JDBC_Connector.get_company_by_description(data_table.getSelectionModel().getSelectedItem().getCompany_description()).getId().toString(), 
                    data_table.getSelectionModel().getSelectedItem().getConnection_info()
                );
                break;
            
            case "company_column":
                Sqlite_JDBC_Connector.put_rdp(
                    data_table.getSelectionModel().getSelectedItem().getId(), 
                    data_table.getSelectionModel().getSelectedItem().getDescription(), 
                    Sqlite_JDBC_Connector.get_types_by_description(data_table.getSelectionModel().getSelectedItem().getTypes_description()).getId().toString(), 
                    Sqlite_JDBC_Connector.get_company_by_description(event.getNewValue().toString()).getId().toString(), 
                    data_table.getSelectionModel().getSelectedItem().getConnection_info()
                );
                break;
            
            case "connectioninfo_column":
                Sqlite_JDBC_Connector.put_rdp(
                    data_table.getSelectionModel().getSelectedItem().getId(), 
                    data_table.getSelectionModel().getSelectedItem().getDescription(), 
                    Sqlite_JDBC_Connector.get_types_by_description(data_table.getSelectionModel().getSelectedItem().getTypes_description()).getId().toString(), 
                    Sqlite_JDBC_Connector.get_company_by_description(data_table.getSelectionModel().getSelectedItem().getCompany_description()).getId().toString(), 
                    event.getNewValue().toString()
                );
                break;
            
        }

        /* read data from db and insert into tableview */
        ArrayList<RdpView> rdpview = Sqlite_JDBC_Connector.get_rdpview();

        data_table.getItems().clear();
        for(RdpView rdpview_row : rdpview){

            data_table.getItems().add(
                new RdpView(
                    rdpview_row.getId(), 
                    rdpview_row.getDescription(), 
                    rdpview_row.getTypes_description(), 
                    rdpview_row.getCompany_description(), 
                    rdpview_row.getConnection_info()
                )
            );

        }
    }
    
    public void reloadCombos(){

        /* load types combo box and column */
        ArrayList<Types> types = Sqlite_JDBC_Connector.get_types();
        ObservableList<String> types_description = FXCollections.observableArrayList();

        for(Types types_row : types){

            types_description.add(types_row.getDescription());

        }
        
        type_column.setCellValueFactory(new PropertyValueFactory<>("Types_description"));
        type_column.setCellFactory(ComboBoxTableCell.<RdpView, String>forTableColumn(types_description));

        /* load company combo box and column */
        ArrayList<Company> company = Sqlite_JDBC_Connector.get_company();
        ObservableList<String> company_description = FXCollections.observableArrayList();
        
        for(Company company_row : company){

            company_description.add(company_row.getDescription());

        }
        
        company_column.setCellValueFactory(new PropertyValueFactory<>("Company_description"));
        company_column.setCellFactory(ComboBoxTableCell.<RdpView, String>forTableColumn(company_description));

    }
    
    private void loadTable(){

        /* load id columns */
        id_column.setCellValueFactory(new PropertyValueFactory<>("Id"));
        id_column.setCellFactory(TextFieldTableCell.<RdpView>forTableColumn());

        /* load description column */
        description_column.setCellValueFactory(new PropertyValueFactory<>("Description"));
        description_column.setCellFactory(TextFieldTableCell.<RdpView>forTableColumn());

        /* load types combo box and column */
        ArrayList<Types> types = Sqlite_JDBC_Connector.get_types();
        ObservableList<String> types_description = FXCollections.observableArrayList();

        for(Types types_row : types){

            types_description.add(types_row.getDescription());

        }
        
        type_column.setCellValueFactory(new PropertyValueFactory<>("Types_description"));
        type_column.setCellFactory(ComboBoxTableCell.<RdpView, String>forTableColumn(types_description));

        /* load company combo box and column */
        ArrayList<Company> company = Sqlite_JDBC_Connector.get_company();
        ObservableList<String> company_description = FXCollections.observableArrayList();
        
        for(Company company_row : company){

            company_description.add(company_row.getDescription());

        }
        
        company_column.setCellValueFactory(new PropertyValueFactory<>("Company_description"));
        company_column.setCellFactory(ComboBoxTableCell.<RdpView, String>forTableColumn(company_description));

        /* load connection_info column */
        connectioninfo_column.setCellValueFactory(new PropertyValueFactory<>("Connection_info"));
        connectioninfo_column.setCellFactory(TextFieldTableCell.<RdpView>forTableColumn());

        /* read data from db and insert into tableview */
        ArrayList<RdpView> rdpview = Sqlite_JDBC_Connector.get_rdpview();

        for(RdpView rdpview_row : rdpview){

            data_table.getItems().add(
                new RdpView(
                    rdpview_row.getId(), 
                    rdpview_row.getDescription(), 
                    rdpview_row.getTypes_description(), 
                    rdpview_row.getCompany_description(), 
                    rdpview_row.getConnection_info()
                )
            );

        }
        
        /* set no results message */
        data_table.setPlaceholder(new Label("No search results."));

    }

}
