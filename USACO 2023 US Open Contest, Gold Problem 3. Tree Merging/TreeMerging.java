import java.util.*;
import java.io.*;


public class TreeMerging {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        for (int testCases = scanner.nextInt(); testCases > 0; testCases--) {
            int nodeCount = scanner.nextInt();
            int mainRoot = (nodeCount * (nodeCount + 1)) / 2;
            int[] previousParent = new int[nodeCount + 1];

            for (int edgeCount = nodeCount - 1; edgeCount > 0; edgeCount--) {
                int childNode = scanner.nextInt();
                int parentNode = scanner.nextInt();
                previousParent[childNode] = parentNode;
                mainRoot -= childNode;
            }

            int mergeCount = scanner.nextInt();
            int[] newParentMapping = new int[nodeCount + 1];
            boolean[] existingNodes = new boolean[nodeCount + 1];
            existingNodes[mainRoot] = true;

            for (int edgeCount = mergeCount - 1; edgeCount > 0; edgeCount--) {
                int childNode = scanner.nextInt();
                int parentNode = scanner.nextInt();
                newParentMapping[childNode] = parentNode;
                existingNodes[childNode] = true;
            }

            int[] nodeDepth = new int[nodeCount + 1];

            for (int iterationCount = nodeCount; iterationCount > 0; iterationCount--) {
                for (int currentNode = 1; currentNode <= nodeCount; currentNode++) {
                    if (currentNode != mainRoot) {
                        nodeDepth[currentNode] = nodeDepth[previousParent[currentNode]] + 1;
                    }
                }
            }

            boolean[][] mergePossibilities = new boolean[nodeCount + 1][nodeCount + 1];

            for (int depthLevel = nodeCount; depthLevel > 0; depthLevel--) {
                for (int currentNode = 1; currentNode <= nodeCount; currentNode++) {
                    if (nodeDepth[currentNode] == depthLevel) {
                        if (existingNodes[currentNode]) {
                            mergePossibilities[currentNode][currentNode] = true;
                        } else {
                            for (int potentialParent = currentNode; potentialParent <= nodeCount; potentialParent++) {
                                if (existingNodes[potentialParent]) {
                                    mergePossibilities[currentNode][potentialParent] = true;

                                    for (int child = 1; child <= nodeCount; child++) {
                                        if (previousParent[child] == currentNode) {
                                            boolean canMergeFlag = false;

                                            for (int newNode = 1; newNode <= nodeCount; newNode++) {
                                                if (newParentMapping[newNode] == potentialParent && mergePossibilities[child][newNode]) {
                                                    canMergeFlag = true;
                                                }
                                            }

                                            mergePossibilities[currentNode][potentialParent] &= canMergeFlag;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            System.out.println(nodeCount - mergeCount);
            int[] representatives = new int[nodeCount + 1];
            representatives[mainRoot] = mainRoot;

            for (int depthLevel = 1; depthLevel <= nodeCount; depthLevel++) {
                for (int currentNode = 1; currentNode <= nodeCount; currentNode++) {
                    if (nodeDepth[currentNode] == depthLevel) {
                        for (int potentialParent = 1; potentialParent <= nodeCount; potentialParent++) {
                            if (newParentMapping[potentialParent] == representatives[previousParent[currentNode]] && mergePossibilities[currentNode][potentialParent]) {
                                representatives[currentNode] = potentialParent;
                            }
                        }

                        if (representatives[currentNode] != currentNode) {
                            System.out.println(currentNode + " " + representatives[currentNode]);
                        }
                    }
                }
            }
        }

        scanner.close();
    }
}