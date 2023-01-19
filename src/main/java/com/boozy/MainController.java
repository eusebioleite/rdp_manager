package com.boozy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Collection;

import com.boozy.tables.*;
import com.boozy.tables.view.*;

public class MainController {

    @FXML
    private TextField search_bar = new TextField();

    @FXML
    private ComboBox<String> filter_type = new ComboBox<String>();
    
    @FXML
    private ComboBox<String> filter_data = new ComboBox<String>();
    
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
        
        search_bar.textProperty().addListener((object, oldValue, newValue) -> {
            if (newValue.length() > oldValue.length()) {

                data_table.getItems().clear();
                
                /* read data from db and insert into tableview */
                ArrayList<RdpView> rdpview = Sqlite_JDBC_Connector.get_rdpview_by_string("IIS");

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
                ArrayList<RdpView> rdpview = Sqlite_JDBC_Connector.get_rdpview_by_string("IIS");

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
        
        loadFilterType();

        loadTable();
    }

    private void loadFilterType(){
        
        /* load filter_type options */
        filter_type.getItems().add("Company");
        filter_type.getItems().add("Connection Type");

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
