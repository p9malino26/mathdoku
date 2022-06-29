package com.patryk.mathdoku.cageData;

import com.patryk.mathdoku.util.MatcherHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CageParser {

    MatcherHelper targetAndSign = new MatcherHelper( "(\\d+)([+-x÷])?\\s");
    MatcherHelper cageMember = new MatcherHelper("(\\d+)(,|$)");
    //MatcherHelper cageMembers = new MatcherHelper("((\\d+),)*(\\d+)");
    MatcherHelper comma = new MatcherHelper(",");


    Cell[] data;
    List<Cage> cageList;


    public int getCellCount(Scanner scanner) {
        int cellCount = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            comma.initMatcher(line);
            int commaCount = 0;
            while (comma.matcher.find()) {
                commaCount++;
            }
            commaCount++; //because number of cells at line = "number of commas" + 1
            cellCount+= commaCount;
        }
        return cellCount;
    }

    /**
     *
     * @param scanner
     * @param data
     * @param cageList
     * @return number of cages
     */
    public int parseData(Scanner scanner, Cell[] data, List<Cage> cageList) throws DataFormatException {
        this.data = data;
        //this.cageList = cageList;
        int cageId = 0;
        while (scanner.hasNextLine()) {
            cageList.add(parseLine(scanner.nextLine(), cageId));
            cageId++;
        }
        return cageId; // cage count
    }

    private Cage parseLine(String cageLine, int cageID) {
        int line = cageID + 1;
        //make the matchers be of this particular cage
        targetAndSign.initMatcher(cageLine);
        cageMember.initMatcher(cageLine);
        //cageMembers.initMatcher(cageLine);

        //extract target and sign

        if (!targetAndSign.matcher.find() ) {
            throw new DataFormatException(line, "Expected digit followed by an optional arithmetic sign.");
        }

        int target = Integer.parseInt(targetAndSign.matcher.group(1));
        char sign = '+';
        if (targetAndSign.matcher.group(2) != null) {
            sign = targetAndSign.matcher.group(2).charAt(0);
        }

        //System.out.printf("Target is %d and sign is %c.\n", target, sign);

        //extract first occurence of cage member cell
        if (!cageMember.matcher.find(targetAndSign.matcher.end())) {
            throw new DataFormatException(line, "Expected multiple comma-separated digits.");
        }
        //cageMembers.matcher.find(targetAndSign.matcher.end());
        //System.out.println("Group count: " + cageMembers.matcher.groupCount());

        boolean isFirstCell = true;
        int markedCellId = 0;

        //for every cage member cell, instantiate that cell with this cage id
        ArrayList<Integer> cageMembers = new ArrayList<>();
        do {
            //
            int userCageId = Integer.parseInt(cageMember.matcher.group(1));
            int cageCellId = userCageId - 1;
            try {
                if (data[cageCellId].getCageId() != -1)
                    throw new DataFormatException(line, "The cage " + userCageId + " has already been listed!");
                data[cageCellId] = new Cell(cageID);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DataFormatException(line, "A cage identifier is too big for the grid of this size.");
            }
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
