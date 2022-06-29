package com.patryk.mathdoku.errorChecking;

import com.patryk.mathdoku.cageData.CageData;
import com.patryk.mathdoku.UserData;
import com.patryk.mathdoku.util.BoardPosVec;

public class UserErrorChecker {
      /*
    every time a change to a cell is made, a check is made whether the row, column and
     cage are valid.

    a row/column is invalid if there is nothing there if there is at least one repeated number. Otherwise, it is valid.
     Therefore, a row may be invalid if it is not yet full

    Each row/col has a bit string where a 1 in the bit string represents a presence of the
    number in that row.

    a cage is invalid if it is full and if the target cannot be created using the sign and the numbers in the cage.
     Therefore, a cage cannot be classed as invalid if it is not yet full.



     Additionally, if the row is valid and if the length of the set is equal to the board
      width, it means the row is full.

    to check if a cage is invalid, first make sure that it is full and if yes, try and see
     if the target can be created by using the sign on the numbers.

    to check if the target can be created by using the sign on the numbers, if it's a
     symmetric operator, test it on all the combinations (not permutations), of the numbers,
     picking out from 2 to n numbers.

     if it's a non-symmetric operator, you will need to test it out on all permutations

 */




    int boardWidth;
    UserData userData;
    RCChecker rcChecker;
    CageChecker cageChecker;
    CageData cageData;


    public UserErrorChecker(int boardWidth, UserData userData, CageData cageData) {
        this.boardWidth = boardWidth;
        this.userData = userData;
        this.cageData = cageData;

        rcChecker = new RCChecker(boardWidth);
        cageChecker = new CageChecker(userData, cageData);
        recalculate();
    }


    public boolean noErrors() {
        return rcChecker.noErrors() && cageChecker.noErrors();
    }

    //public void showErrors(Error )



    private void onSingleCellChange(UserData.ChangeListener.SingleCellChange changeData) {

        /*
        bef     after   call entered    call removed
        x       0       0               1
        0       x       1               0
        x       y       1               1
*/

        if (changeData.getOldValue() != 0) {
            onDigitRemoved(changeData);
        }

        if (changeData.getNewValue() != 0) {
            onDigitEntered(changeData);
        }

    }

    private void onDigitEntered(UserData.ChangeListener.SingleCellChange changeData) {
        rcChecker.onDigitEntered(changeData.getCellChanged(), changeData.getNewValue());
        cageChecker.onDigitEntered(changeData);

    }

    private void onDigitRemoved(UserData.ChangeListener.SingleCellChange changeData) {
        rcChecker.onDigitRemoved(changeData.getCellChanged(), changeData.getOldValue());
        cageChecker.onDigitRemoved(changeData);
    }

    private void onMultipleCellChange (UserData.ChangeListener.MultipleCellChange changeData) {
        if (!changeData.isCleared()) {
            //because there are some values, recalculate
            recalculate();
        } else {
            rcChecker.reset();
            cageChecker.reset();
        }
    }

    /**
     * Called whenever the grid is changed in any way, either by user or by undo/redo.
     * This causes it to recalculate its internal data.
     * @param changeData
     */
    public void onGridChange(UserData.ChangeListener.ChangeData changeData) {
        //rcchecker updates itself at every change, while cageChecker only recalculates when the grid is full.
        //however, everything can be recalculated when the check button is pressed
        if (changeData instanceof UserData.ChangeListener.SingleCellChange) {
            onSingleCellChange((UserData.ChangeListener.SingleCellChange) changeData);
        } else if (changeData instanceof UserData.ChangeListener.MultipleCellChange) {
            onMultipleCellChange((UserData.ChangeListener.MultipleCellChange) changeData);
        }

    }

    //this method may be called when the check button is pressed
    public void recalculate() {
        //no need to recalculate rc info since that is updated live
        rcChecker.recalculate(userData);
    }

    public void reset() {
        rcChecker.reset();
    }


    public void showErrors(ErrorShower errorShower) {
        //rows and columns
        for (int i = 0; i < boardWidth; i++) {
            // is the row erroneous?
            if (rcChecker.isRCInvalid(true, i))
                errorShower.onRowColInvalid(true, i);

            //is the column erroneous??
            if (rcChecker.isRCInvalid(false, i))
                errorShower.onRowColInvalid(false, i);

        }

        //cages
        for (CageInfo cageInfo: cageChecker.getCageInfos()) {
            if (cageInfo.isCageInvalid())
                errorShower.onCageInvalid(cageInfo.getCage());
        }
    }

}
