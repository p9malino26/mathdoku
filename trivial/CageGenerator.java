import java.util.BitSet;

public class CageGenerator {
    public interface BoardReadyFunc {
        void onBoardReady(UserData board);
    }

    private BitSet[] colForbidden;
    private BitSet[] rowForbidden;
    private int size;
    private UserData data;
    private BoardReadyFunc boardReadyFunc;

    public void perform(UserData data, BoardReadyFunc boardReadyFunc) {
        this.size = data.size;
        this.data = data;
        this.boardReadyFunc = boardReadyFunc;
        colForbidden = new BitSet[size];
        rowForbidden = new BitSet[size];
        f(Util.BoardPosVec.zero());
    }

    private void f(Util.BoardPosVec cell) {
        //get bit vector of forbidden values for that row and column
        BitSet forbiddenNums = colForbidden[cell.c].or(rowForbidden[cell.r]);
        for (int i = 1; i <= size; i++) {
            //if that value is not forbidden
            if (!forbiddenNums.get(i)) {
                //give current cell that number
                data.setValueAtCell(cell, i);
                if (cell.isLast()) {
                    boardReadyFunc.onBoardReady(data);
                    return;
                }
                //make this number forbidden in rows and columns
                colForbidden[cell.c].set(i, true);
                rowForbidden[cell.r].set(i, true);
                //go to next cell and recursively do the same
                f(cell.next());
                //after that is done, make this number no longer forbidden
                colForbidden[cell.c].set(i, false);
                rowForbidden[cell.r].set(i, false);
                assert(forbiddenNums.equals(colForbidden[cell.c].or(rowForbidden[cell.r])));
                //but don't remove the number; it will be ignored and later overwritten anyway
            }
        }
    }
}