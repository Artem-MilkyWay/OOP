package ru.nsu.mikiyanskiy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {
    private IncidenceMatrixGraph incidenceGraph;
    private AdjacencyMatrixGraph adjacencyMatrixGraph;
    private AdjacencyListGraph adjacencyListGraph;

    @BeforeEach
    public void setUp() {
        incidenceGraph = new IncidenceMatrixGraph();
        adjacencyMatrixGraph = new AdjacencyMatrixGraph(3);
        adjacencyListGraph = new AdjacencyListGraph();

        // Настраиваем все графы одинаково
        setupGraph(incidenceGraph);
        setupGraph(adjacencyMatrixGraph);
        setupGraph(adjacencyListGraph);
    }

    private void setupGraph(Graph graph) {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
    }

    @Test
    public void testEqualsWithSameStructure() {
        assertTrue(Graph.equals(incidenceGraph, adjacencyListGraph), "Графы должны быть равны, так как они идентичны");
    }

    @Test
    public void testEqualsDifferentGraphs() {
        // Изменяем один из графов, чтобы они не были равны
        adjacencyListGraph.addVertex(3);
        assertFalse(Graph.equals(incidenceGraph, adjacencyListGraph), "Графы не должны быть равны, так как у одного из них больше вершин");

        // Изменяем структуру рёбер
        adjacencyMatrixGraph.removeEdge(0, 1);
        assertFalse(Graph.equals(incidenceGraph, adjacencyMatrixGraph), "Графы не должны быть равны, так как они имеют разные рёбра");
    }

    @Test
    public void testEqualsDifferentCountOfVertices() {
        // Создаем граф с другим количеством вершин
        AdjacencyListGraph differentGraph = new AdjacencyListGraph();
        differentGraph.addVertex(0);
        differentGraph.addVertex(1);
        // Не добавляем 2-ю вершину, чтобы они не совпадали
        assertFalse(Graph.equals(incidenceGraph, differentGraph), "Графы не должны быть равны, так как количество вершин различается");
    }
}
