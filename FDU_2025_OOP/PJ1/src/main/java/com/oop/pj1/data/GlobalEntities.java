package com.oop.pj1.data;

import com.oop.pj1.view.Renderer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import static java.lang.Double.max;

public class GlobalEntities {
    public static Renderer renderer = new Renderer();
    public static BorderPane root = new BorderPane();
    public static double height = 600;
    public static double width = 800;
    public static Scene scene = new Scene(root, width, height);
    public static double BOARD_SIZE = height / 1.8;
    public static double GAME_INFO_WIDTH = height / 5;
    public static double GAME_LIST_WIDTH = height / 5;
    public static double ADD_GAME_WIDTH = height / 5;

    public static double GAP = (width - BOARD_SIZE - GAME_INFO_WIDTH - GAME_LIST_WIDTH - ADD_GAME_WIDTH) / 4;

    public static void updateGap() {
        GAP = max((width - BOARD_SIZE - GAME_INFO_WIDTH - GAME_LIST_WIDTH - ADD_GAME_WIDTH) / 4, 5);
    }

    public static void updateSize() {
        if ((width - height / 1.8 - height / 5 * 3) / 4 < 5) return;

        BOARD_SIZE = height / 1.8;
        GAME_INFO_WIDTH = height / 5;
        GAME_LIST_WIDTH = height / 5;
        ADD_GAME_WIDTH = height / 5;
    }
}
