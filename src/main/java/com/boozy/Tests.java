package com.boozy;

import java.util.ArrayList;

import com.boozy.tables.Company;
import com.boozy.tables.Rdp;
import com.boozy.tables.Types;
import com.boozy.tables.view.RdpView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Tests {
    
    public static void main(String[] args) {
        
        /* read data from db and insert into tableview */
        ArrayList<RdpView> rdpview = Sqlite_JDBC_Connector.get_rdpview_by_string("IIS");
        
        for(RdpView rdpview_row : rdpview){

            System.out.println("id: " + rdpview_row.getId());
            System.out.println("description: " + rdpview_row.getDescription());
            System.out.println("type: " + rdpview_row.getTypes_description());
            System.out.println("company: " + rdpview_row.getCompany_description());
            System.out.println("connection_info: " + rdpview_row.getConnection_info());

        }

    }

}
