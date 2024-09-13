package ru.nsu.mikiyanskiy;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    // Основная функция heapSort
    public static void heapSort(int[] arr) {
        int n = arr.length;

        // Шаг 1: Построение кучи (перегруппировка массива)
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(arr, n, i);
        }

        // Шаг 2: Один за другим извлекаем элементы из кучи
        for (int i = n - 1; i > 0; i--) {
            // Перемещаем текущий корень (максимальный элемент) в конец массива
            swap(arr, 0, i);

            // Вызываем siftDown на уменьшенной куче
            siftDown(arr, i, 0);
        }
    }

    // Функция siftDown для восстановления структуры кучи
    public static void siftDown(int[] arr, int n, int i) {
        int largest = i;        // Инициализируем корень как наибольший элемент
        int left = 2 * i + 1;   // Левый дочерний элемент
        int right = 2 * i + 2;  // Правый дочерний элемент

        // Если левый дочерний элемент больше корня
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // Если правый дочерний элемент больше наибольшего элемента
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // Если наибольший элемент не корень
        if (largest != i) {
            swap(arr, i, largest);

            // Рекурсивно спускаемся вниз по дереву, пока не восстановим кучу
            siftDown(arr, n, largest);
        }
    }

    // Функция для обмена значениями в массиве
    public static void swap(int[] arr, int i1, int i2) {
        int temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }
}
