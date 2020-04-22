package com.patryk.mathdoku;

import java.util.ArrayList;

public class CageParser {
    Cell[] data;

    MatcherHelper targetAndSign = new MatcherHelper( "(\\d+)(.) ");
    MatcherHelper cageMember = new MatcherHelper("(\\d+)");

    public CageParser(Cell[] data) {
        this.data = data;
    }

    public Cage parse(String cageLine, int cageID) {

        //make the matchers be of this particular cage
        targetAndSign.initMatcher(cageLine);
        cageMember.initMatcher(cageLine);

        //extract target and sign
        targetAndSign.matcher.find();
        int target = Integer.parseInt(targetAndSign.matcher.group(1));
        char sign = targetAndSign.matcher.group(2).charAt(0);

        //System.out.printf("Target is %d and sign is %c.\n", target, sign);

        //extract first occurence of cage member cell
        cageMember.matcher.find(targetAndSign.matcher.end());

        boolean isFirstCell = true;
        int markedCellId = 0;

        //for every cage member cell, instantiate that cell with this cage id
        ArrayList<Integer> cageMembers = new ArrayList<>();
        do {
            //
            int cageCellId = Integer.parseInt(cageMember.matcher.group(0)) - 1;
            data[cageCellId] = new Cell(cageID);
            cageMembers.add(cageCellId);
            //additionally, if it is the first cell, make that cage have this cell
            if (isFirstCell) {
                markedCellId = cageCellId;
                isFirstCell = false;
            }

        } while (cageMember.matcher.find());

        //finally, instantiate the cage and return it
        return new Cage(target, Cage.Operator.fromChar(sign), markedCellId, cageMembers);

    }

}
