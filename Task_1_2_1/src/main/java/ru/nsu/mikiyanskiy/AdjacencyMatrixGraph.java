package ru.nsu.mikiyanskiy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * граф в виде матрицы смежности.
 */
public class AdjacencyMatrixGraph implements Graph {
    private boolean[][] adjacencyMatrix;
    private int vertexCount; // Количество вершин в графе

    public AdjacencyMatrixGraph(int size) {
        adjacencyMatrix = new boolean[size][size];
        vertexCount = size; // Устанавливаем количество вершин, переданных в конструктор
    }

    @Override
    public void addVertex(int vertex) {
        // Создаем новую матрицу большего размера
        boolean[][] newMatrix = new boolean[vertexCount + 1][vertexCount + 1];

        // Копируем старые значения в новую матрицу
        for (int i = 0; i < vertexCount; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0, vertexCount);
        }

        adjacencyMatrix = newMatrix; // Обновляем ссылку на матрицу
        vertexCount++; // Увеличиваем количество вершин
    }

    @Override
    public void removeVertex(int vertex) {
        if (vertex < 0 || vertex >= vertexCount) {
            throw new IllegalArgumentException("Vertex index out of bounds.");
        }

        // Удаляем все рёбра, исходящие из вершины
        for (int i = 0; i < vertexCount; i++) {
            adjacencyMatrix[vertex][i] = false; // Удаляем рёбра из удаляемой вершины
            adjacencyMatrix[i][vertex] = false; // Удаляем рёбра, входящие в удаляемую вершину
        }

        // Создаем новую матрицу меньшего размера
        boolean[][] newMatrix = new boolean[vertexCount - 1][vertexCount - 1];
        for (int i = 0, newRow = 0; i < vertexCount; i++) {
            if (i == vertex) continue; // Пропускаем удаляемую строку
            for (int j = 0, newCol = 0; j < vertexCount; j++) {
                if (j == vertex) continue; // Пропускаем удаляемый столбец
                newMatrix[newRow][newCol] = adjacencyMatrix[i][j]; // Копируем значения
                newCol++;
            }
            newRow++;
        }

        adjacencyMatrix = newMatrix; // Обновляем матрицу смежности
        vertexCount--; // Уменьшаем количество вершин
    }

    @Override
    public void addEdge(int fromVertex, int toVertex) {
        if (fromVertex < vertexCount && toVertex < vertexCount) {
            adjacencyMatrix[fromVertex][toVertex] = true;
        } else {
            throw new IllegalArgumentException("Vertex index out of bounds.");
        }
    }

    @Override
    public void removeEdge(int fromVertex, int toVertex) {
        if (fromVertex < vertexCount && toVertex < vertexCount) {
            adjacencyMatrix[fromVertex][toVertex] = false;
        } else {
            throw new IllegalArgumentException("Vertex index out of bounds.");
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            if (adjacencyMatrix[vertex][i]) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    @Override
    public void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            // Чтение количества вершин
            if ((line = br.readLine()) != null) {
                vertexCount = Integer.parseInt(line.trim());
                adjacencyMatrix = new boolean[vertexCount][vertexCount]; // Инициализация матрицы смежности
            }

            // Чтение матрицы смежности
            int row = 0;
            while ((line = br.readLine()) != null && row < vertexCount) {
                String[] values = line.trim().split("\s+");
                if (values.length == vertexCount) { // Проверка количества значений в строке
                    for (int col = 0; col < vertexCount; col++) {
                        adjacencyMatrix[row][col] = values[col].equals("1");
                    }
                } else {
                    System.err.println("Неверное количество значений в строке: " + line);
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
        for (boolean[] row : adjacencyMatrix) {
            for (boolean cell : row) {
                sb.append(cell ? "1 " : "0 ");
            }
            sb.append("\n");
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
        for (int i = 0; i < vertexCount; i++) {
            if (adjacencyMatrix[v][i] && !visited[i]) {
                topologicalSortUtil(i, visited, stack);
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
        return adjacencyMatrix; // Возвращает матрицу смежности
    }

}
