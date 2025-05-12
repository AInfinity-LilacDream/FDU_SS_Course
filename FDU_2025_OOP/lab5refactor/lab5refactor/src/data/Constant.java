package data;

// store public constants
public class Constant {
    public static final int MAX_GAME_COUNT = 9999;

    public static final String Peace = "peace";
    public static final String Reversi = "reversi";
    public static final String Gomoku = "gomoku";
    public static final String Abort = "quit";
    public static final String Pass = "pass";

    public static final String[] basicCommands = new String[]{Peace, Reversi, Gomoku, Abort, Pass};

    public static final String Invalid_Input = "Invalid Input";
    public static final String Playback = "playback";
    public static final String Bomb = "bomb";
    public static final String Switch = "switch";
    public static final String Move = "move";

    public static final String videoFolder = "../assets/";

    public static final String name1P = "Tom";
    public static final String name2P = "Jerry";

    public static final int running = -1;
    public static final int draw = 0;
    public static final int blackWin = 1;
    public static final int whiteWin = 2;
}
