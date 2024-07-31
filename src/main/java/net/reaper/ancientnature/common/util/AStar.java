package net.reaper.ancientnature.common.util;

import java.util.*;

public class AStar {

    public static class Node implements Comparable<Node> {
        public int x;
        public int y;
        double gCost, hCost, fCost;
        Node parent;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.fCost, other.fCost);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Node node = (Node) obj;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private int[][] grid;
    private int width, height;

    public AStar(int[][] grid) {
        this.grid = grid;
        this.width = grid.length;
        this.height = grid[0].length;
    }

    public List<Node> findPath(int startX, int startY, int targetX, int targetY) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        HashSet<Node> closedList = new HashSet<>();

        Node startNode = new Node(startX, startY);
        Node targetNode = new Node(targetX, targetY);

        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();
            closedList.add(currentNode);

            if (currentNode.equals(targetNode)) {
                return reconstructPath(currentNode);
            }

            for (Node neighbor : getNeighbors(currentNode)) {
                if (closedList.contains(neighbor)) continue;

                double tentativeGCost = currentNode.gCost + getDistance(currentNode, neighbor);

                if (tentativeGCost < neighbor.gCost || !openList.contains(neighbor)) {
                    neighbor.gCost = tentativeGCost;
                    neighbor.hCost = getDistance(neighbor, targetNode);
                    neighbor.fCost = neighbor.gCost + neighbor.hCost;
                    neighbor.parent = currentNode;

                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }

        return Collections.emptyList();
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                int newX = node.x + dx;
                int newY = node.y + dy;

                if (isValid(newX, newY)) {
                    neighbors.add(new Node(newX, newY));
                }
            }
        }

        return neighbors;
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height && grid[x][y] == 0;
    }

    private double getDistance(Node a, Node b) {
        int dx = Math.abs(a.x - b.x);
        int dy = Math.abs(a.y - b.y);
        return Math.sqrt(dx * dx + dy * dy);
    }

    private List<Node> reconstructPath(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }
}