package maciek.tree;

/**
 * A tree path step.
 * <p>
 * The tree path is made of steps. A step defines direct relation between nodes (parent/child/sibling etc.)
 */
interface TreePathStep {

	/**
	 * Gets the node this step away from given node.
	 */
	<<N extends TreeNode<N, S>, S extends TreeNodeSemantcis<S>> N getNextNode(N n);

	/**
	 * Creates step to the parent.
	 */
	public static TreePathStep parent() {
		return n -> n.parent();
	}

	/**
	 * Creates step to the child at index.
	 */
	public static TreePathStep child(int idx) {
		return n -> n.children().size() > idx ? n.children().get(idx) : null;

	}

	/**
	 * Creates step to the last child.
	 */
	public static TreePathStep lastChild() {
		return n -> n.children().isEmpty() ? null : n.children().get(n.children().size() - 1);
	}

}
