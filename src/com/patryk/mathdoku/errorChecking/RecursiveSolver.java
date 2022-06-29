package com.patryk.mathdoku.errorChecking;

import com.patryk.mathdoku.cageData.Cage;

public class RecursiveSolver {



    boolean permute;
    Cage.Operator operator;
    int target;

    private boolean f(int result,int[] cageList, int startIndex, int depth) {
        //System.out.println("Analyzing " + result);
        if (result == target)
            return true;
        for (int i = startIndex; i < cageList.length; i++) {
            if (cageList[i] != -1) {
                int e = cageList[i];
                int newResult = depth == 0 ? e : operator.perform(result, e);
                //int newResult = e;
                if (operator == Cage.Operator.DIVIDE && result % e != 0) {
                    //System.out.println(e + " Not divisible so ignoring");
                    continue;
                }
                cageList[i] = -1;
                boolean outcome = f(newResult, cageList, permute ? 0 : i + 1, depth + 1);
                cageList[i] = e;
                if (outcome)
                    return true;
                
            }
        }
        //System.out.println("Done analyzing " + result);
        return false;
    }

    private RecursiveSolver(int target, Cage.Operator operator) {
        this.target = target;
        this.operator = operator;
        this.permute = (operator == Cage.Operator.SUBTRACT || operator == Cage.Operator.DIVIDE);
    }

    public static boolean testSign(int[] cageList, int target, Cage.Operator operator) {
        return new RecursiveSolver(target, operator).f(0, cageList, 0, 0);
    }

}