package ru.nsu.mikiyanskiy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * граф в виде списка смежности.
 */
public class AdjacencyListGraph implements Graph {
    private Map<Integer, List<Integer>> adjacencyList;

    public AdjacencyListGraph() {
        adjacencyList = new HashMap<>();
    }

    @Override
    public void addVertex(int vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    @Override
    public void removeVertex(int vertex) {
        adjacencyList.remove(vertex);
        for (List<Integer> neighbors : adjacencyList.values()) {
            neighbors.remove((Integer) vertex);
        }
    }

    @Override
    public void addEdge(int fromVertex, int toVertex) {
        adjacencyList.putIfAbsent(fromVertex, new ArrayList<>());
        adjacencyList.putIfAbsent(toVertex, new ArrayList<>());
        adjacencyList.get(fromVertex).add(toVertex);
    }

    @Override
    public void removeEdge(int fromVertex, int toVertex) {
        List<Integer> neighbors = adjacencyList.get(fromVertex);
        if (neighbors != null) {
            neighbors.remove((Integer) toVertex);
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }

    @Override
    public void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Чтение количества вершин
            if ((line = br.readLine()) != null) {
                int vertexCount = Integer.parseInt(line.trim());
                for (int i = 0; i < vertexCount; i++) {
                    addVertex(i); // Добавляем вершины 0 до vertexCount - 1
                }
            }

            // Чтение рёбер
            int vertexIndex = 0; // Индекс текущей вершины
            while ((line = br.readLine()) != null) {
                String[] vertices = line.trim().split(" "); // Разделение строки по пробелу
                for (String vertex : vertices) {
                    int toVertex = Integer.parseInt(vertex);
                    addEdge(vertexIndex, toVertex); // Добавляем ребро между текущей вершиной и toVertex
                }
                vertexIndex++; // Переходим к следующей вершине
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Ошибка формата числа: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public List<Integer> topologicalSort() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[adjacencyList.size()];
        for (Integer vertex : adjacencyList.keySet()) {
            if (!visited[vertex]) {
                topologicalSortUtil(vertex, visited, stack);
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
        for (Integer neighbor : adjacencyList.get(v)) {
            if (!visited[neighbor]) {
                topologicalSortUtil(neighbor, visited, stack);
            }
        }
        stack.push(v);
    }

    @Override
    public int getVerticesCount() {
        return adjacencyList.size();
    }

    @Override
    public boolean[][] getAdjacencyMatrix() {
        int size = getVerticesCount();
        boolean[][] matrix = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            for (int neighbor : adjacencyList.get(i)) {
                matrix[i][neighbor] = true; // Устанавливаем связь
            }
        }

        return matrix; // Возвращаем созданную матрицу
    }

}
