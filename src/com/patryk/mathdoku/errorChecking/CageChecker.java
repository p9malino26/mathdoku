package com.patryk.mathdoku.errorChecking;

import com.patryk.mathdoku.cageData.CageData;
import com.patryk.mathdoku.UserData;

public class CageChecker {
    CageData cageData;
    CageInfo[] cageInfos;

    public CageChecker(UserData userData, CageData cageData) {
        this.cageData = cageData;
        // fill cage info array
        cageInfos = new CageInfo[cageData.getCageCount()];
        CageInfo.setUserData(userData);
        for (int i = 0; i < cageData.getCageCount(); i++) {
            cageInfos[i] = new CageInfo(cageData.getCages().get(i));
        }

    }

    public void onDigitEntered(UserData.ChangeListener.SingleCellChange changeData) {
        int cageId = cageData.getValueAtCell(changeData.getCellChanged());
        CageInfo cageInfo = cageInfos[cageId];
        cageInfo.onDigitEntered();

    }

    public void onDigitRemoved(UserData.ChangeListener.SingleCellChange changeData) {
        int cageId = cageData.getValueAtCell(changeData.getCellChanged());
        CageInfo cageInfo = cageInfos[cageId];
        cageInfo.onDigitRemoved();
    }

    public CageInfo[] getCageInfos() {
        return cageInfos;
    }

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

