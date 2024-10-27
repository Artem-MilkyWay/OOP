package ru.nsu.mikiyanskiy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * граф в виде матрицы инцендентности
 */
public class IncidenceMatrixGraph implements Graph {
    private int[][] incidenceMatrix; // Матрица инцидентности
    private int vertexCount;         // Количество вершин
    public int edgeCount;           // Количество рёбер

    public IncidenceMatrixGraph() {
        incidenceMatrix = new int[0][0];
        vertexCount = 0;
        edgeCount = 0;
    }

    @Override
    public void addVertex(int vertex) {
        if (vertex >= vertexCount) {
            // Увеличиваем количество вершин и расширяем матрицу
            vertexCount = vertex + 1;
            int[][] newMatrix = new int[vertexCount][edgeCount];
            for (int i = 0; i < incidenceMatrix.length; i++) {
                newMatrix[i] = Arrays.copyOf(incidenceMatrix[i], edgeCount);
            }
            incidenceMatrix = newMatrix;
        }
    }

    @Override
    public void removeVertex(int vertex) {
        if (vertex < 0 || vertex >= vertexCount) {
            throw new IllegalArgumentException("Vertex does not exist");
        }
        // Удаляем вершину, сдвигаем все последующие вершины на место удалённой
        for (int i = vertex; i < vertexCount - 1; i++) {
            incidenceMatrix[i] = incidenceMatrix[i + 1];
        }
        vertexCount--;

        // Обрезаем массив до нового количества вершин
        incidenceMatrix = Arrays.copyOf(incidenceMatrix, vertexCount);
    }

    @Override
    public void addEdge(int fromVertex, int toVertex) {
        if (fromVertex >= vertexCount || toVertex >= vertexCount) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        // Увеличиваем количество рёбер и расширяем матрицу
        edgeCount++;
        for (int i = 0; i < vertexCount; i++) {
            incidenceMatrix[i] = Arrays.copyOf(incidenceMatrix[i], edgeCount);
        }

        // Обозначаем ребро в матрице
        incidenceMatrix[fromVertex][edgeCount - 1] = 1;  // Исходящее ребро
    }

    @Override
    public void removeEdge(int fromVertex, int toVertex) {
        // Найдём ребро между fromVertex и toVertex
        int edgeIndex = -1;
        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix[fromVertex][i] == 1 && incidenceMatrix[toVertex][i] == -1) {
                edgeIndex = i;
                break;
            }
        }
        if (edgeIndex != -1) {
            // Удаляем ребро, сдвигаем все последующие рёбра на место удалённого
            for (int i = 0; i < vertexCount; i++) {
                for (int j = edgeIndex; j < edgeCount - 1; j++) {
                    incidenceMatrix[i][j] = incidenceMatrix[i][j + 1];
                }
                incidenceMatrix[i] = Arrays.copyOf(incidenceMatrix[i], edgeCount - 1);
            }
            edgeCount--;
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix[vertex][i] == 1) {
                neighbors.add(i);// Исходящее ребро
            }
        }
        return neighbors;
    }

    @Override
    public void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            // Чтение количества вершин и рёбер
            if ((line = br.readLine()) != null) {
                String[] counts = line.trim().split(" ");
                if (counts.length != 2) {
                    throw new IOException("Invalid data format");
                }
                vertexCount = Integer.parseInt(counts[0]);
                edgeCount = Integer.parseInt(counts[1]);
                incidenceMatrix = new int[vertexCount][edgeCount];
            }

            // Чтение матрицы инцидентности
            int row = 0;
            while ((line = br.readLine()) != null && row < vertexCount) {
                String[] values = line.trim().split(" ");
                if (values.length != edgeCount) {
                    throw new IOException("Invalid data format");
                }
                for (int col = 0; col < edgeCount; col++) {
                    incidenceMatrix[row][col] = Integer.parseInt(values[col]);
                }
                row++;
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Ошибка формата числа: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vertexCount; i++) {
            sb.append("Vertex ").append(i).append(": ");
            for (int j = 0; j < edgeCount; j++) {
                sb.append(incidenceMatrix[i][j]).append(" ");
            }
            sb.append("n");
        }
        return sb.toString();
    }

    @Override
    public List<Integer> topologicalSort() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            if (!visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }

        List<Integer> sortedList = new ArrayList<>();
        while (!stack.isEmpty()) {
            sortedList.add(stack.pop());
        }
        return sortedList;
    }

    /**
     * вспомогательная ф-ция сортировки.
     *
     * @param v - обрабатываемая вершина
     * @param visited - массив посещенных вершин
     * @param stack - стек в который помещаем обработанные вершины
     */
    private void topologicalSortUtil(int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;
        for (int j = 0; j < edgeCount; j++) {
            if (incidenceMatrix[v][j] == 1) {
                for (int i = 0; i < vertexCount; i++) {
                    if (incidenceMatrix[i][j] == -1 && !visited[i]) {
                        topologicalSortUtil(i, visited, stack);
                    }
                }
            }
        }
        stack.push(v);
    }

    @Override
    public int getVerticesCount() {
        return vertexCount;
    }

    @Override
    public boolean[][] getAdjacencyMatrix() {
        boolean[][] adjacencyMatrix = new boolean[vertexCount][vertexCount];

        // Проходим по всем рёбрам
        for (int i = 0; i < edgeCount; i++) {
            int fromVertex = -1;
            int toVertex = -1;

            // Ищем вершины, инцидентные этому ребру
            for (int j = 0; j < vertexCount; j++) {
                if (incidenceMatrix[j][i] == 1) {
                    // Первая вершина - откуда идёт ребро
                    if (fromVertex == -1) {
                        fromVertex = j;
                    }
                    // Вторая вершина - куда идёт ребро
                    else {
                        toVertex = j;
                        break;
                    }
                }
            }

            // Если нашли обе вершины, помечаем их как смежные
            if (fromVertex != -1 && toVertex != -1) {
                adjacencyMatrix[fromVertex][toVertex] = true;
                adjacencyMatrix[toVertex][fromVertex] = true; // Для неориентированного графа
            }
        }

        return adjacencyMatrix;
    }
}