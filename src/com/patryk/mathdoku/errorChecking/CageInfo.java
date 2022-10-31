package com.patryk.mathdoku.errorChecking;

import com.patryk.mathdoku.cageData.Cage;
import com.patryk.mathdoku.UserData;

import java.util.List;

public class CageInfo {
    private Cage cage;
    private boolean isInvalid = false;
    private boolean recordValid = false;
    private int populationCount = 0;
    private static UserData userData;

    public static void setUserData(UserData userData) {
        CageInfo.userData = userData;
    }

    public CageInfo (Cage cage) {
        this.cage = cage;
    }


    public boolean isCageInvalid() {
        return isInvalid;
    }

    public Cage getCage() {
        return cage;
    }

    /*public boolean isRecordOutOfDate() {
        return !recordValid;
    }

    public void setRecordValidity(boolean value) {
        recordValid = value;}


    public void setCorrectness(boolean value) {
        isInvalid = value;
    }*/

    public void onDigitEntered() {
        populationCount++;
        if (isFull()) {
            int[] cageMemberData = getMembersOfCage();
            isInvalid = !RecursiveChecker.testSign(cageMemberData, cage.getTarget(), cage.getOperator());
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
            memberData[i] = userData.getValueAtCell(memberCells.get(i));
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
