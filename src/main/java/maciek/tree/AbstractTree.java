package maciek.tree;

/**
 * The abstract Tree implementation.
 */
public abstract class AbstractTree<T extends AbstractTree<T, N, S>, N extends AbstractTreeNode<N, S>, S extends TreeNodeSemantics<S>>
		implements Tree<T, N, S> {

	/**
	 * The previous versions.
	 */
	private final TreeSnapshots<S> treeSnapshots;

	/**
	 * The root.
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
	public N node(AbsoluteTreePath path) {
		return path.get(this);
	}

	@Override
	public TreeSnapshots<S> treeSnapshots() {
		return treeSnapshots;
	}

}
