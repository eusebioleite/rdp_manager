package com.boozy;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Tests extends Application {

    @Override
    public void start(Stage primaryStage) {

        HBox buttonBox1 = new HBox();
        for (int i = 0; i < 10; i++) {
            ToggleButton toggle1 = new ToggleButton();
            FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.ANGELLIST);
            //iconView.setGlyphSize(42.0);
            toggle1.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            toggle1.setGraphic(iconView);
            buttonBox1.getChildren().add(toggle1);
        }

        HBox buttonBox2 = new HBox();
        for (int i = 0; i < 10; i++) {
            ToggleButton toggle1 = FontAwesomeIconFactory.get().createIconToggleButton(FontAwesomeIcon.ANGELLIST, "", "2em");
            buttonBox2.getChildren().add(toggle1);
        }

        FlowPane iconsPane = new FlowPane(3, 3);
        iconsPane.getChildren().add(buttonBox1);
        iconsPane.getChildren().add(buttonBox2);
        for (FontAwesomeIcon icon : FontAwesomeIcon.values()) {
            iconsPane.getChildren().add(FontAwesomeIconFactory.get().createIcon(icon, "3em"));
        }
        Scene scene = new Scene(new ScrollPane(iconsPane), 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("FontAwesomeFX: FontAwesomeIcons Demo: " + FontAwesomeIcon.values().length + " Icons");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
