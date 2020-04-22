import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MathDoku extends Application {

    private static int canvasPixelWidth = 600;
    private static int boardWidth = 6;

    UI ui;

    private InitialData initialData;
    private UserData userData;

    public static ActionRecorderBase<Action> actionRecorder = new CircularActionRecorder();

    public static int getCanvasPixelWidth() {
        return canvasPixelWidth;
    }

    public static int getBoardWidth() {
        return boardWidth;
    }

    public static int getSquarePixelWidth() {
        return canvasPixelWidth / boardWidth;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //GameContext.init(canvasPixelWidth, boardWidth);

        //create the canvas and put it in the window
        primaryStage.setTitle("Mathdoku");

        //create the constant game data
        initialData = new InitialData("example.txt");
        userData = new UserData();

        ui = new UI(initialData, userData);

        markedCellProperty.addListener(new ChangeListener<Util.BoardPosVec>() {
            @Override
            public void changed(ObservableValue<? extends Util.BoardPosVec> observableValue, Util.BoardPosVec oldPos, Util.BoardPosVec newPos) {
                redrawMarkedCell();
            }
        });


        ui.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.g)
        });



        ui.undoButton.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                undo();
            }
        });

        ui.redoButton.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                redo();
            }
        });

        ui.clearButton.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clear();
            }
        });


        userDataDrawer.draw();

        primaryStage.setScene(ui.getScene());


        primaryStage.show();
    }

    /**
     * also checks if the digit is in range
     * @param cell
     * @param digit
     */
    private void appendDigitToCell(Util.BoardPosVec cell, char digit) {
        int newValue = Util.charToInt(digit);
        if (newValue <= GameContext.getInstance().getBoardWidth())
            setValueAtCell(cell, newValue);
    }



    private Util.BoardPosVec getSelectedCell() {
        return this.markedCellProperty.get();
    }

    private void redrawMarkedCell() {
        //TODO set colours, line width
        //clear canvas
        //re-draw the marked cell
        //drawSquare(markedCellCanvas.getGraphicsContext(), markedCellProperty.getValue());
        selectedCellDrawer.draw();

    }

    private void setSelectedCell(Util.BoardPosVec pos) {

        //Util.BoardPosVec newValue = markedCellProperty.get().add(direction.vector);
        //if change has occured to the value
        if (!pos.clampToArea()) {
            markedCellProperty.set(pos);
        }
    }

    private void undo() {
        if (actionRecorder.canUndo()) {
            Action action = actionRecorder.undo();
            if (action.isFlipped()) {
                redoAction(action);
            } else {
                undoAction(action);
            }

        }
    }

    private void redo() {
        if(actionRecorder.canRedo()) {
            Action action = actionRecorder.redo();
            if (action.isFlipped()) {
                undoAction(action);
            } else {
                redoAction(action);
            }
        }
    }

    public void redoAction(Action action) {
        switch (action.getType()) {
            case CHANGE_CELL_VALUE:
                redoSpecificAction((CellValueChangeAction) action);
                break;
            default:
        }    }

    public void undoAction(Action action) {
        switch (action.getType()) {
            case CHANGE_CELL_VALUE:
                undoSpecificAction((CellValueChangeAction) action);
                break;
            default:
        }
    }

    private void undoSpecificAction(CellValueChangeAction action) {
        userData.setValueAtCell(action.getCell(), action.getOldValue());
        userDataDrawer.draw();
    }

    public void redoSpecificAction(CellValueChangeAction action) {
        userData.setValueAtCell(action.getCell(), action.getNewValue());
        userDataDrawer.draw();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
