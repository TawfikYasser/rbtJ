package rbt;

import rbt.Node;

public class RedBlackTree {

	// Root node
	private Node root;
	// nill node of the tree node
	private Node nill;

	// Constructor
	public RedBlackTree() {
		nill = new Node();
		nill.nodeColor = 0;
		nill.leftChild = null;
		nill.rightChild = null;
		root = nill;
	}

	// Function to insert a new node to the red black tree
	public void insert(int key) {
		Node node = new Node();
		node.baseNode = null;
		node.nodeData = key;
		node.leftChild = nill;
		node.rightChild = nill;
		node.nodeColor = 1; // 1 For red

		Node y = null;
		Node x = this.root;

		while (x != nill) {
			y = x;
			if (node.nodeData < x.nodeData) {
				x = x.leftChild;
			} else {
				x = x.rightChild;
			}
		}

		node.baseNode = y;
		if (y == null) {
			root = node;
		} else if (node.nodeData < y.nodeData) {
			y.leftChild = node;
		} else {
			y.rightChild = node;
		}

		if (node.baseNode == null) {
			node.nodeColor = 0;
			return;
		}
		if (node.baseNode.baseNode == null) {
			return;
		}
		fixInsert(node);
	}

	// Clear the node in GUI
	public void clear() {
		root = null;
	}

	// delete the node from the tree
	public void removeNode(int nodeData) {
		mainRemoveNode(this.root, nodeData);
	}

	// This function to fix the colors and places of rbt after delete one
	private void rearrangingNodes(Node x) {
		Node s;
		while (x != root && x.nodeColor == 0) {
			if (x == x.baseNode.leftChild) {
				s = x.baseNode.rightChild;
				if (s.nodeColor == 1) {
					s.nodeColor = 0;
					x.baseNode.nodeColor = 1;
					leftChildRotate(x.baseNode);
					s = x.baseNode.rightChild;
				}

				if (s.leftChild.nodeColor == 0 && s.rightChild.nodeColor == 0) {
					s.nodeColor = 1;
					x = x.baseNode;
				} else {
					if (s.rightChild.nodeColor == 0) {
						s.leftChild.nodeColor = 0;
						s.nodeColor = 1;
						rightChildRotate(s);
						s = x.baseNode.rightChild;
					}
					s.nodeColor = x.baseNode.nodeColor;
					x.baseNode.nodeColor = 0;
					s.rightChild.nodeColor = 0;
					leftChildRotate(x.baseNode);
					x = root;
				}
			} else {
				s = x.baseNode.leftChild;
				if (s.nodeColor == 1) {
					s.nodeColor = 0;
					x.baseNode.nodeColor = 1;
					rightChildRotate(x.baseNode);
					s = x.baseNode.leftChild;
				}

				if (s.rightChild.nodeColor == 0 && s.rightChild.nodeColor == 0) {
					s.nodeColor = 1;
					x = x.baseNode;
				} else {
					if (s.leftChild.nodeColor == 0) {
						s.rightChild.nodeColor = 0;
						s.nodeColor = 1;
						leftChildRotate(s);
						s = x.baseNode.leftChild;
					}
					s.nodeColor = x.baseNode.nodeColor;
					x.baseNode.nodeColor = 0;
					s.leftChild.nodeColor = 0;
					rightChildRotate(x.baseNode);
					x = root;
				}
			}
		}
		x.nodeColor = 0;
	}

	private void rbtTransform(Node u, Node v) {
		if (u.baseNode == null) {
			root = v;
		} else if (u == u.baseNode.leftChild) {
			u.baseNode.leftChild = v;
		} else {
			u.baseNode.rightChild = v;
		}
		v.baseNode = u.baseNode;
	}

