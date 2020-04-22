package com.patryk.mathdoku.errorChecking;

import com.patryk.mathdoku.CageData;
import com.patryk.mathdoku.UserData;

public class CageChecker {
    CageData cageData;
    CageInfo[] cageInfos;
    UserData userData;

    public CageChecker(CageData cageData) {
        this.cageData = cageData;
        // fill cage info array
        cageInfos = new CageInfo[cageData.getCageCount()];
        for (int i = 0; i < cageData.getCageCount(); i++) {
            cageInfos[i] = new CageInfo(cageData.getCages().get(i));
        }

    }

    //
    public void onDigitEntered(UserData.ChangeListener.SingleCellChange changeData) {
        int cageId = cageData.getValueAtCell(changeData.getCellChanged());
        System.out.println("Digit added. Cage is: " + cageId);
        CageInfo cageInfo = cageInfos[cageId];
        cageInfo.onDigitEntered();
        //todo cageInfos[cageId].setRecordValidity(false);

    }

    public void onDigitRemoved(UserData.ChangeListener.SingleCellChange changeData) {
        int cageId = cageData.getValueAtCell(changeData.getCellChanged());
        System.out.println("Digit removed. Cage is: " + cageId);
        CageInfo cageInfo = cageInfos[cageId];
        cageInfo.onDigitRemoved();
    }

    public CageInfo[] getCageInfos() {
        return cageInfos;
    }


//    public void reCalculate() {
//        //reminder: cage is only invalid when it full and has a mistake, not otherwise
//        /*when board is not fully filled in:
//              for every cage, if cage info is out of date, if the cage is filled in:
//                if its valid
//
//        */
//        for (int cageId = 0; cageId < cageData.getCageCount(); cageId++) {
//            CageInfo cageInfo = cageInfos[cageId];
//            if (cageInfo.isRecordOutOfDate()) {
//                if (cageInfo.cageIsfull()) {
//                    Cage cage = cageData.getCage(cageId);
//                    int[] cageMemberData = getMembersOfCage(cage);
//                    cageInfo.setCorrectness(RecursiveSolver.testSign(cageMemberData, cage.getTarget(), cage.getOperator()));
//                    cageInfo.setRecordValidity(true);
//                }
//            }
//        }
//    }



    public boolean noErrors() {
        for (CageInfo cageInfo: cageInfos) {
            if (cageInfo.isCageInvalid())
                return false;
        }

        return true;
    }

    public void reset() {
        for (CageInfo cageInfo: cageInfos) {
            cageInfo.reset();
        }
    }
}


/*class CageInfo_old {
    private final GridErrorChecker gridErrorChecker;
    private final Cage cage;
    private final int[] memberData;
    private boolean cageValid;
    private boolean validityOutOfDate;

    public CageInfo(GridErrorChecker gridErrorChecker, Cage cage) {
        this.gridErrorChecker = gridErrorChecker;
        this.cage = cage;
        memberData = new int[cage.getMemberCells().size()];
        cageValid = false;
        validityOutOfDate = false;
    }

    //public int onCell


    public void reCalculate() {
        for (int i = 0; i < memberData.length; i++) {
            memberData[i] = gridErrorChecker.userData.getValueAtCell(cage.getMemberCells().get(i));
        }

        cageValid = RecursiveSolver.testSign(memberData, cage.getTarget(), cage.getOperator());

        validityOutOfDate = false;
    }
}*/
