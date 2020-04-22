package com.patryk.mathdoku.errorChecking;

import com.patryk.mathdoku.Cage;
import com.patryk.mathdoku.RecursiveSolver;
import com.patryk.mathdoku.UserData;

import java.util.List;

public class CageInfo {
    private Cage cage;
    private boolean isInvalid = false;
    private boolean recordValid = false;
    private int populationCount = 0;


    public CageInfo (Cage cage) {
        this.cage = cage;
        isInvalid = false;
    }


    public boolean isCageInvalid() {
        return isInvalid;
    }

    public Cage getCage() {
        return cage;
    }

    public boolean isRecordOutOfDate() {
        return !recordValid;
    }

    public void setRecordValidity(boolean value) {
        recordValid = value;}


    public void setCorrectness(boolean value) {
        isInvalid = value;
    }

    public void onDigitEntered() {
        populationCount++;
        if (isFull()) {
            int[] cageMemberData = getMembersOfCage();
            isInvalid = !RecursiveSolver.testSign(cageMemberData, cage.getTarget(), cage.getOperator());
            System.out.println("Cage has been made full. Is there a problem?: " + isInvalid);
        }
    }

    public void onDigitRemoved() {
        isInvalid = false;
        populationCount--;
    }

    public int[] getMembersOfCage() {

        List<Integer> memberCells = cage.getMemberCells();
        int[] memberData = new int[memberCells.size()];
        for (int i = 0; i < memberCells.size(); i++) {
            memberData[i] = UserData.me().getValueAtCell(memberCells.get(i));
        }

        return memberData;
    }

    public boolean isFull() {
        //
        return populationCount == cage.getCellCount();
    }

    public void reset() {
        isInvalid = false;
    }
}
