package controller;

import data.Constant;

import java.util.Scanner;

public class GameInputValidator {
    private final Scanner scanner;

    public GameInputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    // 检验输入的合法性
    public String validateNextLine() {
        String input = scanner.nextLine();

        if (input.equals("")) {
            return Constant.Invalid_Input;
        }
        else if (input.equals("peace")) {
            return Constant.Peace;
        }
        else if (input.equals("reversi")) {
            return Constant.Reversi;
        }
        else if (input.equals("gomoku")) {
            return Constant.Gomoku;
        }
        else if (input.equals("quit")) {
            return Constant.Abort;
        }
        else if (input.equals("pass")) {
            return Constant.Pass;
        }
        else if (isDigit(input)) {
            return input;
        }
        else if (input.length() == 2 && input.charAt(0) >= '1' &&
        input.charAt(0) <= '8' && input.charAt(1) >= 'a' && input.charAt(1) <= 'h') {
            return input;
        }
        else {
            return Constant.Invalid_Input;
        }
    }

    public static Boolean isDigit(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
