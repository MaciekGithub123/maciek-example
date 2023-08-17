package maciek.tree2;

public interface WithTreeNodeRelatives<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> {
	
	/**
	 * This node's parent.
	 */
	default N parent() {
		return null;
	}

	/**
	 * This node's left sibling.
	 */
	default N leftSibling();

	/**
	 * This node's right sibling.
	 */
	default N rightSibling();

	/**
	 * This node's children list copy.
	 */
	default List<? extends N> children();

	/**
	 * The semantic of this node.
	 */
	default S semantics();

	/**
	 * The root of the tree this node belongs to.
	 */
	default N root();

	/**
	 * The descendant of this node localized by the relative path.
	 */
	default N descendant(TreePath relativePath);

	/**
	 * The node's descendants.
	 */
	default List<? extends N> descendants();

	/**
	 * The path to this node from the root.
	 */
	default TreePath pathFromRoot();

	// height?
	/**
	 * The distance between this node and the root.
	 */
	default int depth();

}
