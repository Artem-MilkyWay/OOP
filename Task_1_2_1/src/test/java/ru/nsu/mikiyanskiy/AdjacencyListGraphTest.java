package ru.nsu.mikiyanskiy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdjacencyListGraphTest {
    private AdjacencyListGraph graph;

    @BeforeEach
    public void setUp() {
        graph = new AdjacencyListGraph();
    }

    @Test
    public void testAddVertex() {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);

        assertEquals(3, graph.getVerticesCount());
    }

    @Test
    public void testRemoveVertex() {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(0,2);
        graph.removeVertex(1);

        assertEquals(2, graph.getVerticesCount());
        assertEquals(Arrays.asList(), graph.getNeighbors(1)); // Должно быть пусто
        assertEquals(Arrays.asList(2), graph.getNeighbors(0)); // Проверяем соседей
    }

    @Test
    public void testAddEdge() {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);

        List<Integer> neighbors = graph.getNeighbors(0);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(1)); // Проверяем, что 1 - сосед 0
    }

    @Test
    public void testRemoveEdge() {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);

        List<Integer> neighbors = graph.getNeighbors(0);
        assertEquals(0, neighbors.size()); // Соседей не должно быть
    }

    @Test
    public void testGetNeighbors() {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);

        List<Integer> neighbors = graph.getNeighbors(0);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(1));
        assertTrue(neighbors.contains(2));
    }

    @Test
    public void testTopologicalSort() {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        List<Integer> sorted = graph.topologicalSort();
        assertEquals(Arrays.asList(0, 1, 2), sorted); // Проверяем, что порядок правильный
    }

    @Test
    public void testGetAdjacencyMatrix() {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);

        boolean[][] matrix = graph.getAdjacencyMatrix();
        assertEquals(2, matrix.length); // Должно быть 2 строки
        assertEquals(true, matrix[0][1]); // 0 связано с 1
        assertEquals(false, matrix[1][0]); // 1 не связано с 0
    }

    @Test
    public void testReadFromFile() {
        try {
            File tempFile = File.createTempFile("tempfile", ".txt");
            FileWriter writer = new FileWriter(tempFile);
            writer.write("5\n"); // Записываем количество вершин и переходим на новую строку
            writer.write("0 1\n"); // Записываем ребра и переводим строку
            writer.write("0 2\n");
            writer.write("1 3\n");
            writer.write("2 3\n");
            writer.write("3 4\n");
            writer.close();


            AdjacencyListGraph graph = new AdjacencyListGraph();
            graph.readFromFile(tempFile.getPath());

            assertEquals(5, graph.getVerticesCount());
            assertEquals(5, graph.getAdjacencyMatrix().length);

            tempFile.delete();
        } catch (IOException e) {
            fail("Exception thrown while creating test file: " + e.getMessage());
        }
    }
}
