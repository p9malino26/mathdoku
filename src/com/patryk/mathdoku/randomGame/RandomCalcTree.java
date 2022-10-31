package com.patryk.mathdoku.randomGame;

import com.patryk.mathdoku.cageData.Cage;

import java.util.List;
import java.util.Random;

class RandomCalcTree {
    List<Integer> values;
    Random ranObj;
    Cage.Operator operator;

    public RandomCalcTree(List<Integer> values, Random ranObj, Cage.Operator operator) {
        this.values = values;
        this.ranObj = ranObj;
        this.operator = operator;
        assert !values.contains(0);
//        if (operator == Cage.Operator.DIVIDE) {
//            while(values.remove(Integer.valueOf(0))) {}
//        }
    }

    public static int doit(List<Integer> values, Cage.Operator operator, Random ranObj) {
        return new RandomCalcTree(values, ranObj, operator).rec(0, values.size());
    }

    public int rec(int start, int end) {
        if (end - start <= 1) {
            assert values.get(start) != 0; //TODO dbg
            return values.get(start);
        }
        int split = ranObj.nextInt(start + 1, end);
        return operator.perform(rec(start, split), rec(split, end));
    }
}
