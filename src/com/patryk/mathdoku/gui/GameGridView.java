package com.patryk.mathdoku.gui;

import com.patryk.mathdoku.cageData.Cage;
import com.patryk.mathdoku.GameContext;
import com.patryk.mathdoku.UserData;
import com.patryk.mathdoku.gui.drawers.*;
import com.patryk.mathdoku.errorChecking.ErrorShower;
import com.patryk.mathdoku.util.BoardPosVec;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;


public class GameGridView {

    public enum FontSize {SMALL, MEDIUM, LARGE};
    FontSize fontSize;

    private int pixelWidth;

    private CageDrawer cageDrawer;
    private SelectedCellDrawer selectedCellDrawer;
    private UserDataDrawer userDataDrawer;
    private ErrorsHighlighter errorHighlighter;

    private final Drawer[] drawers;

    Group group;

    InputHandler inputHandler = new InputHandler(this);

    private GameContext gameContext;
    BoardPosVec selectedCell = new BoardPosVec(0, 0);

    ErrorShower gameErrorCallback = new ErrorShower() {
        @Override
        public void onCageInvalid(Cage cage) {
            errorHighlighter.drawErroneousCage(cage);
        }

        @Override
        public void onRowColInvalid(boolean isRow, int index) {
            errorHighlighter.drawErroneousRow(isRow, index);
        }
    };

    UserData.ChangeListener redrawGame = (data) -> {
        userDataDrawer.draw();
        errorHighlighter.clearCanvas();
    };


    public GameGridView(int pixelWidth) {
        this.pixelWidth = pixelWidth;
        Drawer.setPixelWidth(pixelWidth);
        BoardPosVec.setPixelWidth(pixelWidth);
        //create canvases

        cageDrawer = new CageDrawer();
        userDataDrawer = new UserDataDrawer();
        selectedCellDrawer = new SelectedCellDrawer();
        errorHighlighter = new ErrorsHighlighter();

        drawers = new Drawer[]{cageDrawer, selectedCellDrawer, userDataDrawer, errorHighlighter};

        //add them to group
        group = new Group();
        group.getChildren().addAll(cageDrawer.getCanvas(), selectedCellDrawer.getCanvas(), userDataDrawer.getCanvas(), errorHighlighter.getCanvas());
        userDataDrawer.getCanvas().toFront();
        errorHighlighter.getCanvas().toBack();

    }

    private void clear() {
        for (Drawer drawer: drawers)
            drawer.clearCanvas();
    }

    public void showErrors() {
        gameContext.getErrorChecker().showErrors(gameErrorCallback);
    }

    public void setGameContext(GameContext gameContext, FontSize fontSize) {
        clear();
        Drawer.setBoardWidth(gameContext.getBoardWidth());
        Drawer.setFontSize(fontSize);
        //note game context
        this.gameContext = gameContext;
        //Drawer.init(gameContext.getBoardWidth(), pixelWidth);

        gameContext.getUserData().addChangeListener(redrawGame);

        //link cage drawer to canvas and draw the cages

        userDataDrawer.setData(gameContext.getUserData());
        cageDrawer.setData(gameContext.getCageData());
        userDataDrawer.updateFontSize();
        cageDrawer.updateFontSize();

        selectedCellDrawer.draw(selectedCell);

        //link the user data drawer but don't draw the user data
        //userDataDrawer.draw();
        //link selected cell drawer to canvas

        //error checking

    }

    public void setFontSize(FontSize fontSize) {

        Drawer.setFontSize(fontSize);
        cageDrawer.updateFontSize();
        userDataDrawer.updateFontSize();

    }

    public void configureKeyEvents() {
        //initialize input handler
        group.addEventHandler(MouseEvent.MOUSE_CLICKED, inputHandler.getMouseHandler());
        group.getScene().setOnKeyPressed(inputHandler.getKeyEventHandler());

    }

    public InputHandler getInputHandler() {
        return inputHandler;
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

    public int getPixelWidth() {
        return pixelWidth;
    }


    public GameContext getGameContext() {
        return gameContext;
    }

    public Node getNode() {
        return group;
    }
}

