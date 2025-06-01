package com.oop.pj1.model;

import java.io.Serializable;

public abstract class Board implements Serializable {
    protected Piece[][] board;
    private int row, col;

    public abstract void initBoard();

    public Board() {
        board = new Piece[19][19];
        initBoard();
    }

    // 检查游戏获胜状况
    // -1 - 游戏未结束
    // 0 - 平局
    // 1 - Tom
    // 2 - Jerry
    public abstract int checkGameEnd();

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Piece[][] getBoard() {
        return board;
    }
}