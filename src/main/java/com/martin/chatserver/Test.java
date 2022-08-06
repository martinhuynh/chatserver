package com.martin.chatserver;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

//testing 
public class Test extends Application {

    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Test");
        button = new Button("Click Me");
        button.setTranslateX(100);
        // button.setText("Click me");
        Circle circle = new Circle();
        circle.setRadius(100);
        StackPane layout = new StackPane();

        layout.getChildren().add(circle);
        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 400, 500);
        // addTextBox(stage, layout);
        stage.setScene(scene);
        stage.show();
        Translate translate = new Translate();
        circle.getTransforms().add(translate);
        button.setOnAction(action -> {
            translate.setX(translate.getX() + 10);
        });
    }

    private void addTextBox(Stage stage, StackPane layout) {
        TextField textField = new TextField();

        Button button = new Button("Click to get text");
        button.setStyle("-fx-background-color: #20B2AA; -fx-background-radius: 15px; -fx-text-fill: #ffffff");

        button.setOnAction(action -> {
            System.out.println(textField.getText());
        });

        HBox hbox = new HBox(textField, button);
        layout.getChildren().add(hbox);
    }
}
