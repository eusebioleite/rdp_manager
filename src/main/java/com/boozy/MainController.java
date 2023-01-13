package com.boozy;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class MainController {
    
    @FXML
    TextField search_bar = new TextField();

    @FXML
    private ComboBox<String> filter_type = new ComboBox<String>();
    
    @FXML
    private TableView<RDPs> data_table = new TableView<RDPs>();

    @FXML
    private TableColumn<RDPs, String> id_column = new TableColumn<>();

    @FXML
    private TableColumn<RDPs, String> description_column = new TableColumn<>();

    @FXML
    private TableColumn<RDPs, String> type_column = new TableColumn<>();

    @FXML
    private TableColumn<RDPs, String> company_column = new TableColumn<>();

    @FXML
    private TableColumn<RDPs, String> connectioninfo_column = new TableColumn<>();



    @FXML
    private void initialize(){
        
        loadFilterType();

        loadTable();
    }

    private void loadFilterType(){
        filter_type.getItems().add("Connection Type");
        filter_type.getItems().add("Company");
    }

    private void loadTable(){

        data_table.setEditable(true);

        id_column.setCellValueFactory(new PropertyValueFactory<>("Id"));
        id_column.setCellFactory(TextFieldTableCell.<RDPs>forTableColumn());

        description_column.setCellValueFactory(new PropertyValueFactory<>("Description"));
        description_column.setCellFactory(TextFieldTableCell.<RDPs>forTableColumn());

        type_column.setCellValueFactory(new PropertyValueFactory<>("Type"));
        type_column.setCellFactory(ComboBoxTableCell.<RDPs, String>forTableColumn("RDP", "AnyDesk", "TeamViewer"));

        company_column.setCellValueFactory(new PropertyValueFactory<>("Company"));
        company_column.setCellFactory(ComboBoxTableCell.<RDPs, String>forTableColumn("Furlan", "Olsa", "Barros"));

        connectioninfo_column.setCellValueFactory(new PropertyValueFactory<>("ConnectionInfo"));
        connectioninfo_column.setCellFactory(TextFieldTableCell.<RDPs>forTableColumn());

        data_table.getItems().add(new RDPs("1", "IIS", "RDP", "Furlan", "10.0.0.2"));
        data_table.getItems().add(new RDPs("2", "Debex", "AnyDesk", "Olsa", "289521373"));
        data_table.getItems().add(new RDPs("3", "IIS", "TeamViewer", "Barros", "38129448194"));
        
        data_table.setPlaceholder(new Label("No search results."));

    }
}
