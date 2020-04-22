//a singleton class
public class GameContext {
    private int canvasPixelWidth;
    private int boardWidth;

    private int squarePixelWidth;

    private static GameContext instance;


    private GameContext(int canvasPixelWidth, int boardWidth) {
        this.canvasPixelWidth = canvasPixelWidth;
        this.boardWidth = boardWidth;

        squarePixelWidth = canvasPixelWidth / boardWidth;
    }

    public static void init(int canvasPixelWidth, int boardWidth) {
        instance = new GameContext(canvasPixelWidth, boardWidth);
    }

    public static GameContext getInstance() throws NoContextException{
        if (instance == null) {
            throw new NoContextException();
        }
        return instance;
    }

    public int getCanvasPixelWidth() {
        return canvasPixelWidth;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getSquarePixelWidth() {
        return squarePixelWidth;
    }
}

class NoContextException extends RuntimeException{
    NoContextException() {
        super("You must first create a game context before any resource tries to obtain it.");
    }
}
