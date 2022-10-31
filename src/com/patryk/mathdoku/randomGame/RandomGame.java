package com.patryk.mathdoku.randomGame;

import com.patryk.mathdoku.UserData;
import com.patryk.mathdoku.cageData.Cage;
import com.patryk.mathdoku.cageData.CageData;
import com.patryk.mathdoku.cageData.Cell;
import com.patryk.mathdoku.util.BoardPosVec;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

public class RandomGame {
    static final int MAX_CAGE_SIZE = 4;
    public static UserData userData; //todo dbg
    public static CageData randomGame(int seed, int size) {
        /**
         * random array
         * partition into cages
         * determine operators/operands
         */
        BoardPosVec.setBoardWidth(size);
        Random ranObj = new Random(seed);
        UserData ud = randomUserData(ranObj, size);
        CageData cd = randomCageData(ranObj, size, ud);
        userData = ud;
        return cd;
    }

    public static UserData randomUserData(Random ranObj, int size) {
        return RandomUserData.doit(size, ranObj);
    }

    public static CageData randomCageData (Random ranObj, int width, UserData ud) {
        //create blank cagedata
        CageData cd = new CageData(width);
        //determine cage boundaries
        List<Cage> cl = RandomPartitionGrid.doit(cd.getData(), MAX_CAGE_SIZE, ranObj);
        cd.setCageList(cl);


        doOperatorsTargets(cd, ud, ranObj);
        return cd;
    }

    public static void doOperatorsTargets(CageData cd, UserData ud, Random ranObj) {

        //determine operators and operands
        for(Cage cage: cd.getCages()) {
            Cage.Operator op = Cage.Operator.values()[ranObj.nextInt(4)];
            //if (op == Cage.Operator.DIVIDE) op = Cage.Operator.ADD;
            Function<Integer, Integer>  f = (Integer cageMember )-> ud.getValueAtCell(Integer.valueOf(cageMember));
            List<Integer> cageValues = new ArrayList(cage.getMemberCells().stream().map(f).toList());
            int target = RandomCalcTree.doit(cageValues, op, ranObj);
            cage.setOperator(op);
            cage.setTarget(target);
        }
    }

}

