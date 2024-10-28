package ru.nsu.mikiyanskiy;

import java.util.List;

/**
 * Интерфейс для класса граф.
 */
public interface Graph {
    void addVertex(int vertex);
    void removeVertex(int vertex);
    void addEdge(int fromVertex, int toVertex);
    void removeEdge(int fromVertex, int toVertex);
    List<Integer> getNeighbors(int vertex);
    void readFromFile(String filename);
    String toString();
    public List<Integer> topologicalSort();
    int getVerticesCount();

    /**
     * Метод для получения матрицы смежности или инцидентности (для сравнения графов представленных в разных видах).
     *
     * @return - матрицу инцедентности
     */
    boolean[][] getAdjacencyMatrix(); // Можно изменить на int[][], если требуется

    static boolean equals(Graph graph1, Graph graph2) {
        if (graph1 == graph2) {
            return true; // одинаковые ссылки
        }
        if (graph1 == null || graph2 == null) {
            return false; // один из графов равен null
        }

        // Сравниваем количество вершин
        if (graph1.getVerticesCount() != graph2.getVerticesCount()) {
            return false;
        }

        // Сравниваем матрицы
        boolean[][] matrix1 = graph1.getAdjacencyMatrix();
        boolean[][] matrix2 = graph2.getAdjacencyMatrix();

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j++) {
                if (matrix1[i][j] != matrix2[i][j]) {
                    return false; // если значения различаются
                }
            }
        }

        return true; // графы равны
    }
}
