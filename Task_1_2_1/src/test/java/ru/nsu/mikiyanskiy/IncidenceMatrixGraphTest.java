package ru.nsu.mikiyanskiy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;

public class IncidenceMatrixGraphTest {

    @Test
    public void testAddVertex() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addVertex(0);
        assertEquals(1, graph.getVerticesCount());

        graph.addVertex(1);
        assertEquals(2, graph.getVerticesCount());

        // Добавление уже существующей вершины не должно изменять количество вершин
        graph.addVertex(1);
        assertEquals(2, graph.getVerticesCount());
    }

    @Test
    public void testRemoveVertex() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);

        assertEquals(2, graph.getVerticesCount());

        graph.removeVertex(1);
        assertEquals(1, graph.getVerticesCount());

        // Попытка удалить несуществующую вершину
        graph.removeVertex(5);
        assertEquals(1, graph.getVerticesCount());
    }

    @Test
    public void testAddEdge() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);

        // Проверка соседей вершины
        List<Integer> neighbors = graph.getNeighbors(0);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(1));

        // Проверка что у вершины 1 нет соседей, так как это конечная точка
        neighbors = graph.getNeighbors(1);
        assertEquals(0, neighbors.size());
    }

    @Test
    public void testRemoveEdge() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        // До удаления
        assertEquals(1, graph.getNeighbors(0).size());
        assertEquals(1, graph.getNeighbors(1).size());

        // Удаление ребра
        graph.removeEdge(0, 1);

        // Проверка что сосед 1 удален у вершины 0
        assertEquals(0, graph.getNeighbors(0).size());
    }

    @Test
    public void testGetNeighbors() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);

        List<Integer> neighbors = graph.getNeighbors(0);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(1));
        assertTrue(neighbors.contains(2));

        neighbors = graph.getNeighbors(1);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(2));
    }

    @Test
    public void testReadFromFile() throws IOException {
        // Создаем временный файл
        File tempFile = File.createTempFile("graph", ".txt");

        // Пишем в файл данные графа
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("4\n");  // Количество вершин
            writer.write("1 2\n");  // Рёбра для вершины 0
            writer.write("0 3\n");  // Рёбра для вершины 1
        }

        // Чтение графа из файла
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.readFromFile(tempFile.getAbsolutePath());

        // Проверяем количество вершин
        assertEquals(4, graph.getVerticesCount());

        // Проверяем соседей каждой вершины
        assertEquals(Arrays.asList(3), graph.getNeighbors(0));
        assertEquals(Arrays.asList(2), graph.getNeighbors(1));
        // Удаляем временный файл
        tempFile.delete();
    }


    @Test
    public void testToString() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        String expectedOutput = "Incidence Matrix:\n" +
                "[-1, 1]\n" +
                "[0, -1, 1]\n";

        assertEquals(expectedOutput, graph.toString());
    }

    @Test
    public void testTopologicalSort() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        List<Integer> sorted = graph.topologicalSort();
        assertEquals(3, sorted.size());

        // Проверяем, что результат сортировки корректен
        assertTrue(sorted.indexOf(0) < sorted.indexOf(1));
        assertTrue(sorted.indexOf(1) < sorted.indexOf(2));
    }

    @Test
    public void testGetVerticesCount() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addVertex(0);
        graph.addVertex(1);
        assertEquals(2, graph.getVerticesCount());

        graph.addVertex(2);
        assertEquals(3, graph.getVerticesCount());
    }


    @Test
    public void testGetAdjacencyMatrix() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        boolean[][] matrix = graph.getAdjacencyMatrix();
        assertTrue(matrix[0][1]);
        assertTrue(matrix[1][2]);
        assertFalse(matrix[0][2]);
    }
}
