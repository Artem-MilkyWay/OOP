package ru.nsu.mikiyanskiy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Граф в виде матрицы инцидентности.
 */
public class IncidenceMatrixGraph implements Graph {
    private List<int[]> incidenceMatrix; // Список рёбер как массивов инцидентности
    private int verticesCount;

    public IncidenceMatrixGraph() {
        incidenceMatrix = new ArrayList<>();
        verticesCount = 0;
    }

    @Override
    public void addVertex(int vertex) {
        // Если вершина уже существует, ничего не делаем
        if (vertex < verticesCount) {
            return;
        }

        // Далее расширяем матрицу как в предыдущем примере
        verticesCount = vertex + 1;
        for (int[] edge : incidenceMatrix) {
            int[] newEdge = Arrays.copyOf(edge, verticesCount);
            edge = newEdge;
        }
    }


    @Override
    public void removeVertex(int vertex) {
        if (vertex >= verticesCount) {
            return;
        }

        // Удаление всех рёбер, инцидентных этой вершине
        incidenceMatrix.removeIf(edge -> edge[vertex] != 0);

        // Обновляем количество вершин, если это была последняя вершина
        if (vertex == verticesCount - 1) {
            verticesCount--;
        }
    }

    @Override
    public void addEdge(int fromVertex, int toVertex) {
        addVertex(fromVertex); // Убедимся, что вершины существуют
        addVertex(toVertex);

        // Создаём новый массив инцидентности для ребра
        int[] edge = new int[verticesCount];
        edge[fromVertex] = -1; // Из вершины fromVertex
        edge[toVertex] = 1;    // В вершину toVertex

        incidenceMatrix.add(edge); // Добавляем новое ребро
    }

    @Override
    public void removeEdge(int fromVertex, int toVertex) {
        incidenceMatrix.removeIf(edge -> edge[fromVertex] == -1 && edge[toVertex] == 1);
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();

        // Проверяем существование вершины
        if (vertex < 0 || vertex >= verticesCount) {
            return neighbors; // Если вершина не существует, возвращаем пустой список
        }

        // Проходим по каждому ребру в матрице инцидентности
        for (int[] edge : incidenceMatrix) {
            // Проверяем длину edge, чтобы избежать выхода за пределы
            if (edge.length > vertex && edge[vertex] == -1) { // Если вершина является началом ребра
                for (int i = 0; i < verticesCount; i++) {
                    // Проверяем длину edge, чтобы избежать выхода за пределы
                    if (edge.length > i && edge[i] == 1) { // Найти соседнюю вершину
                        neighbors.add(i); // Добавляем соседнюю вершину в список
                    }
                }
            }
        }

        return neighbors; // Возвращаем список соседей
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
            while ((line = br.readLine()) != null) {
                String[] vertices = line.trim().split(" "); // Разделение строки по пробелу
                if (vertices.length == 2) {
                    int fromVertex = Integer.parseInt(vertices[0]);
                    int toVertex = Integer.parseInt(vertices[1]);
                    addEdge(fromVertex, toVertex); // Добавляем ребро между вершинами
                }
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
        sb.append("Incidence Matrix:\n");

        for (int[] edge : incidenceMatrix) {
            sb.append(Arrays.toString(edge)).append("\n");
        }

        return sb.toString();
    }

    @Override
    public List<Integer> topologicalSort() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[verticesCount];

        for (int i = 0; i < verticesCount; i++) {
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

    private void topologicalSortUtil(int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;

        for (int[] edge : incidenceMatrix) {
            int fromVertex = -1;
            int toVertex = -1;

            // Находим начальную и конечную вершины для текущего ребра
            for (int i = 0; i < edge.length; i++) {
                if (edge[i] == -1) {
                    fromVertex = i;
                } else if (edge[i] == 1) {
                    toVertex = i;
                }
            }

            // Если это текущее ребро инцидентно вершине v и конечная вершина еще не посещена
            if (fromVertex == v && !visited[toVertex]) {
                topologicalSortUtil(toVertex, visited, stack);
            }
        }

        stack.push(v);
    }

    @Override
    public int getVerticesCount() {
        return verticesCount;
    }

    @Override
    public boolean[][] getAdjacencyMatrix() {
        boolean[][] matrix = new boolean[verticesCount][verticesCount];

        for (int[] edge : incidenceMatrix) {
            int fromVertex = -1;
            int toVertex = -1;

            // Поиск начальной и конечной вершин для данного ребра
            for (int i = 0; i < edge.length; i++) {
                if (edge[i] == -1) {
                    fromVertex = i;
                } else if (edge[i] == 1) {
                    toVertex = i;
                }
            }

            // Если ребро валидное, обновляем матрицу смежности
            if (fromVertex != -1 && toVertex != -1) {
                matrix[fromVertex][toVertex] = true;
            }
        }

        return matrix;
    }
}
