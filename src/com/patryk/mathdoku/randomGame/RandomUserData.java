package com.patryk.mathdoku.randomGame;

import com.patryk.mathdoku.UserData;
import com.patryk.mathdoku.util.BoardPosVec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class RandomUserData {
    UserData userData;
    int size;
    Random ranObj;

    public RandomUserData(int size, Random ranObj) {
        this.size = size;
        this.userData = new UserData(size);
        this.ranObj = ranObj;
    }

    public static UserData doit(int size, Random ranObj) {
        var obj = new RandomUserData(size, ranObj);
        obj.rec(new BoardPosVec(0,0));
        return obj.userData;
    }

    private boolean rec(BoardPosVec pos) {
        int r = pos.r;
        int c = pos.c;
        //todo simplify!
        List<Integer> allDigits = new ArrayList<>();
        for(int i = 1; i <= size; i++) {
            allDigits.add(i);
        }

        Collections.shuffle(allDigits, ranObj);

        //remove row digits
        for (int vcol = 0; vcol < c; vcol++) {
            allDigits.remove(Integer.valueOf(userData.getValueAtCell(new BoardPosVec(r, vcol))));
        }
        //remove column digits
        for (int vrow = 0; vrow < r; vrow++) {
            allDigits.remove(Integer.valueOf(userData.getValueAtCell(new BoardPosVec(vrow, c))));
        }


        for (int digit: allDigits) {
            userData.setValueAtCell(pos, digit);
            if (r == size - 1 && c == size - 1 || rec(pos.next())) return true;
        }

        return false;
    }
}
