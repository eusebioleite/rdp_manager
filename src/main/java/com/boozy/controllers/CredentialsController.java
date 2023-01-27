package com.boozy.controllers;

import java.util.ArrayList;

import com.boozy.AppSettings;
import com.boozy.tables.Credentials;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class CredentialsController {


    /* search bar */
    @FXML
    private TextField search_bar = new TextField();
    
    /* add button */
    @FXML
    private Button add_btn = new Button("add");

    /* delete button */
    @FXML
    private Button del_btn = new Button("del");

    @FXML
    private TableView<Credentials> data_table = new TableView<Credentials>();

    @FXML
    private TableColumn<Credentials, Integer> id_column = new TableColumn<>();

    @FXML
    private TableColumn<Credentials, String> username_column = new TableColumn<>();

    @FXML
    private TableColumn<Credentials, String> password_column = new TableColumn<>();

    @FXML
    private TableColumn<Credentials, Integer> rdp_column = new TableColumn<>();

    @FXML
    private void initialize(){

        /* set icons */
        add_btn.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.PLUS));
        del_btn.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.TIMES));

        /* load tableview */
        loadTable();

        /* search_bar listener */
        search_bar.textProperty().addListener((object, oldValue, newValue) -> {
            if (newValue.length() > oldValue.length()) {
                
                data_table.getItems().clear();
                
                /* read data from db and insert into tableview */
                ArrayList<Credentials> credentials = AppSettings.get_credentials_by_string(object.getValue());

                for(Credentials credentials_row : credentials){

                    data_table.getItems().add(
                        new Credentials(
                            credentials_row.getId(), 
                            credentials_row.getUsername(),
                            credentials_row.getPassword(),
                            credentials_row.getRdp_id()
                        )
                    );
                    
                }

            } else {

                data_table.getItems().clear();

                /* read data from db and insert into tableview */
                ArrayList<Credentials> credentials = AppSettings.get_credentials_by_string(object.getValue());

                for(Credentials credentials_row : credentials){

                    data_table.getItems().add(
                        new Credentials(
                            credentials_row.getId(),  
                            credentials_row.getUsername(),
                            credentials_row.getPassword(),
                            credentials_row.getRdp_id()
                        )
                    );
                    
                }

            }

        });
    
    }

    @FXML
    private void actionAdd(){
        
        /* Insert empty row */
        AppSettings.post_credentials("username...", "password...", 0);
        
        /* clear data_table */
        data_table.getItems().clear();

        /* read data from db and insert into tableview */
        ArrayList<Credentials> credentials = AppSettings.get_credentials();

        for(Credentials credentials_row : credentials){

            data_table.getItems().add(
                new Credentials(
                    credentials_row.getId(),  
                    credentials_row.getUsername(),
                    credentials_row.getPassword(),
                    credentials_row.getRdp_id()
                )
            );

        }
    }
    
    @FXML
    private void actionDel(){

        AppSettings.delete_credentials(data_table.getSelectionModel().getSelectedItem().getId());

        /* clear data_table */
        data_table.getItems().clear();

        /* read data from db and insert into tableview */
        ArrayList<Credentials> credentials = AppSettings.get_credentials();

        for(Credentials credentials_row : credentials){

            data_table.getItems().add(
                new Credentials(
                    credentials_row.getId(),  
                    credentials_row.getUsername(),
                    credentials_row.getPassword(),
                    credentials_row.getRdp_id()
                )
            );

        }

    }

    @FXML
    public void onEditCommitData_Table(CellEditEvent<?,?> event){

        switch(event.getTableColumn().idProperty().getValue().toString()){

            case "username_column":
                AppSettings.put_credentials(
                    data_table.getSelectionModel().getSelectedItem().getId(), 
                    event.getNewValue().toString(),
                    data_table.getSelectionModel().getSelectedItem().getPassword(),
                    data_table.getSelectionModel().getSelectedItem().getRdp_id()
                );

                break;

            case "password_column":
                AppSettings.put_credentials(
                    data_table.getSelectionModel().getSelectedItem().getId(),
                    data_table.getSelectionModel().getSelectedItem().getUsername(), 
                    event.getNewValue().toString(),
                    data_table.getSelectionModel().getSelectedItem().getRdp_id()
                );

                break;

            case "rdp_column":
                AppSettings.put_credentials(
                    data_table.getSelectionModel().getSelectedItem().getId(), 
                    data_table.getSelectionModel().getSelectedItem().getUsername(),
                    data_table.getSelectionModel().getSelectedItem().getPassword(),
                    Integer.parseInt(event.getNewValue().toString())
                );

                break;

        }

        /* read data from db and insert into tableview */
        ArrayList<Credentials> Credentials = AppSettings.get_credentials();

        data_table.getItems().clear();
        for(Credentials credentials_row : Credentials){

            data_table.getItems().add(
                new Credentials(
                    credentials_row.getId(),  
                    credentials_row.getUsername(),
                    credentials_row.getPassword(),
                    credentials_row.getRdp_id()
                )
            );

        }
    }

    private void loadTable(){

        /* load id columns */
        id_column.setCellValueFactory(new PropertyValueFactory<>("Id"));
        id_column.setCellFactory(TextFieldTableCell.<Credentials, Integer>forTableColumn(new IntegerStringConverter()));

        /* load name column */
        username_column.setCellValueFactory(new PropertyValueFactory<>("Username"));
        username_column.setCellFactory(TextFieldTableCell.<Credentials>forTableColumn());

        /* load description column */
        password_column.setCellValueFactory(new PropertyValueFactory<>("Password"));
        password_column.setCellFactory(TextFieldTableCell.<Credentials>forTableColumn());

        /* load rdp_id columns */
        rdp_column.setCellValueFactory(new PropertyValueFactory<>("Rdp_id"));
        rdp_column.setCellFactory(TextFieldTableCell.<Credentials, Integer>forTableColumn(new IntegerStringConverter()));

        /* read data from db and insert into tableview */
        ArrayList<Credentials> Credentials = AppSettings.get_credentials();

        for(Credentials credentials_row : Credentials){

            data_table.getItems().add(
                new Credentials(
                    credentials_row.getId(),  
                    credentials_row.getUsername(),
                    credentials_row.getPassword(),
                    credentials_row.getRdp_id()
                )
            );

        }
        
        /* set no results message */
        data_table.setPlaceholder(new Label("No search results."));

    }
}
