package ru.nsu.mikiyanskiy;

public class Main {
    public static void main(String[] args) {
        Game new_game = new Game();  // Создаём объект игры
        new_game.start(1);           // Запускаем игру (аргумент 0 означает 1 раунд)
    }
}
