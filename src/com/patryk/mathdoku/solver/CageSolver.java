package com.patryk.mathdoku.solver;

import com.patryk.mathdoku.GameContext;
import com.patryk.mathdoku.UserData;
import com.patryk.mathdoku.cageData.Cage;
import com.patryk.mathdoku.errorChecking.RecursiveChecker;
import com.patryk.mathdoku.util.BoardPosVec;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CageSolver {

    public static Set<List<Integer>> doit(UserData userData, Cage cage) {
        Set<List<Integer>> solutions = new HashSet<>();
        switch (cage.getOperator()) {
            case ADD -> rec_add(userData, cage.getMemberCells(), solutions, 0, cage.getTarget());
            case MULTIPLY -> rec_multiply(userData, solutions, cage.getMemberCells(), 0, cage.getTarget());
            default -> rec_generic(userData, solutions, cage, 0);
        }
        return solutions;
    }



    private static void rec_add(UserData userData, List<Integer> cells, Set<List<Integer>> solutions, int cageCellIndex, int remaining) {
        int boardCellIndex = cells.get(cageCellIndex);
        Set<Integer> allowedDigits = userData.getAllowedDigits(boardCellIndex);
        if (cageCellIndex == cells.size() - 1) {
            if (allowedDigits.contains(remaining)) {
                userData.setValueAtCell(boardCellIndex, remaining);
                solutions.add(cells.stream().map(userData::getValueAtCell).toList());
                userData.setValueAtCell(boardCellIndex, 0);
            }
            return;
        }

        int upperLimit = Math.min(remaining - (cells.size() - cageCellIndex - 1), BoardPosVec.getBoardWidth());
        for(int i = upperLimit; i > 0; i--) {
            if (allowedDigits.contains(i)) {
                userData.setValueAtCell(boardCellIndex, i);
                rec_add(userData, cells, solutions, cageCellIndex + 1, remaining - i);

            }
        }

        userData.setValueAtCell(boardCellIndex, 0);
    }

    private static void rec_multiply(UserData userData, Set<List<Integer>> solutions, List<Integer> cells, int cageCellIndex, int remaining) {
        int boardCellIndex = cells.get(cageCellIndex);
        Set<Integer> allowedDigits = userData.getAllowedDigits(boardCellIndex);
        if (cageCellIndex == cells.size() - 1) {
            if (allowedDigits.contains(remaining)) {
                userData.setValueAtCell(boardCellIndex, remaining);
                solutions.add(cells.stream().map(userData::getValueAtCell).toList());
                userData.setValueAtCell(boardCellIndex, 0);
            }
            return;
        }

        for (int n : allowedDigits) {
            if (remaining % n!= 0) continue;

            userData.setValueAtCell(boardCellIndex, n);
            rec_multiply(userData, solutions, cells, cageCellIndex + 1, Math.floorDiv(remaining, n));
        }
        userData.setValueAtCell(boardCellIndex, 0);
    }


    private static void rec_generic(UserData userData, Set<List<Integer>> solutions, Cage cage, int cageCellIndex) {
        final List<Integer> cells = cage.getMemberCells();
        int boardCellIndex = cells.get(cageCellIndex);
        Set<Integer> allowedDigits = userData.getAllowedDigits(boardCellIndex);

        for (int n : allowedDigits) {
            userData.setValueAtCell(boardCellIndex, n);

            if (cageCellIndex == cells.size() - 1) {
                var cageValues = cells.stream().map(userData::getValueAtCell).toList();
                if (RecursiveChecker.testSign(cageValues, cage.getTarget(), cage.getOperator())) {
                    solutions.add(cageValues);
                    return;
                }
                userData.setValueAtCell(boardCellIndex, 0);
                continue;
            }
            
            rec_generic(userData, solutions, cage, cageCellIndex + 1);
        }
        userData.setValueAtCell(boardCellIndex, 0);
    }
}
