package maciek.tree;

/**
 * Abstract tree implementation.
 */
public abstract class AbstractTree<T extends Tree<T, N, S>, N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>>
		implements Tree<T, N, S> {

	/**
	 * The tree snapshots.
	 */
	private final TreeSnapshots<S> treeSnapshots;

	/**
	 * The tree root.
	 */
	private N root;
	
	/**
	 * Creates the tree and takes it snapshot.
	 * 
	 * @param treeSnapshots the tree history, copied on set
	 */
	protected AbstractTree(N root, TreeSnapshots<S> treeSnapshots) {
		this.root = root;
		this.treeSnapshots = treeSnapshots.copyAndTakeSanpshot(this);
	}

	@Override
	public N root() {
		return root;
	}

	@Override
	public TreeSnapshots<S> treeSnapshots() {
		return treeSnapshots;
	}

}
