package com.patryk.mathdoku;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class NewGameWindow extends Stage {
    private static int WIDTH = 400;
    private static int HEIGHT = 300;
    Scene scene;
    Button manualInputButton;
    Button fileButton;
    Button cancelButton;

    public NewGameWindow() {
        setTitle("New Game ...");
        VBox masterLayout = new VBox(
                new Label("How do you want to load the game?"),
                new HBox(new Label("By file:"), fileButton = new Button("Load file")),
                new HBox(new Label("Or"), manualInputButton = new Button(" type it up manually")),
                cancelButton = new Button("Cancel")

        );

        masterLayout.setSpacing(0.4);

        manualInputButton.addEventHandler(ActionEvent.ANY, (Event e) -> {
            Dialog<String> dialog = new ManualGameInputDialog();
            System.out.println("Return value: " + dialog.showAndWait().get());
        });

        fileButton.addEventHandler(ActionEvent.ANY, (Event e) -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(this);
            System.out.println("file chosen: " + file.getAbsolutePath());
        });

        cancelButton.addEventHandler(ActionEvent.ANY, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                close();
            }
        });

        scene = new Scene(masterLayout, WIDTH, HEIGHT);
        setScene(scene);



    }
}