	private void mainRemoveNode(Node node, int key) {
		// find the node containing node data
		Node c = nill;
		Node a, b;
		while (node != nill) {
			if (node.nodeData == key) {
				c = node;
			}

			if (node.nodeData <= key) {
				node = node.rightChild;
			} else {
				node = node.leftChild;
			}
		}

		if (c == nill) {
			System.out.println("Invalid node value");
			return;
		}

		b = c;
		int bNodeColor = b.nodeColor;
		if (c.leftChild == nill) {
			a = c.rightChild;
			rbtTransform(c, c.rightChild);
		} else if (c.rightChild == nill) {
			a = c.leftChild;
			rbtTransform(c, c.leftChild);
		} else {
			b = minimum(c.rightChild);
			bNodeColor = b.nodeColor;
			a = b.rightChild;
			if (b.baseNode == c) {
				a.baseNode = b;
			} else {
				rbtTransform(b, b.rightChild);
				b.rightChild = c.rightChild;
				b.rightChild.baseNode = b;
			}

			rbtTransform(c, b);
			b.leftChild = c.leftChild;
			b.leftChild.baseNode = b;
			b.nodeColor = c.nodeColor;
		}
		if (bNodeColor == 0) {
			rearrangingNodes(a);
		}
	}

	// Fix the red black tree after inserting new node
	private void fixInsert(Node k) {
		Node u;
		while (k.baseNode.nodeColor == 1) {
			if (k.baseNode == k.baseNode.baseNode.rightChild) {
				u = k.baseNode.baseNode.leftChild;
				if (u.nodeColor == 1) {
					u.nodeColor = 0;
					k.baseNode.nodeColor = 0;
					k.baseNode.baseNode.nodeColor = 1;
					k = k.baseNode.baseNode;
				} else {
					if (k == k.baseNode.leftChild) {
						k = k.baseNode;
						rightChildRotate(k);
					}
					k.baseNode.nodeColor = 0;
					k.baseNode.baseNode.nodeColor = 1;
					leftChildRotate(k.baseNode.baseNode);
				}
			} else {
				u = k.baseNode.baseNode.rightChild;

				if (u.nodeColor == 1) {
					u.nodeColor = 0;
					k.baseNode.nodeColor = 0;
					k.baseNode.baseNode.nodeColor = 1;
					k = k.baseNode.baseNode;
				} else {
					if (k == k.baseNode.rightChild) {
						k = k.baseNode;
						leftChildRotate(k);
					}
					k.baseNode.nodeColor = 0;
					k.baseNode.baseNode.nodeColor = 1;
					rightChildRotate(k.baseNode.baseNode);
				}
			}
			if (k == root) {
				break;
			}
		}
		root.nodeColor = 0;
	}

	// find the node with the minimum nodeData
	public Node minimum(Node node) {
		while (node.leftChild != nill) {
			node = node.leftChild;
		}
		return node;
	}

	// rotate leftChild
	public void leftChildRotate(Node x) {
		Node y = x.rightChild;
		x.rightChild = y.leftChild;
		if (y.leftChild != nill) {
			y.leftChild.baseNode = x;
		}
		y.baseNode = x.baseNode;
		if (x.baseNode == null) {
			this.root = y;
		} else if (x == x.baseNode.leftChild) {
			x.baseNode.leftChild = y;
		} else {
			x.baseNode.rightChild = y;
		}
		y.leftChild = x;
		x.baseNode = y;
	}

	// rotate rightChild of a node
	public void rightChildRotate(Node x) {
		Node y = x.leftChild;
		x.leftChild = y.rightChild;
		if (y.rightChild != nill) {
			y.rightChild.baseNode = x;
		}
		y.baseNode = x.baseNode;
		if (x.baseNode == null) {
			this.root = y;
		} else if (x == x.baseNode.rightChild) {
			x.baseNode.rightChild = y;
		} else {
			x.baseNode.leftChild = y;
		}
		y.rightChild = x;
		x.baseNode = y;
	}

	/*
	 * 
	 * The following functions help with GUI
	 * 
	 */
	public Node getRoot() {
		return this.root;
	}

	public int getDepth() {
		return this.getDepth(this.root);
	}

	private int getDepth(Node node) {
		if (node != null) {
			int rightChild_depth;
			int leftChild_depth = this.getDepth(node.getLeftChild());
			return leftChild_depth > (rightChild_depth = this.getDepth(node.getRightChild())) ? leftChild_depth + 1
					: rightChild_depth + 1;
		}
		return 0;
	}

}
