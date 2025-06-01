package com.oop.pj1.view;

import com.oop.pj1.Game;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Messenger {
    public enum MessageType { INFO, SUCCESS, WARNING, ERROR }

    private static final Stage gameStage = Game.stage;
    private static final double WIDTH = 300;
    private static final double HEIGHT = 80;
    private static final int DISPLAY_TIME = 3000; // 显示时间(ms)
    private static final int SPACING = 10;        // 消息间距
    private static final List<Stage> activeToasts = new ArrayList<>();
    
    private Messenger() {}

    private static void showMessage(MessageType type, String message) {
        Stage stage = new Stage(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);
        
        // 创建图标
        ImageView icon = new ImageView(getIcon(type));
        icon.setFitWidth(24);
        icon.setFitHeight(24);
        
        // 创建文本标签
        Label text = new Label(message);
        text.setWrapText(true);
        text.setTextFill(Color.WHITE);
        text.setStyle("-fx-font-size: 14px;");
        
        // 创建内容容器
        HBox root = new HBox(12, icon, text);
        root.setAlignment(Pos.CENTER_LEFT);
        root.setPadding(new Insets(15));
        root.setStyle(String.format("""
            -fx-background-color: %s;
            -fx-background-radius: 6px;
            -fx-border-radius: 6px;
            -fx-border-color: rgba(255,255,255,0.2);
            -fx-border-width: 1px;
            """, getBackgroundColor(type))
        );
        root.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.3)));
        
        // 设置场景
        Scene scene = new Scene(root);
        scene.setFill(null);
        stage.setScene(scene);
        
        // 计算位置（游戏窗口右下角）
        double gameWidth = gameStage.getWidth();
        double gameHeight = gameStage.getHeight();
        double gameX = gameStage.getX();
        double gameY = gameStage.getY();
        
        double xPos = gameX + gameWidth - WIDTH - 20;
        double yPos = gameY + gameHeight - HEIGHT - 20;
        
        // 根据已有消息调整位置
        yPos -= activeToasts.size() * (HEIGHT + SPACING);
        
        stage.setX(xPos);
        stage.setY(yPos);
        
        activeToasts.add(stage);
        
        // 入场动画
        root.setOpacity(0);
        root.setTranslateX(20);
        
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(200), root);
        slideIn.setFromX(20);
        slideIn.setToX(0);
        
        ParallelTransition in = new ParallelTransition(fadeIn, slideIn);
        
        // 出场动画
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), root);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(200), root);
        slideOut.setFromX(0);
        slideOut.setToX(20);
        
        ParallelTransition out = new ParallelTransition(fadeOut, slideOut);
        out.setOnFinished(_ -> {
            stage.close();
            activeToasts.remove(stage);
            repositionAll(); // 重新排列剩余消息
        });
        
        // 监听游戏窗口位置和大小变化
        gameStage.xProperty().addListener((_, _, newX) -> stage.setX(newX.doubleValue() + gameStage.getWidth() - WIDTH - 20));
        gameStage.yProperty().addListener((_, _, _) -> repositionAll());
        gameStage.widthProperty().addListener((_, _, newWidth) -> stage.setX(gameStage.getX() + newWidth.doubleValue() - WIDTH - 20));
        gameStage.heightProperty().addListener((_, _, _) -> repositionAll());
        
        // 显示并自动关闭
        stage.show();
        in.play();
        
        PauseTransition delay = new PauseTransition(Duration.millis(DISPLAY_TIME));
        delay.setOnFinished(_ -> out.play());
        delay.play();
    }
    
    private static void repositionAll() {
        if (activeToasts.isEmpty()) return;
        
        double gameHeight = gameStage.getHeight();
        double gameY = gameStage.getY();
        double baseY = gameY + gameHeight - HEIGHT - 20;
        
        for (int i = 0; i < activeToasts.size(); i++) {
            Stage stage = activeToasts.get(i);
            double targetY = baseY - i * (HEIGHT + SPACING);
            stage.setY(targetY);
        }
    }

    private static String getBackgroundColor(MessageType type) {
        return switch (type) {
            case INFO    -> "#3498db"; // 蓝色
            case SUCCESS -> "#2ecc71"; // 绿色
            case WARNING -> "#f1c40f"; // 黄色
            case ERROR   -> "#e74c3c"; // 红色
        };
    }
    
    private static Image getIcon(MessageType type) {
        String path = switch (type) {
            case INFO    -> "/icons/info.png";
            case SUCCESS -> "/icons/success.png";
            case WARNING -> "/icons/warning.png";
            case ERROR   -> "/icons/error.png";
        };
        return new Image(Objects.requireNonNull(Messenger.class.getResourceAsStream(path)));
    }

    public static void info(String format, Object... args) {
        Platform.runLater(() -> showMessage(MessageType.INFO, String.format(format, args)));
    }

    public static void success(String format, Object... args) {
        Platform.runLater(() -> showMessage(MessageType.SUCCESS, String.format(format, args)));
    }

    public static void error(String format, Object... args) {
        Platform.runLater(() -> showMessage(MessageType.ERROR, String.format(format, args)));
    }
}
