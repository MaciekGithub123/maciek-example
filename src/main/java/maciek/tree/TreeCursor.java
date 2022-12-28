package maciek.tree;

/**
 * A tree node pointer. A tree location defined by a tree node. Exposes method for traversing the tree.
 */
public class TreeCursor<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> implements TreeLocation<N, S> {

	/**
	 * The indicated tree node.
	 */
	private N currentNode;
	
	/**
	 * The tree node indicated immediately before the current node.
	 */
	private N prevNode;

	/**
	 * Creates the cursor indicating given node.
	 */
	public TreeCursor(N node) {
		this.currentNode = node;
	}

	/**
	 * Gets the indicated node.
	 */
	public N get() {
		return currentNode;
	}
	
	/**
	 * Gets the node indicated immediately before the current one.
	 */
	public N getPrev() {
		return prevNode;
	}

	/**
	 * Gets the tree location defined by the current node or maps the location to the new tree.
	 */
	@Override
	public N get(Tree<?, N, S> tree) {
		if (contains(tree, currentNode)) {
			return currentNode;
		}

		ImmutableTree<S> currentTree = currentNode.subtree();
		if (currentTree.equals(tree)) {
			N root = tree.root();
			currentNode = root.descendant(currentNode.pathFromRoot());
			prevNode = root.descendant(prevNode.pathFromRoot());
			return currentNode;
		}
		return null;
	}

	/**
	 * Moves the cursor along given relative path.
	 * 
	 * @return this, for method chaining
	 */
	public TreeCursor<N, S> goTo(TreePath path) {
		return goTo(path.followFrom(currentNode));
	}

	/**
	 * Moves the cursor to the root.
	 * 
	 * @return this, for method chaining
	 */
	public TreeCursor<N, S> goToRoot() {
		return goTo(currentNode.root());
	}

	/**
	 * Moves the cursor to the current node's parent.
	 * 
	 * @return this, for method chaining
	 */
	public TreeCursor<N, S> goToParent() {
		return goTo(currentNode.parent());
	}

	/**
	 * Moves the cursor to the current node's first child.
	 * 
	 * @return this, for method chaining
	 */
	public TreeCursor<N, S> goToFirstChild() {
		return goTo(currentNode.children().get(0));
	}

	/**
	 * Moves the cursor to the current node's first child.
	 * 
	 * @return this, for method chaining
	 */
	public TreeCursor<N, S> goToLastChild() {
		return goTo(currentNode.children().get(currentNode.children().size() - 1));
	}

	/**
	 * Moves the cursor to the current node's first child.
	 * 
	 * @return this, for method chaining
	 */
	public TreeCursor<N, S> goLeft() {
		return goTo(currentNode.leftSibling());
	}

	/**
	 * Moves the cursor to the current node left siblings.
	 */
	public TreeCursor<N, S> goRight() {
		return goTo(currentNode.rightSibling());
	}
	
	/**
	 * Moves the cursor to the tree node.
	 */
	public TreeCursor<N, S> goTo(N n) {
		prevNode = currentNode;
		currentNode = n;
		return this;
	}

	/**
	 * Checks whether the tree contains the tree node.
	 */
	private boolean contains(Tree<?, N, S> tree, N treeNode) {
		return tree.root() == treeNode.root();
	}

}
