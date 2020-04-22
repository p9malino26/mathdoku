package com.patryk.mathdoku;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MathDoku extends Application {
    MainWindow mainWindow;
    GameContext gameContext;

    private static final String dataFilePath = "example.txt";
    private static final String title = "MathDoku!";

    int boardWidth = 6;

    //key event handler
    private EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }
        }
    };

    
    public void start(Stage primaryStage) {
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);

        try {
            //set up the game context and the UI
            NewGameWindow newGameWindow = new NewGameWindow();
            newGameWindow.showAndWait();
            gameContext = new GameContext(dataFilePath, boardWidth);
            gameContext.setActive();
            mainWindow = new MainWindow(gameContext);

            //set up the key press
            //creates a blank board with nothing
        /*ui.controlMenu.addEvent(new EventHandler<ActionEvent>(){
            handle() {
                //if it's load from file
                loadFromFile();
            }
        })*/


            primaryStage.setScene(mainWindow.getScene());
            primaryStage.setTitle(title);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Something's wrong with loading the file.");
            Platform.exit();
        }
    }

    //T
    /*private void loadFromFile() {
        if (gameContext != null) {
            //(if there is a loaded game) and it has had some changes made to it
            //ask the user if they really want to over-write the game
        }
        //open file dialog, read data from file and
        try {
            setGameContext(new GameContext(data));
        } catch (InvalidDataException e) {
            //show a dialog saying the error and quit
        }
    }

    private void setGameContext(GameContext context) {
        gameContext = context;
        ui.setGameContext(context);
    }*/

    public static void main(String[] args) {
        launch(args);
    }

}

