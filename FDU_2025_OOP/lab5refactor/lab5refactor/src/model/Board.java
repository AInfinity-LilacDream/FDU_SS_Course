package model;

public abstract class Board {
    protected Piece[][] board;

    public abstract void initBoard();

    public Board() {
        board = new Piece[9][9];
        initBoard();
    }

    // 检查游戏获胜状况
    // -1 - 游戏未结束
    // 0 - 平局
    // 1 - Tom
    // 2 - Jerry
    public abstract int checkGameEnd();

    public Piece[][] getBoard() {
        return board;
    }
}