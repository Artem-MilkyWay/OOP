package ru.nsu.mikiyanskiy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.mikiyanskiy.controller.SnakeGameController;

public class Main extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Snake Game");
        showConfigScene();
    }

    public static void startGameWithConfig(int rows, int cols, int food, int winLength) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/nsu/mikiyanskiy/view/game.fxml"));
        Scene scene = new Scene(loader.load());
        SnakeGameController controller = loader.getController();
        controller.initGame(rows, cols, food, winLength);
        
        // Устанавливаем сцену
        primaryStage.setScene(scene);
        
        // Устанавливаем размер окна под игровое поле
        int cellSize = 30; // Размер клетки
        int padding = 40; // Отступы
        primaryStage.setWidth(cols * cellSize + padding + 300);
        primaryStage.setHeight(rows * cellSize + padding + 420); // +50 для счетчика
        
        // Центрируем окно
        primaryStage.centerOnScreen();
    }

    public static void showConfigScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/nsu/mikiyanskiy/view/game_config.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Snake Game - Settings");
        primaryStage.setScene(scene);

        // Устанавливаем размер окна настроек
        primaryStage.setWidth(850);
        primaryStage.setHeight(750);
        // Центрируем окно
        primaryStage.centerOnScreen();
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
