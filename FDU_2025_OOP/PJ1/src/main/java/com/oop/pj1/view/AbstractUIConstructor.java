package com.oop.pj1.view;

import com.oop.pj1.controller.GameController;
import com.oop.pj1.data.Constant;
import com.oop.pj1.data.GlobalEntities;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import com.oop.pj1.Game;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

import static com.oop.pj1.data.GlobalEntities.ADD_GAME_WIDTH;

public abstract class AbstractUIConstructor {
    public abstract GridPane createBoard(GameController game);
    public abstract VBox createGameInfo(GameController game);

    Label blackPlayer = new Label();
    Label whitePlayer = new Label();

    Button demoMode = new Button("演示模式");

    public AbstractUIConstructor() {
        demoMode.setOnAction(_ -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择一个文件");

            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

            File selectedFile = fileChooser.showOpenDialog(Game.getStage());

            if (selectedFile != null) {
                String absolutePath = selectedFile.getAbsolutePath();
                try {
                    Game.playDemo(absolutePath);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public VBox createGameList() {
        VBox vbox = new VBox(10);
        vbox.setPrefWidth(GlobalEntities.GAME_LIST_WIDTH);
        

        Label title = new Label("游戏列表");
        title.setStyle(String.format("-fx-font-size: %.0fpx;", GlobalEntities.height / 35));
        
        ListView<String> listView = new ListView<>();
        listView.setPrefWidth(GlobalEntities.GAME_LIST_WIDTH - 20);
        listView.setStyle(String.format("-fx-font-size: %.0fpx;", GlobalEntities.height / 40));

        for (int i = 1; i <= Game.getGameTotal(); ++i) {
            GameController game = Game.getGameList()[i];
            listView.getItems().add(game.getGameType());
        }

        listView.setOnMouseClicked(_ -> {
            String sid = listView.getSelectionModel().getSelectedItem();
            if (sid == null) return;

            int id = listView.getSelectionModel().getSelectedIndex();
            Game.switchGame(id + 1);
            Game.refreshGame();
        });

        vbox.getChildren().addAll(title, listView);

        return vbox;
    }

    public VBox createAddNewGame() {
        VBox vbox = new VBox(10);
        vbox.setPrefWidth(ADD_GAME_WIDTH);
        

        Label title = new Label("添加新游戏");
        title.setStyle(String.format("-fx-font-size: %.0fpx;", GlobalEntities.height / 35));
        Button addPeace = new Button("添加Peace");
        Button addReversi = new Button("添加Reversi");
        Button addGomoku = new Button("添加Gomoku");

        String buttonStyle = String.format("""
            -fx-font-size: %.0fpx;
            -fx-min-height: %.0fpx;
            -fx-max-height: %.0fpx;
            """, 
            GlobalEntities.height / 60,
            GlobalEntities.height / 25,
            GlobalEntities.height / 25
        );

        addPeace.setStyle(buttonStyle);
        addReversi.setStyle(buttonStyle);
        addGomoku.setStyle(buttonStyle);

        addPeace.setPrefWidth(ADD_GAME_WIDTH - 20);
        addReversi.setPrefWidth(ADD_GAME_WIDTH - 20);
        addGomoku.setPrefWidth(ADD_GAME_WIDTH - 20);

        addPeace.setOnAction(_ -> Game.addGame(Constant.Peace));
        addReversi.setOnAction(_ -> Game.addGame(Constant.Reversi));
        addGomoku.setOnAction(_ -> Game.addGame(Constant.Gomoku));

        vbox.getChildren().addAll(title, addPeace, addReversi, addGomoku);

        return vbox;
    }

    protected void changeLabelBorder(GameController game) {
        double fontSize = GlobalEntities.height / 50;
        String baseStyle = String.format("-fx-font-size: %.0fpx;", fontSize);

        if (Objects.equals(game.getCurrentPlayer().getName(), Constant.name1P)) {
            blackPlayer.setStyle(baseStyle + "-fx-border-color: red; -fx-border-width: 2px;");
            whitePlayer.setStyle(baseStyle + "-fx-border-color: transparent; -fx-border-width: 2px;");
        }
        else if (Objects.equals(game.getCurrentPlayer().getName(), Constant.name2P)) {
            blackPlayer.setStyle(baseStyle + "-fx-border-color: transparent; -fx-border-width: 2px;");
            whitePlayer.setStyle(baseStyle + "-fx-border-color: red; -fx-border-width: 2px;");
        }
    }
}
