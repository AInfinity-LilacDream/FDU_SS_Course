package entities;

public class Board {
    public Piece[][] board = new Piece[9][9];
    public Player currentPlayer;

    public void init(Player player) {
        currentPlayer = player;

        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                board[i][j] = Piece.EMPTY;
            }
        }
        board[4][5] = Piece.BLACK;
        board[5][4] = Piece.BLACK;
        board[4][4] = Piece.WHITE;
        board[5][5] = Piece.WHITE;
    }
}
