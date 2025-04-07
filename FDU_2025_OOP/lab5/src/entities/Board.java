package entities;

public class Board {
    public Piece[][] board = new Piece[9][9];
    public Player currentPlayer;
    public int id;
    public String mode;
    public int round; // the current round total of a game
    public boolean endFlag;

    public void initBoard(Player player, int id, String mode) {
        this.endFlag = false;
        switch (mode) {
            case "peace": initPeaceBoard(player, id, mode); break;
            case "reversi": initReversiBoard(player, id, mode); break;
            case "gomoku": initGomokuBoard(player, id, mode); break;
        }
    }

    public void initReversiBoard(Player player, int id, String mode) {
        initPeaceBoard(player, id, mode);
        board[4][5] = Piece.BLACK;
        board[5][4] = Piece.BLACK;
        board[4][4] = Piece.WHITE;
        board[5][5] = Piece.WHITE;
    }

    public void initPeaceBoard(Player player, int id, String mode) {
        currentPlayer = player;
        this.id = id;
        this.mode = mode;

        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                board[i][j] = Piece.EMPTY;
            }
        }
    }

    public void initGomokuBoard(Player player, int id, String mode) {
        currentPlayer = player;
        this.id = id;
        this.mode = mode;
        this.round = 1;

        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                board[i][j] = Piece.EMPTY;
            }
        }
    }
}
