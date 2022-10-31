package com.patryk.mathdoku;

import com.patryk.mathdoku.cageData.Cage;
import com.patryk.mathdoku.cageData.CageData;
import com.patryk.mathdoku.cageData.DataFormatException;
import com.patryk.mathdoku.solver.CageSolver;
import com.patryk.mathdoku.gui.GameUI;
import com.patryk.mathdoku.gui.ManualGameInputDialog;
import com.patryk.mathdoku.gui.RandGameDialog;
import com.patryk.mathdoku.randomGame.RandomGame;
import com.patryk.mathdoku.solver.Solver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class MathDoku extends Application {
    GameUI gameUI;
    GameContext gameContext;
    Stage stage;

    private static final String title = "MathDoku!";


    EventHandler<ActionEvent> onFileLoadButtonPressed = (event) -> {

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file == null)
            return;

        try {

            setGameContext(new GameContext(Files.readString(Paths.get(file.getAbsolutePath()))));
        } catch (IOException e) {
            //this should never happen
        } catch (DataFormatException e) {
            gameUI.showDataFormatErrorAlert(e);
        }

    };

    EventHandler<ActionEvent> onManualInputButtonPressed = (event) -> {
        ManualGameInputDialog dialog = new ManualGameInputDialog();
        while (true) {

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    setGameContext(new GameContext(result.get()));
                    break;
                } catch (DataFormatException e) {
                    gameUI.showDataFormatErrorAlert(e);
                }
            } else {
                break;
            }

        }


    };

    EventHandler<ActionEvent> onRanGameButtonPressed = (event) -> {
        RandGameDialog dialog = new RandGameDialog();

        Optional<RandGameDialog.RanGameInfo> result = dialog.showAndWait();

        if (result.isPresent()) {
            int seed;
            seed = result.get().seed;
            int size = result.get().width;

            CageData cageData = RandomGame.randomGame(seed, size);
            setGameContext(new GameContext(cageData));
        }


    };

    private void onSolveButtonPressed(ActionEvent event) {
        gameContext.userData.clear();
        gameUI.wonBefore = true;
        Solver.solve(gameContext.getUserData(), gameContext.getCageData());
    }



    //key event handler
    private EventHandler<KeyEvent> keyEventHandler = keyEvent -> {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            if (wantsToExit()) {
                Platform.exit();
            }
        }
    };


    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);

        gameUI = new GameUI();

        primaryStage.setOnCloseRequest((windowEvent) -> {
            if (wantsToExit()) {
                Platform.exit();
            } else {
                windowEvent.consume();
            }
        });

        initUiFunctionality();

        primaryStage.setScene(gameUI.getScene());
        primaryStage.setTitle(title);
        primaryStage.show();


    }

    /*In order to abstract the functionality of the GUI from the layout, i have decided to
     declare the layout in the gameUI class and the functionality in this class.
     This is the way Qt does it in C++, which inspired me to do it that way in case you are
     wondering.*/
    private void initUiFunctionality() {
        gameUI.undoButton.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> {
            if (gameUI.showConfirmDialog())
                gameContext.undo();
        });


        gameUI.redoButton.addEventHandler(ActionEvent.ANY,(ActionEvent event) -> {
            if (gameUI.showConfirmDialog())
                gameContext.redo();
        });
        gameUI.clearButton.addEventHandler(ActionEvent.ANY, (ActionEvent actionEvent) -> {
            if (gameUI.showConfirmDialog())
                gameContext.clear(true);
        });

        gameUI.checkButton.addEventHandler(ActionEvent.ANY,(event) ->  gameUI.gameGridView.showErrors());
        gameUI.fileLoadButton.addEventHandler(ActionEvent.ANY, onFileLoadButtonPressed);
        gameUI.textLoadButton.addEventHandler(ActionEvent.ANY, onManualInputButtonPressed);
        gameUI.ranGameButton.addEventHandler(ActionEvent.ANY, onRanGameButtonPressed);
        gameUI.solveButton.addEventHandler(ActionEvent.ANY, this::onSolveButtonPressed);

        gameUI.setNumberButtonCallback((digit) -> gameUI.gameGridView.getInputHandler().injectNumberKey(digit));

    }

    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
        gameUI.setGameContext(gameContext);

    }

    public boolean wantsToExit() {
        //todo debug
        return true;
//        if (shouldDisplayExitDialog()) {
//            //show confirmation dialog
//
//
//            return gameUI.showConfirmExitDialog();
//        }
//        return true;
    }

    public boolean shouldDisplayExitDialog() {
        if (gameContext != null) {
            return !(gameContext.getUserData().isEmpty() || gameUI.hasWonBefore());
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }

}

