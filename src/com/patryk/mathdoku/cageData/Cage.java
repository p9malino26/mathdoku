package com.patryk.mathdoku.cageData;

import java.util.ArrayList;
import java.util.List;

public class Cage {
    int target;
    Cage.Operator operator = Operator.ADD;
    int markedCell = 0;
    private List<Integer> memberCells = new ArrayList<>();

    public Cage() {}

    public Cage(int target, Cage.Operator operator, int markedCell, List<Integer> memberCells) {
        this.target = target;
        this.operator = operator;
        this.markedCell = markedCell;
        this.memberCells = memberCells;
    }

    public int getTarget() {return target; }

    public Cage.Operator getOperator() {
        return operator;
    }

    public int getMarkedCell() { return markedCell; }

    public List<Integer> getMemberCells() {
        return memberCells;
    }

    public int getCellCount() {
        return memberCells.size();
    }

    @Override
    public String toString() {
        String s = Integer.toString(target);
        if (getCellCount() > 1)
            s+=this.operator.sign;
        return s;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public void setMemberCells(List<Integer> memberCells) {
        this.memberCells = memberCells;
    }

    public enum Operator {
        ADD('+'), SUBTRACT('-'), MULTIPLY('x'), DIVIDE('÷');

        public final char sign;

        Operator(char sign) {
            this.sign = sign;
        }

        public static Cage.Operator fromChar(char c) {
            switch (c) {
                case '+':
                    return ADD;
                case '-':
                    return SUBTRACT;
                case 'x':
                    return MULTIPLY;
                case '÷':
                    return DIVIDE;
                default:
                    throw new RuntimeException("Char invalid!!!");
            }
        }

        public int perform(int operand1, int operand2) {
            switch (this) {
                case ADD:
                    return operand1 + operand2;
                case SUBTRACT:
                    return operand1 - operand2;
                case MULTIPLY:
                    return operand1 * operand2;
                case DIVIDE:
                    return Math.max(1, Math.floorDiv(operand1, operand2));
                default:
                    return -1;
            }
        }
    }
}
