package com.oop.pj1;

import com.oop.pj1.controller.GameController;
import com.oop.pj1.data.Constant;
import com.oop.pj1.data.GlobalEntities;
import com.oop.pj1.data.Serializer;
import com.oop.pj1.model.*;
import com.oop.pj1.view.Messenger;
import com.oop.pj1.view.Renderer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import java.io.*;
import java.util.Scanner;

public class Game extends Application {
    private static final Player Tom = new Player("Tom");
    private static final Player Jerry = new Player("Jerry");

    private static GameController[] gameList = new GameController[Constant.MAX_GAME_COUNT];
    private static int gameTotal;

    private static GameController currentGame;
    private static int currentGameID;
    private static final GameInputValidator validator = new GameInputValidator();

    private static final Renderer renderer = GlobalEntities.renderer;

    public static Stage stage;

    private static Timeline currentTimeline = null;  // 添加静态变量保存当前Timeline

    @Override
    public void start(Stage primaryStage) throws IOException {

        boolean restored = Serializer.restoreState();

        stage = primaryStage;
        primaryStage.widthProperty().addListener((_, _, newVal) -> {
            GlobalEntities.width = newVal.intValue();
            GlobalEntities.updateGap();
            renderer.constructNextFrame(currentGameID, Game.getGameList(), Game.getGameTotal(), Game.getStage());
        });

        // 监听窗口高度变化
        primaryStage.heightProperty().addListener((_, _, newVal) -> {
            GlobalEntities.height = newVal.intValue();
            GlobalEntities.updateSize();
            GlobalEntities.updateGap();
            renderer.constructNextFrame(currentGameID, Game.getGameList(), Game.getGameTotal(), Game.getStage());
        });

        // initialize vars
        // create 3 boards
        if (!restored) {
            gameList[++gameTotal] = new GameController(new PeaceBoard(), Tom, Jerry, Constant.Peace);
            gameList[++gameTotal] = new GameController(new ReversiBoard(), Tom, Jerry, Constant.Reversi);
            gameList[++gameTotal] = new GameController(new GomokuBoard(), Tom, Jerry, Constant.Gomoku);

            currentGame = gameList[1];
            currentGameID = 1;
        }

        renderer.constructNextFrame(currentGameID, gameList, gameTotal, primaryStage);
    }

    public static void refreshGame() {
        renderer.constructNextFrame(currentGameID, gameList, gameTotal, stage);
    }

    public static void addGame(String mode) {
        switch (mode) {
            case Constant.Peace:
                gameList[++gameTotal] = new GameController(new PeaceBoard(), Tom, Jerry, Constant.Peace);
                refreshGame();
                break;
            case Constant.Reversi:
                gameList[++gameTotal] = new GameController(new ReversiBoard(), Tom, Jerry, Constant.Reversi);
                refreshGame();
                break;
            case Constant.Gomoku:
                gameList[++gameTotal] = new GameController(new GomokuBoard(), Tom, Jerry, Constant.Gomoku);
                refreshGame();
                break;
        }
    }

    public static void switchGame(int gameID) {
        currentGameID = gameID;
        currentGame = gameList[currentGameID];
        refreshGame();
    }

    public static GameController[] getGameList() {
        return gameList;
    }

    public static int getGameTotal() {
        return gameTotal;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setGameTotal(int restoredGameTotal) {
        gameTotal = restoredGameTotal;
    }

    public static void setCurrentGame(GameController currentGame) {
        Game.currentGame = currentGame;
    }

    public static void setGameList(GameController[] savedGameList) {
        gameList = savedGameList;
    }

    public static void setCurrentGameID(int savedCurrentGameID) {
        currentGameID = savedCurrentGameID;
    }

    public static int getCurrentGameID() {
        return currentGameID;
    }

    public static GameController getCurrentGame() {
        return currentGame;
    }

    private static void makeNextMove(String[] signal) {
        switch (signal[0]) {
            case Constant.Peace:
                addGame(Constant.Peace);
                break;
            case Constant.Reversi:
                addGame(Constant.Reversi);
                break;
            case Constant.Gomoku:
                addGame(Constant.Gomoku);
                break;
            case Constant.Abort:
                System.exit(0);
                break;
            case Constant.Invalid_Input:
                Messenger.error("输入格式有误！");
                break;
            case Constant.Bomb:
                currentGame.setBomb(signal[1]);
                break;
            case Constant.Pass:
                currentGame.makeNextMove(signal[0]);
                break;
            case Constant.Switch:
                if (!(Integer.parseInt(signal[1]) >= 1 && Integer.parseInt(signal[1]) <= gameTotal)) {
                    Messenger.error("输入格式有误！");
                    return;
                }
                currentGameID = Integer.parseInt(signal[1]);
                currentGame = gameList[currentGameID];
                return;
            case Constant.Playback:
            case Constant.Move:
                currentGame.makeNextMove(signal[1]);
        }
    }

    public static void playDemo(String demoPath) throws FileNotFoundException {
        // 如果有正在运行的演示，先停止它
        if (currentTimeline != null) {
            currentTimeline.stop();
            currentTimeline.getKeyFrames().clear();
        }

        File file = new File(demoPath);
        Scanner scanner = new Scanner(file);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), _ -> {
            if (!scanner.hasNextLine()) {
                scanner.close();
                currentTimeline.stop();
                currentTimeline.getKeyFrames().clear();
                currentTimeline = null;
                Messenger.info("演示完成");
                return;
            }

            String[] signal = validator.validateNextLine(scanner);
            makeNextMove(signal);
            refreshGame();
        }));
        
        timeline.setCycleCount(Timeline.INDEFINITE);
        currentTimeline = timeline;  // 保存当前Timeline的引用
        timeline.play();
    }

    public static void main() {
        launch();
    }
}
