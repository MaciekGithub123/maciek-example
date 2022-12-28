package maciek.tree;

/**
 * Defines a tree node location in the tree.
 */
public interface TreeLocation<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> {

	/**
	 * Gets from the tree a tree node at the tree location.
	 */
	N get(Tree<?, N, S> tree);

}
