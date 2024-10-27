package ru.nsu.mikiyanskiy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class AdjacencyMatrixGraphTest {

    @Test
    public void testAddVertex() {
        Graph graph = new AdjacencyMatrixGraph(5); // Создаем граф с 5 вершинами
        graph.addVertex(5); // Добавляем новую вершину

        assertEquals(6, graph.getVerticesCount()); // Проверяем количество вершин
    }

    @Test
    public void testRemoveVertex() {
        Graph graph = new AdjacencyMatrixGraph(5); // Создаем граф с 5 вершинами
        graph.removeVertex(0); // Удаляем вершину

        assertEquals(4, graph.getVerticesCount()); // Проверяем количество вершин после удаления
    }

    @Test
    public void testAddEdge() {
        Graph graph = new AdjacencyMatrixGraph(5); // Создаем граф с 5 вершинами
        graph.addEdge(0, 1); // Добавляем ребро

        assertTrue(graph.getAdjacencyMatrix()[0][1]); // Проверяем наличие ребра
    }

    @Test
    public void testRemoveEdge() {
        Graph graph = new AdjacencyMatrixGraph(5); // Создаем граф с 5 вершинами
        graph.addEdge(0, 1); // Добавляем ребро
        graph.removeEdge(0, 1); // Удаляем ребро

        assertFalse(graph.getAdjacencyMatrix()[0][1]); // Проверяем отсутствие ребра
    }

    @Test
    public void testGetNeighbors() {
        Graph graph = new AdjacencyMatrixGraph(5); // Создаем граф с 5 вершинами
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);

        List<Integer> neighborsOfVertex0 = graph.getNeighbors(0); // Получаем соседей вершины 0

        assertEquals(2, neighborsOfVertex0.size()); // Проверяем количество соседей
        assertTrue(neighborsOfVertex0.contains(1)); // Проверяем наличие соседа 1
        assertTrue(neighborsOfVertex0.contains(2)); // Проверяем наличие соседа 2
    }

    @Test
    public void testTopologicalSort() {
        Graph graph = new AdjacencyMatrixGraph(5); // Создаем граф с 5 вершинами
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        List<Integer> sortedList = graph.topologicalSort(); // Выполняем топологическую сортировку

        assertEquals(5, sortedList.size());
        assertEquals(0, sortedList.get(0).intValue());
        assertEquals(2, sortedList.get(1).intValue());
        assertEquals(1, sortedList.get(2).intValue());
        assertEquals(3, sortedList.get(3).intValue());
        assertEquals(4, sortedList.get(4).intValue());
    }

    @Test
    public void testReadFromFile() {
        File tempFile = new File("tempfile.txt");

        // Создаем временный файл с данными для графа
        int[][] matrix = {
                {0, 1, 1, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0}
        };

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("5\n");
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    writer.write(matrix[i][j] + " "); // Записываем значение в матрице
                }
                writer.write("\n"); // Переходим на новую строку после каждой строки
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Graph graph = new AdjacencyMatrixGraph(5); // Создаем граф
        graph.readFromFile(tempFile.getPath()); // Читаем данные из временного файла

        // Проверяем, что данные прочитаны корректно
        assertTrue(graph.getAdjacencyMatrix()[0][1]); // Проверяем наличие ребра 0-1
        assertTrue(graph.getAdjacencyMatrix()[0][2]); // Проверяем наличие ребра 0-2
        assertTrue(graph.getAdjacencyMatrix()[1][3]); // Проверяем наличие ребра 1-3
        assertTrue(graph.getAdjacencyMatrix()[2][3]); // Проверяем наличие ребра 2-3
        assertTrue(graph.getAdjacencyMatrix()[3][4]); // Проверяем наличие ребра 3-4

        tempFile.delete(); // Удаляем временный файл
    }
}
