package com.patryk.mathdoku;

import com.patryk.mathdoku.drawers.CageDrawer;
import com.patryk.mathdoku.drawers.Drawer;
import com.patryk.mathdoku.drawers.SelectedCellDrawer;
import com.patryk.mathdoku.drawers.UserDataDrawer;
import com.patryk.mathdoku.errorChecking.ErrorShower;
import com.patryk.mathdoku.global.BoardPosVec;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class GameGridView {
    private static int pixelWidth= 600;

    private CageDrawer cageDrawer;
    private SelectedCellDrawer selectedCellDrawer;
    private UserDataDrawer userDataDrawer;
    private ErrorsHighlighter errorHighlighter;

    Canvas cageCanvas;
    Canvas selectedCellCanvas;
    Canvas userNumbersCanvas;
    Canvas errorCanvas;

    Group group;

    InputHandler inputHandler = new InputHandler(this);

    private GameContext gameContext;
    BoardPosVec selectedCell = new BoardPosVec(0, 0);

    ErrorShower errorCallback;
    public GameGridView(GameContext gameContext) {

        //create canvases
        cageCanvas = new Canvas(pixelWidth, pixelWidth);
        selectedCellCanvas = new Canvas(pixelWidth, pixelWidth);
        userNumbersCanvas = new Canvas(pixelWidth, pixelWidth);
        errorCanvas = new Canvas(pixelWidth, pixelWidth);

        //add them to group
        group = new Group();
        group.getChildren().addAll(cageCanvas, selectedCellCanvas, userNumbersCanvas, errorCanvas);
        errorCanvas.toBack();

        //note game context
        this.gameContext = gameContext;

        gameContext.getUserData().addChangeListener(new UserData.ChangeListener() {
            @Override
            public void onDataChanged(UserData.ChangeListener.ChangeData data) {
                userDataDrawer.draw();
                errorHighlighter.clearCanvas();
            }
        });

        //link cage drawer to canvas and draw the cages
        cageDrawer = new CageDrawer(gameContext.getCageData(), cageCanvas.getGraphicsContext2D());
        cageDrawer.draw();

        //link the user data drawer but don't draw the user data
        userDataDrawer = new UserDataDrawer(userNumbersCanvas.getGraphicsContext2D(), gameContext.getUserData());
        userDataDrawer.draw();
        //link selected cell drawer to canvas
        selectedCellDrawer = new SelectedCellDrawer(selectedCellCanvas.getGraphicsContext2D());
        selectedCellDrawer.draw(selectedCell);

        //draw the selected cell
        selectedCellDrawer.draw(selectedCell);

        //error checking
        errorHighlighter = new ErrorsHighlighter(errorCanvas.getGraphicsContext2D());

        errorCallback = new ErrorShower() {
            @Override
            public void onCageInvalid(Cage cage) {
                errorHighlighter.drawErroneousCage(cage);
            }

            @Override
            public void onRowColInvalid(boolean isRow, int index) {
                errorHighlighter.drawErroneousRow(isRow, index);
            }
        };


    }

    public void configureKeyEvents() {
        //initialize input handler
        group.addEventHandler(MouseEvent.MOUSE_CLICKED, inputHandler.getMouseHandler());
        group.getScene().setOnKeyPressed(inputHandler.getKeyEventHandler());

    }

    public void setSelectedCell(BoardPosVec selectedCell) {
        if (!selectedCell.clampToArea()) {
            this.selectedCell = selectedCell;
            selectedCellDrawer.draw(selectedCell);
        }
    }

    public BoardPosVec getSelectedCell() {
        return selectedCell;
    }

    public static int getPixelWidth() {
        return pixelWidth;
    }

    public static int getSquarePixelWidth() {
        return pixelWidth / GameContext.getBoardWidth();
    }

    public GameContext getGameContext() {
        return gameContext;
    }

    public ErrorShower getErrorCallback() {
        return errorCallback;
    }

    public Node getNode() {
        return group;
    }
}

class InputHandler {
    //the class must store view in order to be able to call its methods
    GameGridView view;

    //constructor
    public InputHandler(GameGridView view){
        this.view = view;

    }

    //mouse callback
    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            view.getNode().requestFocus();
            BoardPosVec selectedCellPos = new BoardPosVec((int)mouseEvent.getY(), (int)mouseEvent.getX());
            selectedCellPos = selectedCellPos.fromPixelToBoardSpace();
            view.setSelectedCell(selectedCellPos);

        }
    };

    //key callback
    EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
        @Override
        //can either be user entering a value, or navigating
        public void handle(KeyEvent keyEvent) {
            if (!handleDirectionKeyPress(keyEvent)) handleValueEntered(keyEvent);
        }
    };

    public boolean handleDirectionKeyPress(KeyEvent event) {

        Util.Direction direction;// = Util.Direction.NORTH;
        switch (event.getCode()) {

            case W:
            case UP:
                direction = Util.Direction.NORTH;
                break;
            case D:
            case RIGHT:
                direction = Util.Direction.EAST;
                break;

            case S:
            case DOWN:
                direction = Util.Direction.SOUTH;
                break;

            case A:
            case LEFT:
                direction = Util.Direction.WEST;
                break;


            default:
                return false;


        }

        //consume if arrow key to prevent loss of focus
        switch (event.getCode()) {
            case UP:
            case RIGHT:
            case DOWN:
            case LEFT:
                event.consume();
        }
        view.setSelectedCell(view.getSelectedCell().add(direction.vector));
        return true;
    }

    public boolean handleValueEntered (KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case BACK_SPACE:
                view.getGameContext().setValueAtCell(view.getSelectedCell(), 0, true);
                break;
            default:
                char keyCharacter = keyEvent.getText().charAt(0);
                char maxChar = Character.forDigit(view.getGameContext().getBoardWidth(), 10);
                //if digit entered:
                if ( keyCharacter >='1'&& keyCharacter <= maxChar) {
                    view.getGameContext().setValueAtCell(view.getSelectedCell(), Util.charToInt(keyCharacter), true);
                    return true;
                }

                return false;


        }

        return true;
    }

    //returns handlers so that the view can register them
    public EventHandler<MouseEvent> getMouseHandler() {
        return mouseHandler;
    }

    public EventHandler<KeyEvent> getKeyEventHandler() {
        return keyEventHandler;
    }
}
