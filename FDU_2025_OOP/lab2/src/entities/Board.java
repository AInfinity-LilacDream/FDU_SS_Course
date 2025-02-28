package entities;

public class Board {
    public Piece[][] board = new Piece[9][9];

    public void init() {
        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                board[i][j] = Piece.EMPTY;
            }
        }
    }
}
