package com.patryk.mathdoku;

import com.patryk.mathdoku.errorChecking.ErrorShower;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainWindow {

    Scene scene;

    VBox controlPane;

    Button undoButton = new Button("Undo");
    Button redoButton = new Button("Redo");
    Button clearButton = new Button("Clear");
    Button checkButton = new Button("Check");
    Button fileLoadButton = new Button("Load game from file");
    Button textLoadButton = new Button("Load game from text input");


    VBox numberPad = new VBox();
    GameGridView gameGridView;
    UndoRedoButtonManager undoRedoButtonManager;

    EventHandler<ActionEvent> onClearButtonPressed = (ActionEvent actionEvent) -> gameGridView.getGameContext().clear(true);
    EventHandler<ActionEvent> onUndoButtonPressed = (ActionEvent event) -> {
        gameGridView.getGameContext().undo();
    };
    EventHandler<ActionEvent> onRedoButtonPressed = (ActionEvent event) -> gameGridView.getGameContext().redo();

    EventHandler<ActionEvent> onCheckButtonPressed = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent actionEvent) {
            GameContext.me().getErrorChecker().showErrors(gameGridView.getErrorCallback());
        }
    };





    public MainWindow(GameContext gameContext) {

        undoRedoButtonManager = new UndoRedoButtonManager(undoButton, redoButton, gameContext.me().getActionRecorder());
        //initialize control pane
        controlPane = new VBox();
        controlPane.getChildren().addAll(undoButton, redoButton, clearButton, checkButton, fileLoadButton, textLoadButton);

        //clear button
        clearButton.addEventHandler(ActionEvent.ANY, onClearButtonPressed);

        //undo button
        undoButton.addEventHandler(ActionEvent.ANY, onUndoButtonPressed);

        redoButton.addEventHandler(ActionEvent.ANY, onRedoButtonPressed);
        redoButton.setDisable(true);

        //initialize button pane
        //todo give them actions!
        for (int i = 1; i <= gameContext.getBoardWidth(); i++) {
            controlPane.getChildren().add(new Button(Integer.toString(i)));
        }

        checkButton.addEventHandler(ActionEvent.ANY, onCheckButtonPressed);

        //init game grid view
        gameGridView = new GameGridView(gameContext);


        //initialize master layout
        HBox masterLayout = new HBox();
        masterLayout.getChildren().addAll(gameGridView.getNode(), controlPane, numberPad);

        //init scene
        scene = new Scene(masterLayout, gameGridView.getPixelWidth() + 250, gameGridView.getPixelWidth());
        gameGridView.configureKeyEvents();
        gameGridView.getNode().requestFocus();



    }

    public Scene getScene() {
        return scene;
    }

}

