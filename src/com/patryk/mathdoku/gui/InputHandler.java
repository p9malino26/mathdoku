package com.patryk.mathdoku.gui;

import com.patryk.mathdoku.util.Direction;
import com.patryk.mathdoku.util.Util;
import com.patryk.mathdoku.util.BoardPosVec;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class InputHandler {
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

        Direction direction;// = Util.Direction.NORTH;
        switch (event.getCode()) {

            case W:
            case UP:
                direction = Direction.NORTH;
                break;
            case D:
            case RIGHT:
                direction = Direction.EAST;
                break;

            case S:
            case DOWN:
                direction = Direction.SOUTH;
                break;

            case A:
            case LEFT:
                direction = Direction.WEST;
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
                    injectNumberKey(Util.charToInt(keyCharacter));
                    return true;
                }

                return false;


        }


        return true;
    }

    public void injectNumberKey(int digit) {
        view.getGameContext().setValueAtCell(view.getSelectedCell(), digit, true);
    }

    //returns handlers so that the view can register them
    public EventHandler<MouseEvent> getMouseHandler() {
        return mouseHandler;
    }

    public EventHandler<KeyEvent> getKeyEventHandler() {
        return keyEventHandler;
    }
}
