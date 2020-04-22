package com.patryk.mathdoku.errorChecking;

import com.patryk.mathdoku.Cage;

public interface ErrorShower {
    void onCageInvalid(Cage cage);
    void onRowColInvalid(boolean isRow, int index);
}
