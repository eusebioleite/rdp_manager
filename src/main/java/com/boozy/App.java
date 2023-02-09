package com.boozy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.boozy.controllers.MainController;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        /* load fxml */
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));

        /* create window */
        MainController mainController = fxmlLoader.getController();
        scene = new Scene(fxmlLoader.load(), 640, 400);
        stage.setTitle("RDP Manager");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnHidden(e -> mainController.shutdown());
        stage.show();

    }

    public static void main(String[] args) {
        
        AppSettings.create_db();
        launch();
        
    }

}