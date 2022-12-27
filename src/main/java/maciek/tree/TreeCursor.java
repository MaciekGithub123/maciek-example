package maciek.tree;

/**
 * Indicates a node in the tree.
 *
 * @param <N> the tree node type
 * @param <S> the tree node semantics
 */
public class TreeCursor<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> implements TreeLocation<N, S> {

	/**
	 * The tree node indicated by this cursor.
	 */
	private N currentNode;
	
	/**
	 * Creates the cursor indicating given node.
	 */
	public TreeCursor(N node) {
		this.currentNode = node;
	}

	@Override
	public N get(Tree<?, N, S> tree) {
		return currentNode;
	}

	/**
	 * Moves the cursor along given relative path.
	 * 
	 * @return this, for method chaining
	 */
	public TreeCursor<N, S> goTo(TreePath path) {
		currentNode = path.followFrom(currentNode);
		return this;
	}

	/**
	 * Moves the cursor to the root.
	 * 
	 * @return this, for method chaining
	 */
	public TreeCursor<N, S> goToRoot() {
		currentNode = currentNode.root();
		return this;
	}

	/**
	 * Moves the cursor to the current node's parent.
	 * 
	 * @return this, for method chaining
	 */
	public TreeCursor<N, S> goToParent() {
		currentNode = currentNode.parent();
		return this;
	}

	/**
	 * Moves the cursor to the current node's first child.
	 * 
	 * @return this, for method chaining
	 */
	public TreeCursor<N, S> goToFirstChild() {
		currentNode = currentNode.children().get(0);
		return this;
	}

	/**
	 * Moves the cursor to the current node's first child.
	 * 
	 * @return this, for method chaining
	 */
	public TreeCursor<N, S> goToLastChild() {
		currentNode = currentNode.children().get(currentNode.children().size() - 1);
		return this;
	}

	/**
	 * Moves the cursor to the current node's first child.
	 * 
	 * @return this, for method chaining
	 */
	public TreeCursor<N, S> goLeft() {
		currentNode = currentNode.leftSibling();
		return this;
	}

	/**
	 * Moves the cursor to the current node left siblings.
	 */
	public TreeCursor<N, S> goRight() {
		currentNode = currentNode.rightSibling();
		return this;
	}

}
