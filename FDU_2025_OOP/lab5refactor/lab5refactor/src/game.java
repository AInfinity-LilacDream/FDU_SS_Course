import model.*;
import controller.*;
import data.*;
import view.*;

import java.util.Scanner;

public class game {
    private static Player Tom = new Player("Tom");
    private static Player Jerry = new Player("Jerry");

    private static GameController[] gameList = new GameController[Constant.MAX_GAME_COUNT];
    private static int gameTotal;

    private static GameController currentGame;
    private static int currentGameID;
    private static Boolean invalidInputFlag = false;

    private static final Scanner scanner = new Scanner(System.in);

    private static final Renderer renderer = new Renderer();
    private static final GameInputValidator validator = new GameInputValidator(scanner);

    private static Boolean makeNextMove(String signal) {
        switch (signal) {
            case Constant.Peace:
                gameList[++gameTotal] = new GameController(new PeaceBoard(), Tom, Jerry, Constant.Peace);
                return true;
            case Constant.Reversi:
                gameList[++gameTotal] = new GameController(new ReversiBoard(), Tom, Jerry, Constant.Reversi);
                return true;
            case Constant.Gomoku:
                gameList[++gameTotal] = new GameController(new GomokuBoard(), Tom, Jerry, Constant.Gomoku);
                return true;
            case Constant.Abort:
                System.exit(0);
                return true;
            case Constant.Invalid_Input:
                System.out.println("输入格式有误！");
                return false;
            default:
                if (GameInputValidator.isDigit(signal)) {
                    if (!(Integer.parseInt(signal) >= 1 && Integer.parseInt(signal) <= gameTotal)) {
                        System.out.println("输入格式有误！");
                        return false;
                    }
                    currentGameID = Integer.parseInt(signal);
                    currentGame = gameList[currentGameID];
                    return true;
                }
                else {
                    return currentGame.makeNextMove(signal);
                }
        }
    }

    public static void main(String[] args) {
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

            String signal = validator.validateNextLine();
            invalidInputFlag = !makeNextMove(signal);

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
