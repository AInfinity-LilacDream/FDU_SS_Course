package model;

import data.Constant;

import java.util.Scanner;

public class GameInputValidator {
    // 检验输入的合法性
    public String[] validateNextLine(Scanner scanner) {
        String input = scanner.nextLine();
        input = input.toLowerCase(); // 把大写字母全部转成小写

        if (input.isEmpty()) {
            return new String[]{Constant.Invalid_Input};
        }

        for (String command : Constant.basicCommands) {
            if (command.equals(input)) {
                return new String[]{command};
            }
        }

        if (input.contains(" ")) {
            String[] parts = input.split(" ");
            if (parts[0].equals(Constant.Playback)) {
                return parts;
            }
            return new String[]{Constant.Invalid_Input};
        }
        else if (input.length() == 3 && input.charAt(0) == '@' &&
                ((input.charAt(1) >= '1' &&
                        input.charAt(1) <= '9') || (input.charAt(1) >= 'a' && input.charAt(1) <= 'f')) &&
                input.charAt(2) >= 'a' && input.charAt(2) <= 'o'){
            return new String[]{Constant.Bomb, input};
        }
        else if (isDigit(input)) {
            return new String[]{Constant.Switch, input};
        }
        else if (input.length() == 2 && ((input.charAt(0) >= '1' &&
        input.charAt(0) <= '9') || (input.charAt(0) >= 'a' && input.charAt(0) <= 'f')) && input.charAt(1) >= 'a' && input.charAt(1) <= 'o') {
            return new String[]{Constant.Move, input};
        }
        else {
            return new String[]{Constant.Invalid_Input};
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
