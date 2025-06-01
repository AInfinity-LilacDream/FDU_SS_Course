package com.oop.pj1.model;

import com.oop.pj1.data.Constant;

import java.io.Serializable;

public class PeaceBoard extends Board implements Serializable {

    @Override
    public void initBoard() {
        setRow(8);
        setCol(8);
        for (int i = 1; i <= getRow(); ++i) {
            for (int j = 1; j <= getCol(); ++j) {
                board[i][j] = Piece.Empty;
            }
        }
        board[4][4] = board[5][5] = Piece.White;
        board[4][5] = board[5][4] = Piece.Black;
    }

    @Override
    public int checkGameEnd() {
        for (int i = 1; i <= getRow(); ++i) {
            for (int j = 1; j <= getCol(); ++j) {
                if (board[i][j] == Piece.Empty) {
                    return Constant.running;
                }
            }
        }
        return Constant.draw;
    }
}
