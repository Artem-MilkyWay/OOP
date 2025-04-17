package ru.nsu.mikiyanskiy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.nsu.mikiyanskiy.Main;

public class ConfigurationController {

    @FXML
    private TextField rowsField;
    @FXML
    private TextField colsField;
    @FXML
    private TextField foodField;
    @FXML
    private TextField winLengthField;

    // Проверка валидности строк и колонок
    private static boolean isValidRowAndCol(int rows, int cols) {
        return rows >= 5 && rows <= 30 && cols >= 5 && cols <= 30;
    }

    // Проверка валидности количества еды
    private static boolean isValidFoodAmount(int food) {
        return food >= 1 && food <= 16;
    }

    // Проверка валидности длины победы
    private static boolean isValidWinLength(int winLength, int rows, int cols) {
        return winLength >= 2 && winLength <= rows * cols;
    }

    // Статический метод для валидации конфигурации
    public static boolean isValidConfig(int rows, int cols, int food, int winLength) {
        return isValidRowAndCol(rows, cols) && isValidFoodAmount(food) && isValidWinLength(winLength, rows, cols);
    }

    @FXML
    private void startGame() {
        try {
            int rows = Integer.parseInt(rowsField.getText());
            int cols = Integer.parseInt(colsField.getText());
            int food = Integer.parseInt(foodField.getText());
            int winLength = Integer.parseInt(winLengthField.getText());

            if (!isValidRowAndCol(rows, cols)) {
                System.out.println("Row and column sizes should be from 5 to 30.");
                return;
            }

            if (!isValidFoodAmount(food)) {
                System.out.println("Food count should be between 1 and 16.");
                return;
            }

            if (!isValidWinLength(winLength, rows, cols)) {
                System.out.println("Win length should be between 2 and " + (rows * cols));
                return;
            }

            Main.startGameWithConfig(rows, cols, food, winLength);
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numbers.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
