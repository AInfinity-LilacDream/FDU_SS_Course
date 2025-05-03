package model;

import data.Constant;

public class PeaceBoard extends Board {
    @Override
    public void initBoard() {
        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                board[i][j] = Piece.Empty;
            }
        }
    }

    @Override
    public int checkGameEnd() {
        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                if (board[i][j] == Piece.Empty) {
                    return Constant.running;
                }
            }
        }
        return Constant.draw;
    }
}
