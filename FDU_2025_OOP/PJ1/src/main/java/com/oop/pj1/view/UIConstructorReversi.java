package com.oop.pj1.view;

import com.oop.pj1.Game;
import com.oop.pj1.controller.GameController;
import com.oop.pj1.data.Constant;
import com.oop.pj1.data.GlobalEntities;
import com.oop.pj1.data.Serializer;
import com.oop.pj1.model.Piece;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class UIConstructorReversi extends AbstractUIConstructor {

    @Override
    public GridPane createBoard(GameController game) {
        int BOARD_SIZE = game.getRow();
        double TILE_SIZE = GlobalEntities.BOARD_SIZE / BOARD_SIZE;

        GridPane board = new GridPane();
        board.setHgap(0);
        board.setVgap(0);

        for (int col = 0; col < BOARD_SIZE; col++) {
            Label label = new Label(String.valueOf((char) ('A' + col)));
            label.setPrefSize(TILE_SIZE, TILE_SIZE);
            label.setStyle(String.format("-fx-font-size: %.0fpx; -fx-alignment: center;", TILE_SIZE / 2));
            board.add(label, col + 1, 0);
        }

        for (int i = 0; i < BOARD_SIZE; ++i) {
            Label label = new Label(String.valueOf(i + 1));
            label.setPrefSize(TILE_SIZE, TILE_SIZE);
            label.setStyle(String.format("-fx-font-size: %.0fpx; -fx-alignment: center;", TILE_SIZE / 2));
            board.add(label, 0, i + 1);

            for (int j = 0; j < BOARD_SIZE; ++j) {
                Circle circle = new Circle(TILE_SIZE / 4);
                Text text = new Text("+");
                text.setStyle("-fx-font-size: " + (TILE_SIZE / 2) + ";");
                text.setFill(Color.TRANSPARENT);
                switch(game.getBoard()[i + 1][j + 1]) {
                    case Piece.Black:
                        circle.setFill(Color.BLACK);
                        break;
                    case Piece.White:
                        circle.setFill(Color.WHITE);
                        break;
                    case Piece.LEGAL:
                        circle.setFill(Color.TRANSPARENT);
                        text.setFill(Color.GREEN);
                        break;
                    default:
                        circle.setFill(Color.TRANSPARENT);
                        break;
                }

                StackPane tile = new StackPane(circle, text);
                String borderStyle = String.format(
                    "-fx-background-color: lightblue; " +
                    "-fx-border-color: black; " +
                    "-fx-border-width: %d %d %d %d;",  // 上右下左
                    i == 0 ? 1 : 0,      // 第一行需要上边框
                    1,                    // 所有格子都需要右边框
                    1,                    // 所有格子都需要下边框
                    j == 0 ? 1 : 0       // 第一列需要左边框
                );
                tile.setStyle(borderStyle);
                tile.setPrefSize(TILE_SIZE, TILE_SIZE);
                int finalI = i;
                int finalJ = j;
                tile.setOnMouseClicked(_ -> {
                    game.makeNextMove(finalI + 1 + String.valueOf((char)(finalJ + 'a')));
                    changeLabelBorder(game);
                    Game.refreshGame();
                });

                board.add(tile, j + 1, i + 1);
            }
        }

        return board;
    }

    @Override
    public VBox createGameInfo(GameController game) {
        VBox vbox = new VBox(10);
        vbox.setPrefWidth(GlobalEntities.GAME_INFO_WIDTH);

        Label title = new Label(String.format("游戏%d", Game.getCurrentGameID()));
        title.setStyle(String.format("-fx-font-size: %.0fpx;", GlobalEntities.height / 35));

        blackPlayer = new Label(String.format("黑棋 [Tom]: %d", game.getBlackScore()));
        whitePlayer = new Label(String.format("白棋 [Jerry]: %d", game.getWhiteScore()));
        blackPlayer.setStyle(String.format("-fx-font-size: %.0fpx;", GlobalEntities.height / 40));
        whitePlayer.setStyle(String.format("-fx-font-size: %.0fpx;", GlobalEntities.height / 40));

        Button skipBtn = new Button("跳过本轮");
        Button exitGame = new Button("退出游戏");
        String buttonStyle = String.format("""
            -fx-font-size: %.0fpx;
            -fx-min-height: %.0fpx;
            -fx-max-height: %.0fpx;
            """, 
            GlobalEntities.height / 60,  // 改为更小的字体
            GlobalEntities.height / 25,
            GlobalEntities.height / 25
        );
        skipBtn.setStyle(buttonStyle);
        exitGame.setStyle(buttonStyle);
        demoMode.setStyle(buttonStyle);

        skipBtn.setOnAction(_ -> {
            game.makeNextMove(Constant.Pass);
            changeLabelBorder(game);
            Game.refreshGame();
        });
        exitGame.setOnAction(_ -> {
            Serializer.saveState(Game.getGameList(), Game.getCurrentGameID(), Game.getGameTotal(), Game.getCurrentGame());
            System.exit(0);
        });

        vbox.getChildren().addAll(title, blackPlayer, whitePlayer, skipBtn, demoMode, exitGame);
        changeLabelBorder(game);

        return vbox;
    }
}
