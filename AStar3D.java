package io.github.joheras;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;


public class AStar3D {
    private static int DEFAULT_HV_COST = 10; // Horizontal - Vertical Cost
    private static int DEFAULT_DIAGONAL_COST = 14;
    private int hvCost;
    private int diagonalCost;
    private Node3D[][][] searchArea;
    
    private PriorityQueue<Node3D> openList;
    private Set<Node3D> closedSet;
    private Node3D initialNode;
    private Node3D finalNode;

    public AStar3D(int rows, int cols, int height, Node3D initialNode, Node3D finalNode, int hvCost, int diagonalCost) {
        this.hvCost = hvCost;
        this.diagonalCost = diagonalCost;
        setInitialNode(initialNode);
        setFinalNode(finalNode);
        this.searchArea = new Node3D[rows][cols][height];
        this.openList = new PriorityQueue<Node3D>(new Comparator<Node3D>() {
            @Override
            public int compare(Node3D node0, Node3D node1) {
                return Integer.compare(node0.getF(), node1.getF());
            }
        });
        setNodes();
        this.closedSet = new HashSet<>();
    }

    public AStar3D(int rows, int cols, int height, Node3D initialNode, Node3D finalNode) {
        this(rows, cols, height, initialNode, finalNode, DEFAULT_HV_COST, DEFAULT_DIAGONAL_COST);
    }

    private void setNodes() {
    	for (int k = 0; k < searchArea.length; k++) {
	        for (int i = 0; i < searchArea.length; i++) {
	            for (int j = 0; j < searchArea[0].length; j++) {
	                Node3D node = new Node3D(i, j, k);
	                node.calculateHeuristic(getFinalNode());
	                this.searchArea[i][j][k] = node;
	            }
	        }
    	}
    }

    public void setBlocks(int[][] blocksArray) {
        for (int i = 0; i < blocksArray.length; i++) {
            int row = blocksArray[i][0];
            int col = blocksArray[i][1];
            int height = blocksArray[i][2];
            setBlock(row, col, height);
        }
    }

    public List<Node3D> findPath() {
        openList.add(initialNode);
        while (!isEmpty(openList)) {
            Node3D currentNode = openList.poll();
            closedSet.add(currentNode);
            if (isFinalNode(currentNode)) {
                return getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
            }
        }
        return new ArrayList<Node3D>();
    }

    private List<Node3D> getPath(Node3D currentNode) {
        List<Node3D> path = new ArrayList<Node3D>();
        path.add(currentNode);
        Node3D parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
    }

    private void addAdjacentNodes(Node3D currentNode) {
        addAdjacentUpperRow(currentNode);
        addAdjacentMiddleRow(currentNode);
        addAdjacentLowerRow(currentNode);
    }

    private void addAdjacentLowerRow(Node3D currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int height = currentNode.getHeight();
        int lowerRow = row + 1;
        if (lowerRow < getSearchArea().length) {
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, lowerRow, height, getDiagonalCost()); 
            }
            if (col + 1 < getSearchArea()[0].length) {
                checkNode(currentNode, col + 1, lowerRow, height, getDiagonalCost()); 
            }
            checkNode(currentNode, col, lowerRow, height, getHvCost());
        }
    }

    private void addAdjacentMiddleRow(Node3D currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int height = currentNode.getHeight();
        int middleRow = row;
        if (col - 1 >= 0) {
            checkNode(currentNode, col - 1, middleRow, height, getHvCost());
        }
        if (col + 1 < getSearchArea()[0].length) {
            checkNode(currentNode, col + 1, middleRow, height, getHvCost());
        }
    }

    private void addAdjacentUpperRow(Node3D currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int height = currentNode.getHeight();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, upperRow, height, getDiagonalCost()); 
            }
            if (col + 1 < getSearchArea()[0].length) {
                checkNode(currentNode, col + 1, upperRow, height, getDiagonalCost()); 
            }
            checkNode(currentNode, col, upperRow, height, getHvCost());
        }
    }

    private void checkNode(Node3D currentNode, int col, int row, int height, int cost) {
        Node3D adjacentNode = getSearchArea()[row][col][height];
        if (!adjacentNode.isBlock() && !getClosedSet().contains(adjacentNode)) {
            if (!getOpenList().contains(adjacentNode)) {
                adjacentNode.setNodeData(currentNode, cost);
                getOpenList().add(adjacentNode);
            } else {
                boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
                if (changed) {
                   
                    // contents with the modified "finalCost" value of the modified node
                    getOpenList().remove(adjacentNode);
                    getOpenList().add(adjacentNode);
                }
            }
        }
    }

    private boolean isFinalNode(Node3D currentNode) {
        return currentNode.equals(finalNode);
    }

    private boolean isEmpty(PriorityQueue<Node3D> openList) {
        return openList.size() == 0;
    }

    private void setBlock(int row, int col, int height) {
        this.searchArea[row][col][height].setBlock(true);
    }

    public Node3D getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(Node3D initialNode) {
        this.initialNode = initialNode;
    }

    public Node3D getFinalNode() {
        return finalNode;
    }

    public void setFinalNode(Node3D finalNode) {
        this.finalNode = finalNode;
    }

    public Node3D[][][] getSearchArea() {
        return searchArea;
    }

    public void setSearchArea(Node3D[][][] searchArea) {
        this.searchArea = searchArea;
    }

    public PriorityQueue<Node3D> getOpenList() {
        return openList;
    }

    public void setOpenList(PriorityQueue<Node3D> openList) {
        this.openList = openList;
    }

    public Set<Node3D> getClosedSet() {
        return closedSet;
    }

    public void setClosedSet(Set<Node3D> closedSet) {
        this.closedSet = closedSet;
    }

    public int getHvCost() {
        return hvCost;
    }

    public void setHvCost(int hvCost) {
        this.hvCost = hvCost;
    }

    private int getDiagonalCost() {
        return diagonalCost;
    }

    private void setDiagonalCost(int diagonalCost) {
        this.diagonalCost = diagonalCost;
    }
}


