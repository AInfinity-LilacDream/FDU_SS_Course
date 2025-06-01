package com.oop.pj1.view;

import com.oop.pj1.controller.GameController;
import com.oop.pj1.data.Constant;
import com.oop.pj1.data.GlobalEntities;
import com.oop.pj1.model.Piece;
import com.oop.pj1.model.Player;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;

import static com.oop.pj1.data.GlobalEntities.root;
import static com.oop.pj1.data.GlobalEntities.scene;

// render ui based on given details
public class Renderer implements Serializable {
    private Piece[][] currentBoard;
    public int currentGameID, gameTotal;
    private Player currentPlayer, alternatePlayer;
    private String gameType;
    private GameController[] gameList;
    private int currentRound;
    private int blackScore, whiteScore;
    private int row, col;
    private int blackBombCount, whiteBombCount;
    private GameController currentGame;
    private Stage primaryStage;

    private UIConstructorPeace uiConstructorPeace = new UIConstructorPeace();
    private UIConstructorReversi uiConstructorReversi = new UIConstructorReversi();
    private UICostructorGomoku uiConstructorGomoku = new UICostructorGomoku();

    public void constructNextFrame(int currentGameID, GameController[] gameList, int gameTotal, Stage primaryStage) {
        setCurrentGameID(currentGameID);
        setGameList(gameList);
        setGameTotal(gameTotal);
        setPrimaryStage(primaryStage);

        renderFrame();
    }

    public void setGameList(GameController[] gameList) {
        this.gameList = gameList;

        this.currentGame = gameList[currentGameID];
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

    public void renderFrame() {
        clearScreen();
        switch (gameType) {
            case Constant.Peace:
                renderPeace(currentGame, this.primaryStage);
                break;
            case Constant.Reversi:
                renderReversi(currentGame, this.primaryStage);
                break;
            case Constant.Gomoku:
                renderGomoku(currentGame, this.primaryStage);
                break;
        }
    }

    private void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void renderPeace(GameController currentGame, Stage stage) {
        GridPane board = uiConstructorPeace.createBoard(currentGame);

        VBox gameInfo = uiConstructorPeace.createGameInfo(currentGame);
        VBox gameList = uiConstructorPeace.createGameList();
        VBox addNewGame = uiConstructorPeace.createAddNewGame();
        VBox dummy = new VBox();
        dummy.setPrefWidth(0);

        HBox gameView = new HBox(board, gameInfo, gameList, addNewGame, dummy);
        gameView.setSpacing(GlobalEntities.GAP);

        root.setCenter(gameView);

        stage.setTitle("Peace");
        stage.setScene(scene);
        stage.show();
    }

    private void renderReversi(GameController currentGame, Stage stage) {
        GridPane board = uiConstructorReversi.createBoard(currentGame);

        VBox gameInfo = uiConstructorReversi.createGameInfo(currentGame);
        VBox gameList = uiConstructorReversi.createGameList();
        VBox addNewGame = uiConstructorReversi.createAddNewGame();
        VBox dummy = new VBox();
        dummy.setPrefWidth(0);

        HBox gameView = new HBox(board, gameInfo, gameList, addNewGame, dummy);
        gameView.setSpacing(GlobalEntities.GAP);

        root.setCenter(gameView);

        stage.setTitle("Reversi");
        stage.setScene(scene);
        stage.show();
    }

    private void renderGomoku(GameController currentGame, Stage stage) {
        GridPane board = uiConstructorGomoku.createBoard(currentGame);

        VBox gameInfo = uiConstructorGomoku.createGameInfo(currentGame);
        VBox gameList = uiConstructorGomoku.createGameList();
        VBox addNewGame = uiConstructorGomoku.createAddNewGame();
        VBox dummy = new VBox();
        dummy.setPrefWidth(0);

        HBox gameView = new HBox(board, gameInfo, gameList, addNewGame, dummy);
        gameView.setSpacing(GlobalEntities.GAP);

        root.setCenter(gameView);

        stage.setTitle("Gomoku");
        stage.setScene(scene);
        stage.show();
    }

    public GameController[] getGameList() {
        return gameList;
    }

    public int getGameTotal() {
        return gameTotal;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }
}