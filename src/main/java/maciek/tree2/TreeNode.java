package maciek.tree2;

/**
 * A basic element in tree structure. The node has semantics assigned.
 * 
 * @param <N> the node type
 * @param <S> the semantics type
 */
public interface TreeNode<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> extends WithTreeNodeRelatives<N, S>{
	
	/**
	 * Gets node determined by given relative path starting from this node.
	 */
	N get(TreePath path);
	
	N copy();
	
//
//	/**
//	 * The subtree containing this node and its descendants.
//	 */
//	ImmutableTree<S> subtree();

}
