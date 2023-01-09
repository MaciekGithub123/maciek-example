package maciek.tree;

// TODO
public class MoveTreeNode<S extends TreeNodeSemantics<S>> implements TreeModification<S> {

	/**
	 * The location of the node to be moved.
	 */
	private final TreeNodeQuery<S> srcLoc;

	/**
	 * The target location for the node.
	 */
	private final TreeNodeQuery<S> targetLoc;

	public MoveTreeNode(TreeNodeQuery<S> from, TreeNodeQuery<S> to) {
		this.srcLoc = from;
		this.targetLoc = to;
	}

	@Override
	public boolean modify(MutableTree<S> tree) {
		
		AbsoluteTreePath srcPath = srcLoc.query(tree);
		AbsoluteTreePath targetPath = targetLoc.query(tree);
		
		if (srcPath == null || targetPath == null) {
			return false;
		}

		tree.node(srcPath).setParent(tree.node(targetPath.parent()), targetPath.getIndexes().getLast());
		
		return true;
	}

}
