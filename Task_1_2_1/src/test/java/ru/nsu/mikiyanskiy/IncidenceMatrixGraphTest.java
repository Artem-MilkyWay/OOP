package ru.nsu.mikiyanskiy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileWriter;

public class IncidenceMatrixGraphTest {

    @Test
    public void testAddVertex() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addVertex(0);
        assertEquals(1, graph.getVerticesCount());
    }

    @Test
    public void testRemoveVertex() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addVertex(0);
        graph.removeVertex(0);
        assertEquals(0, graph.getVerticesCount());
    }

    @Test
    public void testRemoveEdge() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addVertex(2);
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        assertEquals(false, graph.getAdjacencyMatrix()[0][1]);
    }

    @Test
    public void testGetNeighbors() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addVertex(2);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        assertEquals(2, graph.getNeighbors(0).size());
    }

    @Test
    public void testTopologicalSort() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addVertex(4);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        assertEquals(4, graph.topologicalSort().get(0));
    }

    @Test
    public void testReadFromFile() {
        try {
            File tempFile = File.createTempFile("tempfile", ".txt");
            FileWriter writer = new FileWriter(tempFile);
            writer.write("4 2\n"); // Данные о 4 вершинах и 2 ребрах
            writer.write("1 1 0 0\n"); // Матрица инцидентности
            writer.write("0 0 1 1\n");
            writer.close();

            IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
            graph.readFromFile(tempFile.getPath());

            assertEquals(4, graph.getVerticesCount());
            assertEquals(2, graph.edgeCount);

            tempFile.delete();
        } catch (IOException e) {
            fail("IOException during test file creation: " + e.getMessage());
        }
    }
}
