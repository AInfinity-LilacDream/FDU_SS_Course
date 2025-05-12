package view;

import java.io.*;
import java.util.Objects;

import data.Constant;
import model.*;
import controller.*;

import javax.sound.midi.SysexMessage;

// render ui based on given details
public class Renderer {
    private Piece[][] currentBoard;
    public int currentGameID, gameTotal;
    private Player currentPlayer, alternatePlayer;
    private String gameType;
    private GameController[] gameList;
    private int currentRound;
    private int blackScore, whiteScore;
    private int row, col;
    private int blackBombCount, whiteBombCount;


    public void setGameList(GameController[] gameList) {
        this.gameList = gameList;

        GameController currentGame = gameList[currentGameID];
        this.currentBoard = currentGame.getBoard();
        this.currentPlayer = currentGame.getCurrentPlayer();
        this.alternatePlayer = currentGame.getAlternatePlayer();
        this.gameType = currentGame.getGameType();
        this.currentRound = currentGame.getCurrentRound();
        this.blackScore = currentGame.getBlackScore();
        this.whiteScore = currentGame.getWhiteScore();
        this.row = currentGame.getRow();
        this.col = currentGame.getCol();
        this.blackBombCount = currentGame.getBlackBombCount();
        this.whiteBombCount = currentGame.getWhiteBombCount();
    }

    public void setCurrentGameID(int id) {
        this.currentGameID = id;
    }

    public void setGameTotal(int total) {
        this.gameTotal = total;
    }

    public void renderFrame(Boolean invalidInputFlag) {
        if (!invalidInputFlag) {
            clearScreen();
            switch (gameType) {
                case Constant.Peace:
                    renderPeace(gameTotal);
                    break;
                case Constant.Reversi:
                    renderReversi(gameTotal);
                    break;
                case Constant.Gomoku:
                    renderGomoku(gameTotal);
                    break;
            }
        }
        renderTips();
    };

    private void renderTips() {
        if (Objects.equals(gameType, Constant.Reversi)) {
            System.out.printf("请玩家[%s]输入落子位置(1a) / 游戏编号(1-%d) / 新游戏类型(peace/reversi/gomoku) / 退出程序(quit):", currentPlayer.getName(), gameTotal);
        }
        else if (Objects.equals(gameType, Constant.Gomoku)) {
            System.out.printf("请玩家[%s]输入落子位置(1a) / 游戏编号(1-%d) / 新游戏类型(peace/reversi/gomoku) / 炸弹(bomb) / 放弃行棋(pass) / 退出程序(quit):", currentPlayer.getName(), gameTotal);
        }
        else {
            System.out.printf("请玩家[%s]输入落子位置(1a) / 游戏编号(1-%d) / 新游戏类型(peace/reversi/gomoku) / 放弃行棋(pass) / 退出程序(quit):", currentPlayer.getName(), gameTotal);
        }
    }

