package com.patryk.mathdoku.solver;

import com.patryk.mathdoku.UserData;
import com.patryk.mathdoku.cageData.Cage;
import com.patryk.mathdoku.cageData.CageData;

import java.util.List;
import java.util.Set;

//todo check whole
public class Solver {
    UserData userData;
    List<Cage> cageList;

    public Solver(UserData userData, List<Cage> cageList) {
        this.userData = userData;
        this.cageList = cageList;
    }


    public static boolean solve(UserData userData, CageData cageData) {
        var obj = new Solver(userData, cageData.getCageList());
        return obj.rec(0);
    }

    private boolean rec(int cageIndex) {
        //if (cageIndex == 12) return true;
        Cage c = cageList.get(cageIndex);
        Set<List<Integer>> cageSolutions = CageSolver.doit(userData, c);
        if (cageSolutions.isEmpty()) {
            return false;
        }
        if (cageIndex == cageList.size() - 1) {
            applyCageSolution(userData, c.getMemberCells(), (List<Integer>) cageSolutions.toArray()[0]);
            return true;
        }

        for(List<Integer> solution: cageSolutions) {
            applyCageSolution(userData, c.getMemberCells(), solution);
            if (rec(cageIndex + 1)) return true;
        }

        clearCageSolution(userData, c.getMemberCells());
        return false;
    }

    public static void clearCageSolution(UserData userData, List<Integer> cells) {

        for(int i = 0; i < cells.size(); i++) {
            userData.setValueAtCell(cells.get(i), 0);
        }
    }

    public static void applyCageSolution(UserData userData, List<Integer> cells, List<Integer> cageSolution) {
        for(int i = 0; i < cells.size(); i++) {
            userData.setValueAtCell(cells.get(i), cageSolution.get(i));
        }
    }

}

/* rec(cageIndex) {
    cage = cages[cageIndex]
    solutions = get valid cage solutions
    if solutions empty:
        return false
    if on last cage:
        return first of solutions

    for solution in solutions:
        if rec(cageIndex + 1) return true

    return false

 */