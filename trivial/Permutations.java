public class Permutations {
    public enum Sign {PLUS, MINUS, MULTIPLY, DIVIDE};

    //public boolean fits(int target, int[] nums) {

    /*checks if target can be formed by applying op1 with sign sign to any integer in the list or by also
       additionally recursively applying each result to the remainders in the list.
     */

    private int operation(int operand1, int operand2, Sign operator) {
        switch (operator) {
            case PLUS:
                return operand1 + operand2;
            case MINUS:
                return operand1 - operand2;
            case MULTIPLY:
                return operand1 * operand2;
            case DIVIDE:
                return Math.floorDiv(operand1, operand2);
            default:
                return -1;

        }
    }

    boolean permute;
    Sign operator;
    int target = 240;

    private boolean f(int result,int[] cageList, int startIndex, int depth) {
        //System.out.println("Analyzing " + result);
        if (result == target)
            return true;
        for (int i = startIndex; i < cageList.length; i++) {
            if (cageList[i] != -1) {
                int e = cageList[i];
                int newResult = depth == 0 ? e : operation(result, e, operator);
                //int newResult = e;
                if (operator == Sign.DIVIDE && result % e != 0) {
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

    //!!!not thread safe!!!
    public boolean testSign(int[] cageList, int target, Sign operator) {
        this.target = target;
        this.operator = operator;
        this.permute = (operator == Sign.MINUS || operator == Sign.DIVIDE);

        return f(0, cageList, 0, 0);
    }

    public static void main(String[] args) {
        System.out.println( new Permutations().testSign(new int[]{9, 1, 2, 3}, 8, Sign.MINUS)); 
    }
}