package com.patryk.mathdoku.gui;

import com.patryk.mathdoku.GameContext;
import com.patryk.mathdoku.actions.UndoRedoButtonManager;
import com.patryk.mathdoku.UserData;
import com.patryk.mathdoku.cageData.DataFormatException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameUI {

    private static final int MAX_WIDTH = 8;
    private Scene scene;
    GameContext gameContext;
    public boolean wonBefore = false;

    public boolean hasWonBefore() {
        return wonBefore;
    }

    VBox controlPane;

    public Button undoButton = new Button("Undo");
    public Button redoButton = new Button("Redo");
    public Button clearButton = new Button("Clear");
    public Button checkButton = new Button("Check");
    public Button solveButton = new Button("Solve");
    public Button fileLoadButton = new Button("Load game from file");
    public Button textLoadButton = new Button("Load game from text input");
    public Button ranGameButton = new Button("Random game");

    public ComboBox<GameGridView.FontSize> fontSizeComboBox;
    GameGridView.FontSize defaultFontSize = GameGridView.FontSize.MEDIUM;


    VBox numberPad = new VBox();
    public GameGridView gameGridView;
    UndoRedoButtonManager undoRedoButtonManager;

    UserData.ChangeListener onUserDataChanged = (changeData) -> {
        if (gameContext.isWon() && !wonBefore) {
            wonBefore = true;
            showWinningAnimation();
        }

        if (gameContext.getUserData().isEmpty()) {
            clearButton.setDisable(true);
        } else {
            clearButton.setDisable(false);
        }

    };


    public interface NumberButtonCallback {
        void onNumberButtonPressed(int buttonNumber);
    }

    NumberButtonCallback numberButtonCallback;


    EventHandler<ActionEvent> onNumberButtonPressed = (event) -> {
        Button button = (Button)event.getSource();
        numberButtonCallback.onNumberButtonPressed((Integer)button.getUserData());
    };


    public GameUI() {
        undoRedoButtonManager = new UndoRedoButtonManager(undoButton, redoButton);
        //initialize control pane
        controlPane = new VBox();
        controlPane.getChildren().addAll(undoButton, redoButton, clearButton, checkButton, solveButton, fileLoadButton, textLoadButton, ranGameButton);

        //disable clear and check buttons
        clearButton.setDisable(true);
        checkButton.setDisable(true);

        //font combobox#
        fontSizeComboBox = new ComboBox<>();
        fontSizeComboBox.setValue(defaultFontSize);
        fontSizeComboBox.getItems().addAll(GameGridView.FontSize.SMALL,
                GameGridView.FontSize.MEDIUM,
                GameGridView.FontSize.LARGE);

        fontSizeComboBox.setDisable(true);
        //fontSizeComboBox.se
        fontSizeComboBox.valueProperty().addListener(new ChangeListener<GameGridView.FontSize>() {
            @Override
            public void changed(ObservableValue<? extends GameGridView.FontSize> observableValue, GameGridView.FontSize oldSize, GameGridView.FontSize newSize) {
                gameGridView.setFontSize(newSize);
            }
        });

        //initialize button pane, disable all
        for (int i = 1; i <= MAX_WIDTH; i++) {
            Button button = new Button(Integer.toString(i));
            button.setUserData(i);
            button.addEventHandler(ActionEvent.ANY, onNumberButtonPressed);
            numberPad.getChildren().add(button);
            button.setDisable(true);
        }



        //init game grid view
        gameGridView = new GameGridView(600);

        //initialize master layout
        HBox masterLayout = new HBox();
        masterLayout.getChildren().addAll(controlPane, gameGridView.getNode(), numberPad, fontSizeComboBox);

        //init scene
        scene = new Scene(masterLayout);

    }

    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
        undoRedoButtonManager.setGameContext(gameContext);
        wonBefore = false;

        //enable some buttons
        checkButton.setDisable(false);

        fontSizeComboBox.setDisable(false);

        //enable buttons which are valid ...
        for (int i = 0; i < MAX_WIDTH; i++) {
            boolean disable = i >= gameContext.getBoardWidth();
            numberPad.getChildren().get(i).setDisable(disable);
        }


        //init game grid view
        gameGridView.setGameContext(gameContext, defaultFontSize);

        gameGridView.getNode().requestFocus();
        gameContext.getUserData().addChangeListener(onUserDataChanged);
        //init scene
        gameGridView.configureKeyEvents();

    }

    private void showWinningAnimation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations!");
        alert.setHeaderText("You won the game!");
        try {
            alert.setGraphic(new ImageView(new Image(new FileInputStream("fireworks.jpg"))));
        } catch (FileNotFoundException e) {

        }
        alert.showAndWait();
    }

    public void setNumberButtonCallback(NumberButtonCallback numberButtonCallback) {
        this.numberButtonCallback = numberButtonCallback;
    }

    public void showDataFormatErrorAlert(DataFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Data format error.");
        alert.setContentText(e.getMessage());
        alert.setResizable(true);
        alert.showAndWait();
    }

    public boolean showConfirmExitDialog() {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Exit");
        confirmDialog.setHeaderText("A game is currently running.");
        confirmDialog.setContentText("Are you sure you want to exit? You will lose your progress!");
        confirmDialog.setResizable(true);

        ButtonType userChoice = confirmDialog.showAndWait().get();

        return userChoice.equals(ButtonType.OK);
    }

    public boolean showConfirmDialog( ) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm action,");
        confirmDialog.setContentText("Are you sure?");

        ButtonType userChoice = confirmDialog.showAndWait().get();

        return userChoice.equals(ButtonType.OK);

    }
    public Scene getScene() {
        return scene;
    }

}

