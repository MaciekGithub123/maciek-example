package maciek.tree2;

/**
 * A path in the tree.
 * <p>
 * The path may be relative, when starting from any node, or absolute when starting from the root.
 * <p>
 * The difference between path and {@link TreeLocation} is that a path is reversible.
 */
public interface TreePath {

	/**
	 * Follows the tree path starting from given node.
	 */
	<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> TreeNode<N, S> followFrom(TreeNode<N, S> node);
	
	/**
	 * The path that followed after this path leads to the starting node. 
	 */
	TreePath reverse();
	
}
