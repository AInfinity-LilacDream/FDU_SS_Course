import model.*;
import controller.*;
import data.*;
import view.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class game {
    private static Player Tom = new Player("Tom");
    private static Player Jerry = new Player("Jerry");

    private static GameController[] gameList = new GameController[Constant.MAX_GAME_COUNT];
    private static int gameTotal;

    private static GameController currentGame;
    private static int currentGameID;
    private static Boolean invalidInputFlag = false;

    private static final Scanner scannerSystemIn = new Scanner(System.in);
    private static Scanner scannerFile;
    private static Scanner scanner = scannerSystemIn;

    private static final Renderer renderer = new Renderer();
    private static final GameInputValidator validator = new GameInputValidator();

    private static int makeNextMove(String[] signal) {
        switch (signal[0]) {
            case Constant.Peace:
                gameList[++gameTotal] = new GameController(new PeaceBoard(), Tom, Jerry, Constant.Peace);
                return 1;
            case Constant.Reversi:
                gameList[++gameTotal] = new GameController(new ReversiBoard(), Tom, Jerry, Constant.Reversi);
                return 1;
            case Constant.Gomoku:
                gameList[++gameTotal] = new GameController(new GomokuBoard(), Tom, Jerry, Constant.Gomoku);
                return 1;
            case Constant.Abort:
                System.exit(0);
                return 1;
            case Constant.Invalid_Input:
                System.out.println("输入格式有误！");
                return 0;
            case Constant.Bomb:
                return currentGame.setBomb(signal[1]);
            case Constant.Pass:
                return currentGame.makeNextMove(signal[0]);
            case Constant.Switch:
                if (!(Integer.parseInt(signal[1]) >= 1 && Integer.parseInt(signal[1]) <= gameTotal)) {
                    System.out.println("输入格式有误！");
                    return 0;
                }
                currentGameID = Integer.parseInt(signal[1]);
                currentGame = gameList[currentGameID];
                return 1;
            case Constant.Playback:
                return 2;
            case Constant.Move:
                return currentGame.makeNextMove(signal[1]);
        }

        return 0;
    }

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        // create 3 boards
        gameList[++gameTotal] = new GameController(new PeaceBoard(), Tom, Jerry, Constant.Peace);
        gameList[++gameTotal] = new GameController(new ReversiBoard(), Tom, Jerry, Constant.Reversi);
        gameList[++gameTotal] = new GameController(new GomokuBoard(), Tom, Jerry, Constant.Gomoku);

        currentGame = gameList[1];
        currentGameID = 1;

        // main loop
        while (true) {
            renderer.setCurrentGameID(currentGameID);
            renderer.setGameList(gameList);
            renderer.setGameTotal(gameTotal);

            renderer.renderFrame(invalidInputFlag);
            invalidInputFlag = false;

            // 切换到标准输入流
            if (!scanner.hasNextLine()) {
                scanner = scannerSystemIn;
                scannerFile.close();
            }

            // delay
            if (scanner == scannerFile) {
                Thread.sleep(1000);
            }
            String[] signal = validator.validateNextLine(scanner);
            int ret = makeNextMove(signal);
            if (ret == 1) {
                invalidInputFlag = false;
            }
            else if (ret == 0) {
                invalidInputFlag = true;
            }
            else if (ret == 2) { // playback
                if (!FileProcessor.fileExists(Constant.videoFolder, signal[1])) {
                    System.out.println("文件不存在！");
                    invalidInputFlag = true;
                }
                else {
                    String filePath = Constant.videoFolder + signal[1];
                    File file = new File(filePath);
                    scannerFile = new Scanner(file);
                    scanner = scannerFile;

                    invalidInputFlag = false;
                }
            }

            // 成功行动之后，判断盘面形势
            if (!invalidInputFlag && !currentGame.getEndFlag()) {
                switch(currentGame.checkGameEnd()) {
                    case Constant.draw:
                        renderer.renderFrame(invalidInputFlag);
                        System.out.println("\n游戏结束！双方平局！");
                        invalidInputFlag = true;
                        currentGame.setEndFlag();
                        break;
                    case Constant.blackWin:
                        renderer.renderFrame(invalidInputFlag);
                        System.out.printf("\n游戏结束！[%s]获胜！\n", Tom.getName());
                        invalidInputFlag = true;
                        currentGame.setEndFlag();
                        break;
                    case Constant.whiteWin:
                        renderer.renderFrame(invalidInputFlag);
                        System.out.printf("\n游戏结束！[%s]获胜！\n", Jerry.getName());
                        invalidInputFlag = true;
                        currentGame.setEndFlag();
                        break;
                }
            }
        }
    }
}
