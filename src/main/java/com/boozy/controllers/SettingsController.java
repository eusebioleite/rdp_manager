package com.boozy.controllers;

import java.util.ArrayList;

import com.boozy.AppSettings;
import com.boozy.tables.Settings;

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

public class SettingsController {


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
    private TableView<Settings> data_table = new TableView<Settings>();

    @FXML
    private TableColumn<Settings, Integer> id_column = new TableColumn<>();

    @FXML
    private TableColumn<Settings, String> name_column = new TableColumn<>();

    @FXML
    private TableColumn<Settings, String> value_column = new TableColumn<>();

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
                ArrayList<Settings> settings = AppSettings.get_settings_by_string(object.getValue());

                for(Settings settings_row : settings){

                    data_table.getItems().add(
                        new Settings(
                            settings_row.getId(), 
                            settings_row.getName(),
                            settings_row.getValue() 
                        )
                    );
                    
                }

            } else {

                data_table.getItems().clear();

                /* read data from db and insert into tableview */
                ArrayList<Settings> settings = AppSettings.get_settings_by_string(object.getValue());

                for(Settings settings_row : settings){

                    data_table.getItems().add(
                        new Settings(
                            settings_row.getId(), 
                            settings_row.getName(),
                            settings_row.getValue() 
                        )
                    );
                    
                }

            }

        });
    
    }

    @FXML
    private void actionAdd(){
        
        /* Insert empty row */
        AppSettings.post_settings("name...", "value...");
        
        /* clear data_table */
        data_table.getItems().clear();

        /* read data from db and insert into tableview */
        ArrayList<Settings> settings = AppSettings.get_settings();

        for(Settings settings_row : settings){

            data_table.getItems().add(
                new Settings(
                    settings_row.getId(), 
                    settings_row.getName(),
                    settings_row.getValue() 
                )
            );

        }
    }
    
    @FXML
    private void actionDel(){

        AppSettings.delete_settings(data_table.getSelectionModel().getSelectedItem().getId());

        /* clear data_table */
        data_table.getItems().clear();

        /* read data from db and insert into tableview */
        ArrayList<Settings> settings = AppSettings.get_settings();

        for(Settings settings_row : settings){

            data_table.getItems().add(
                new Settings(
                    settings_row.getId(), 
                    settings_row.getName(),
                    settings_row.getValue() 
                )
            );

        }

    }

    @FXML
    public void onEditCommitData_Table(CellEditEvent<?,?> event){

        switch(event.getTableColumn().idProperty().getValue().toString()){

            case "name_column":
                AppSettings.put_settings(
                    data_table.getSelectionModel().getSelectedItem().getId(), 
                    event.getNewValue().toString(),
                    data_table.getSelectionModel().getSelectedItem().getValue()
                );

                break;

            case "value_column":
                AppSettings.put_settings(
                    data_table.getSelectionModel().getSelectedItem().getId(), 
                    data_table.getSelectionModel().getSelectedItem().getName(),
                    event.getNewValue().toString()
                );

                break;

        }

        /* read data from db and insert into tableview */
        ArrayList<Settings> Settings = AppSettings.get_settings();

        data_table.getItems().clear();
        for(Settings settings_row : Settings){

            data_table.getItems().add(
                new Settings(
                    settings_row.getId(), 
                    settings_row.getName(),
                    settings_row.getValue() 
                )
            );

        }
    }

    private void loadTable(){

        /* load id columns */
        id_column.setCellValueFactory(new PropertyValueFactory<>("Id"));
        id_column.setCellFactory(TextFieldTableCell.<Settings, Integer>forTableColumn(new IntegerStringConverter()));

        /* load name column */
        
        name_column.setCellValueFactory(new PropertyValueFactory<>("Name"));
        name_column.setCellFactory(TextFieldTableCell.<Settings>forTableColumn());
        
        /* load value column */
                
        value_column.setCellValueFactory(new PropertyValueFactory<>("Value"));
        value_column.setCellFactory(TextFieldTableCell.<Settings>forTableColumn());

        /* read data from db and insert into tableview */
        ArrayList<Settings> Settings = AppSettings.get_settings();

        for(Settings settings_row : Settings){

            data_table.getItems().add(
                new Settings(
                    settings_row.getId(), 
                    settings_row.getName(),
                    settings_row.getValue()
                )
            );

        }
        
        /* set no results message */
        data_table.setPlaceholder(new Label("No search results."));

    }
}
