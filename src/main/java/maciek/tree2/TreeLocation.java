package maciek.tree2;

/**
 * A location of a node in the tree.
 * 
 * @param <N> the tree nodes type
 * @param <S> the node semantics type
 */
public interface TreeLocation<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> {

	/**
	 * Gets a node at the location in the tree.
	 */
	N get(Tree<?, N, S> tree);

}