    private void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void renderPeace(int gameTotal) {
        System.out.print("  ");
        for (char i = 'A'; i <= 'A' + row - 1; ++i) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 1; i <= Math.max(row, 2 + gameTotal); ++i) {
            // board row
            if (i <= row) {
                System.out.print(i + " ");
                for (int j = 1; j <= col; ++j) {
                    switch (currentBoard[i][j]) {
                        case Piece.Black:
                            System.out.print("○ ");
                            break;
                        case Piece.White:
                            System.out.print("● ");
                            break;
                        case Piece.Empty:
                            System.out.print(". ");
                    }
                }
            }

            // 2nd column
            switch (i) {
                case 3:
                    System.out.printf("\tGame %d\t\t\t", currentGameID);
                    break;
                case 4:
                    System.out.printf("\tPlayer1 [%s]", Constant.name1P);
                    if (Objects.equals(currentPlayer.getName(), Constant.name1P)) {
                        System.out.print("   ○\t");
                    }
                    else {
                        System.out.print("\t\t");
                    }
                    break;
                case 5:
                    System.out.printf("\tPlayer2 [%s]", Constant.name2P);
                    if (Objects.equals(currentPlayer.getName(), Constant.name2P)) {
                        System.out.print(" ●\t");
                    }
                    else {
                        System.out.print("\t\t");
                    }
                    break;
                default:
                    System.out.print(i > row ? "\t\t\t\t\t\t" : "\t\t\t\t");
                    break;
            }

            // 3rd column
            if (i == 2) {
                System.out.println("Game List");
            }
            else if (i >= 3 && i <= 2 + gameTotal) {
                System.out.printf("%d. %s\n", i - 2, gameList[i - 2].getGameType());
            }
            else {
                System.out.println();
            }
        }
    }

    private void renderReversi(int gameTotal) {
        System.out.print("  ");
        for (char i = 'A'; i <= 'A' + row - 1; ++i) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 1; i <= Math.max(row, 2 + gameTotal); ++i) {
            // board row
            if (i <= row) {
                System.out.print(i + " ");
                for (int j = 1; j <= col; ++j) {
                    switch (currentBoard[i][j]) {
                        case Piece.Black:
                            System.out.print("○ ");
                            break;
                        case Piece.White:
                            System.out.print("● ");
                            break;
                        case Piece.Empty:
                            System.out.print(". ");
                            break;
                        case Piece.LEGAL:
                            System.out.print("+ ");
                    }
                }
            }

            // 2nd column
            switch (i) {
                case 3:
                    System.out.printf("\tGame %d\t\t\t", currentGameID);
                    break;
                case 4:
                    System.out.printf("\tPlayer1 [%s]", Constant.name1P);
                    if (Objects.equals(currentPlayer.getName(), Constant.name1P)) {
                        System.out.printf("   ○ %d\t", blackScore);
                    }
                    else {
                        System.out.printf("     %d\t", blackScore);
                    }
                    break;
                case 5:
                    System.out.printf("\tPlayer2 [%s]", Constant.name2P);
                    if (Objects.equals(currentPlayer.getName(), Constant.name2P)) {
                        System.out.printf(" ● %d\t", whiteScore);
                    }
                    else {
                        System.out.printf("   %d\t", whiteScore);
                    }
                    break;
                default:
                    System.out.print(i > row ? "\t\t\t\t\t\t" : "\t\t\t\t");
                    break;
            }

            // 3rd column
            if (i == 2) {
                System.out.println("Game List");
            }
            else if (i >= 3 && i <= 2 + gameTotal) {
                System.out.printf("%d. %s\n", i - 2, gameList[i - 2].getGameType());
            }
            else {
                System.out.println();
            }
        }
    }

    private void renderGomoku(int gameTotal) {
        System.out.print("  ");
        for (char i = 'A'; i <= 'A' + row - 1; ++i) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 1; i <= Math.max(row, 2 + gameTotal); ++i) {
            // board row
            if (i <= row) {
                System.out.print(i <= 9 ? i + " " : (char)('A' + i - 10) + " ");
                for (int j = 1; j <= col; ++j) {
                    switch (currentBoard[i][j]) {
                        case Piece.Black:
                            System.out.print("○ ");
                            break;
                        case Piece.White:
                            System.out.print("● ");
                            break;
                        case Piece.Empty:
                            System.out.print(". ");
                            break;
                        case Piece.BARRIER:
                            System.out.print("# ");
                            break;
                        case Piece.BOMB:
                            System.out.print("@ ");
                    }
                }
            }

            // 2nd column
            switch (i) {
                case 3:
                    System.out.printf("\tGame %d\t\t\t", currentGameID);
                    break;
                case 4:
                    System.out.printf("\tPlayer1 [%s]", Constant.name1P);
                    if (Objects.equals(currentPlayer.getName(), Constant.name1P)) {
                        System.out.print("   ○\t");
                    }
                    else {
                        System.out.print("\t\t");
                    }
                    break;
                case 5:
                    System.out.printf("\tPlayer2 [%s]", Constant.name2P);
                    if (Objects.equals(currentPlayer.getName(), Constant.name2P)) {
                        System.out.print(" ●\t");
                    }
                    else {
                        System.out.print("\t\t");
                    }
                    break;
                case 6:
                    System.out.printf("\tCurrent Round: %d\t", currentRound);
                    break;
                default:
                    System.out.print(i > row ? "\t\t\t\t\t\t\t\t" : "\t\t\t\t");
                    break;
            }

            // 3rd column
            switch (i) {
                case 3:
                    System.out.print("Bombs\t");
                    break;
                case 4:
                    System.out.printf("  %d\t", blackBombCount);
                    break;
                case 5:
                    System.out.printf("  %d\t", whiteBombCount);
                    break;
                default:
                    System.out.print("\t");
            }

            // 4th column
            if (i == 2) {
                System.out.println("Game List");
            }
            else if (i >= 3 && i <= 2 + gameTotal) {
                System.out.printf("%d. %s\n", i - 2, gameList[i - 2].getGameType());
            }
            else {
                System.out.println();
            }
        }
    }
}