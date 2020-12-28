package rbt;

import java.awt.Color;

public class Node {

	public Node baseNode;
	public Node rightChild;
	public Node leftChild;
	public int nodeColor;
	public int nodeData;

	public Node getBaseNode() {
		return baseNode;
	}

	public void setBaseNode(Node baseNode) {
		this.baseNode = baseNode;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	public int getNodeColor() {
		return nodeColor;
	}

	public void setNodeColor(int nodeColor) {
		this.nodeColor = nodeColor;
	}

	public int getNodeData() {
		return nodeData;
	}

	public void setNodeData(int nodeData) {
		this.nodeData = nodeData;
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
