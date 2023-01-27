package com.boozy.controllers;

import java.util.ArrayList;

import com.boozy.AppSettings;
import com.boozy.tables.Types;

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

public class TypesController {


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
    private TableView<Types> data_table = new TableView<Types>();

    @FXML
    private TableColumn<Types, Integer> id_column = new TableColumn<>();

    @FXML
    private TableColumn<Types, String> description_column = new TableColumn<>();

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
                ArrayList<Types> types = AppSettings.get_types_by_string(object.getValue());

                for(Types types_row : types){

                    data_table.getItems().add(
                        new Types(
                            types_row.getId(), 
                            types_row.getDescription()
                        )
                    );
                    
                }

            } else {

                data_table.getItems().clear();

                /* read data from db and insert into tableview */
                ArrayList<Types> types = AppSettings.get_types_by_string(object.getValue());

                for(Types types_row : types){

                    data_table.getItems().add(
                        new Types(
                            types_row.getId(), 
                            types_row.getDescription()
                        )
                    );
                    
                }

            }

        });
    
    }

    @FXML
    private void actionAdd(){
        
        /* Insert empty row */
        AppSettings.post_types("your description...");
        
        /* clear data_table */
        data_table.getItems().clear();

        /* read data from db and insert into tableview */
        ArrayList<Types> types = AppSettings.get_types();

        for(Types types_row : types){

            data_table.getItems().add(
                new Types(
                    types_row.getId(), 
                    types_row.getDescription()
                )
            );

        }

        (new MainController()).reloadCombos();
        
    }
    
    @FXML
    private void actionDel(){

        AppSettings.delete_types(data_table.getSelectionModel().getSelectedItem().getId());

        /* clear data_table */
        data_table.getItems().clear();

        /* read data from db and insert into tableview */
        ArrayList<Types> types = AppSettings.get_types();

        for(Types types_row : types){

            data_table.getItems().add(
                new Types(
                    types_row.getId(), 
                    types_row.getDescription()
                )
            );

        }

    }

    @FXML
    public void onEditCommitData_Table(CellEditEvent<?,?> event){

        switch(event.getTableColumn().idProperty().getValue().toString()){

            case "description_column":
                AppSettings.put_types(
                    data_table.getSelectionModel().getSelectedItem().getId().toString(), 
                    event.getNewValue().toString()
                );

                break;

        }

        /* read data from db and insert into tableview */
        ArrayList<Types> Types = AppSettings.get_types();

        data_table.getItems().clear();
        for(Types types_row : Types){

            data_table.getItems().add(
                new Types(
                    types_row.getId(), 
                    types_row.getDescription()
                )
            );

        }

        (new MainController()).reloadCombos();

    }

    private void loadTable(){

        /* load id columns */
        id_column.setCellValueFactory(new PropertyValueFactory<>("Id"));
        id_column.setCellFactory(TextFieldTableCell.<Types, Integer>forTableColumn(new IntegerStringConverter()));

        /* load description column */
        
        description_column.setCellValueFactory(new PropertyValueFactory<>("Description"));
        description_column.setCellFactory(TextFieldTableCell.<Types>forTableColumn());

        /* read data from db and insert into tableview */
        ArrayList<Types> Types = AppSettings.get_types();

        for(Types types_row : Types){

            data_table.getItems().add(
                new Types(
                    types_row.getId(), 
                    types_row.getDescription()
                )
            );

        }
        
        /* set no results message */
        data_table.setPlaceholder(new Label("No search results."));

    }
}
