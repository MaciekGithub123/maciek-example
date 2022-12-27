package maciek.tree;

/**
 * A movement of a tree node from the one location in the tree to the other.
 *
 * @param <N> the tree nodes type
 * @param <S> the tree nodes semantics type
 */
public class TreeNodeMovement<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> {
	
	private final TreeLocation<N, S> from;
	
	private final TreeLocation<N, S> to;
	
	/**
	 * Creates a tree node movement.
	 * 
	 * @param from the initial location of the tree node
	 * @param to the target location of the tree node
	 */
	public TreeNodeMovement(TreeLocation<N, S> from, TreeLocation<N, S> to) {
		this.from = from;
		this.to = to;
	}
	
	/**
	 * Moves the tree node between the locations in the tree.
	 */
	public void moveTreeNode(Tree<?, N, S> tree) {
		
		
	}

}
