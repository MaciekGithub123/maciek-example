package maciek.tree;

/**
 * A transformation of the node tree.
 */
public interface TreeTransformation<T extends Tree<T, N, S>, N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> {
	
	/**
	 * Transforms the tree.
	 */
	T transform(T tree);
	
}
