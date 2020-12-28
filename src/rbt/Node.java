package rbt;

import java.awt.Color;

import rbt.Node;

public class Node {

	public int nodeData; // holds the key
	public Node baseNode; // pointer to the parent
	public Node leftChild; // pointer to left child
	public Node rightChild; // pointer to right child
	public int nodeColor; // 1 . Red, 0 . Black
	

	public int getNodeData() {
		return nodeData;
	}

	public void setNodeData(int nodeData) {
		this.nodeData = nodeData;
	}

	public Node getBaseNode() {
		return baseNode;
	}

	public void setBaseNode(Node baseNode) {
		this.baseNode = baseNode;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

	public int getNodeColor() {
		return nodeColor;
	}

	public void setNodeColor(int nodeColor) {
		this.nodeColor = nodeColor;
	}

	public Color getColorCode() {
		if (isRedColor())
			return new Color(250, 70, 70);
		else
			return new Color(70, 70, 70);

	}

	public boolean isRedColor() {
		return getNodeColor() == 1;
	}

	@Override
	public String toString() {
		return "" + nodeData;
	}

}
