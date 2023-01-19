package com.boozy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        /* load fxml */
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
        
        /* create scene */
        scene = new Scene(fxmlLoader.load(), 640, 400);
        stage.setTitle("RDP Manager");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

        launch();
        
    }

}