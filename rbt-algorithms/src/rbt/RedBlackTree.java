package rbt;

public class RedBlackTree {

	public Node root;
	public Node nill;

	public RedBlackTree() {
		root = null;
		nill = null;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	void leftRotate(Node node)

	{
		Node node1 = node.rightChild;
		node.rightChild = node1.leftChild;
		if (node1.leftChild != nill) {
			node1.leftChild.baseNode = node;
		}
		node1.baseNode = node.baseNode;
		if (node.baseNode == null) {
			this.root = node1;
		} else if (node == node.baseNode.leftChild) {
			node.baseNode.leftChild = node1;
		} else {
			node.baseNode.rightChild = node1;
		}
		node1.leftChild = node;
		node.baseNode = node1;
	}

	void rightRotate(Node node) {
		Node node1 = node.leftChild;
		node.leftChild = node1.rightChild;
		if (node1.rightChild != nill) {
			node1.rightChild.baseNode = node;
		}
		node1.baseNode = node.baseNode;
		if (node.baseNode == null) {
			this.root = node1;
		} else if (node == node.baseNode.rightChild) {
			node.baseNode.rightChild = node1;
		} else {
			node.baseNode.leftChild = node1;
		}
		node1.rightChild = node;
		node.baseNode = node1;
	}

	public void insert(int nodeData) {
		Node node = new Node();
		node.baseNode = null;
		node.nodeData = nodeData;
		node.leftChild = nill;
		node.rightChild = nill;
		node.nodeColor = 1;
		Node node1 = null;
		Node node2 = this.root;

		while (node2 != nill) {
			node1 = node2;
			if (node.nodeData < node2.nodeData) {
				node2 = node2.leftChild;
			} else {
				node2 = node2.rightChild;
			}
		}

		node.baseNode = node1;
		if (node1 == null) {
			root = node;
		} else if (node.nodeData < node1.nodeData) {
			node1.leftChild = node;
		} else {
			node1.rightChild = node;
		}

		if (node.baseNode == null) {
			node.nodeColor = 0;
			return;
		}

		if (node.baseNode.baseNode == null) {
			return;
		}

		// Fix the tree
		Fixup(node);
	}

	private void Fixup(Node node) {
		Node uncle = null;

		while (node.baseNode.nodeColor == 1) {
			if (node.baseNode == node.baseNode.baseNode.rightChild) {
				uncle = node.baseNode.baseNode.leftChild; // uncle
				if (uncle == null) {
					uncle = new Node();
					uncle.nodeColor = 0;
				}
				if (uncle.nodeColor == 1) {
					uncle.nodeColor = 0;
					node.baseNode.nodeColor = 0;
					node.baseNode.baseNode.nodeColor = 1;
					node = node.baseNode.baseNode;
				} else {
					if (node == node.baseNode.leftChild) {
						node = node.baseNode;
						rightRotate(node);
					}
					node.baseNode.nodeColor = 0;
					node.baseNode.baseNode.nodeColor = 1;
					leftRotate(node.baseNode.baseNode);
				}
			} else {
				uncle = node.baseNode.baseNode.rightChild; // uncle

				if (uncle == null) {
					uncle = new Node();
					uncle.nodeColor = 0;
				}
				if (uncle.nodeColor == 1) {
					uncle.nodeColor = 0;
					node.baseNode.nodeColor = 0;
					node.baseNode.baseNode.nodeColor = 1;
					node = node.baseNode.baseNode;
				} else {
					if (node == node.baseNode.rightChild) {
						node = node.baseNode;
						leftRotate(node);
					}
					node.baseNode.nodeColor = 0;
					node.baseNode.baseNode.nodeColor = 1;
					rightRotate(node.baseNode.baseNode);
				}
			}
			if (node == root) {
				break;
			}
		}
		root.nodeColor = 0;
	}

	// ******************************************************

	private void fixDelete(Node x) {
		Node s;
		while (x != root && x.nodeColor == 0) {
			if (x == x.baseNode.leftChild) {
				s = x.baseNode.rightChild;
				if (s.nodeColor == 1) {
					// case 3.1
					s.nodeColor = 0;
					x.baseNode.nodeColor = 1;
					leftRotate(x.baseNode);
					s = x.baseNode.rightChild;
				}

				if (s.leftChild.nodeColor == 0 && s.rightChild.nodeColor == 0) {
					// case 3.2
					s.nodeColor = 1;
					x = x.baseNode;
				} else {
					if (s.rightChild.nodeColor == 0) {
						// case 3.3
						s.leftChild.nodeColor = 0;
						s.nodeColor = 1;
						rightRotate(s);
						s = x.baseNode.rightChild;
					}

					// case 3.4
					s.nodeColor = x.baseNode.nodeColor;
					x.baseNode.nodeColor = 0;
					s.rightChild.nodeColor = 0;
					leftRotate(x.baseNode);
					x = root;
				}
			} else {
				s = x.baseNode.leftChild;
				if (s.nodeColor == 1) {
					// case 3.1
					s.nodeColor = 0;
					x.baseNode.nodeColor = 1;
					rightRotate(x.baseNode);
					s = x.baseNode.leftChild;
				}

				if (s.rightChild.nodeColor == 0 && s.rightChild.nodeColor == 0) {
					// case 3.2
					s.nodeColor = 1;
					x = x.baseNode;
				} else {
					if (s.leftChild.nodeColor == 0) {
						// case 3.3
						s.rightChild.nodeColor = 0;
						s.nodeColor = 1;
						leftRotate(s);
						s = x.baseNode.leftChild;
					}

					// case 3.4
					s.nodeColor = x.baseNode.nodeColor;
					x.baseNode.nodeColor = 0;
					s.leftChild.nodeColor = 0;
					rightRotate(x.baseNode);
					x = root;
				}
			}
		}
		x.nodeColor = 0;
	}

	public Node minimum(Node node) {

		while (node.leftChild != nill) {
			node = node.leftChild;
		}
		return node;
	}

	private void rbTransfer(Node u, Node v) {
		if (u.baseNode == null) {
			root = v;
		} else if (u == u.baseNode.leftChild) {
			u.baseNode.leftChild = v;
		} else {
			u.baseNode.rightChild = v;
		}
		v.baseNode = u.baseNode;
	}

	private void deleteNodeHelper(Node node, int nodeData) {
		// find the node containing key
		Node z = nill;
		Node x, y;
		while (node != nill){
			if (node.nodeData == nodeData) {
				z = node;
			}else if (node.nodeData < nodeData) {
				node = node.rightChild;
			} else {
				node = node.leftChild;
			}
		}

		if (z == nill) {
			System.out.println("Couldn't find key in the tree");
			return;
		} 

		y = z;
		int yOriginalColor = y.nodeColor;	
		if (z.leftChild == nill) {
			x = z.rightChild;
			rbTransfer(z, z.rightChild);
		} else if (z.rightChild == nill) {
			x = z.leftChild;
			rbTransfer(z, z.leftChild);
		} else {
			y = minimum(z.rightChild);
			yOriginalColor = y.nodeColor;
			x = y.rightChild;
			if (y.baseNode == z) {
				x.baseNode = y;
			} else {
				rbTransfer(y, y.rightChild);
				y.rightChild = z.rightChild;
				y.rightChild.baseNode = y;
			}

			rbTransfer(z, y);
			y.leftChild = z.leftChild;
			y.leftChild.baseNode = y;
			y.nodeColor = z.nodeColor;
		}
		if (yOriginalColor == 0){
			fixDelete(x);
		}
	}

	public void remove(int nodeData) {

		//removeNode(this.root, nodeData);
	}
	
	public void deleteNode(int data) {
		deleteNodeHelper(this.root, data);
	}
	public void post() {
		Node node = root;
		postOrderHelper(node);
	}

	void postOrderHelper(Node node) {

		if (node != nill) {
			postOrderHelper(node.leftChild);
			postOrderHelper(node.rightChild);
			System.out.println(node.nodeData + " ");
		}
	}

	public int getDepth() {
		return this.getDepth(this.root);
	}

	private int getDepth(Node node) {
		if (node != null) {
			int right_depth;
			int left_depth = this.getDepth(node.getLeftChild());
			return left_depth > (right_depth = this.getDepth(node.getRightChild())) ? left_depth + 1 : right_depth + 1;
		}
		return 0;
	}

	public void clear() {
		root = null;
	}
}
