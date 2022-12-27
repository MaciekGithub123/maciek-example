package maciek.tree;

/**
 * A tree with mutable tree structure.
 * <p>
 * The nodes of this tree can be added, removed or moved.
 * 
 * @param <S> the tree nodes semantics type
 */
public class MutableTree<S extends TreeNodeSemantics<S>> extends AbstractTree<MutableTree<S>, MutableTreeNode<S>, S> {
	
	/**
	 * Protected constructor.
	 */
	protected MutableTree(MutableTreeNode<S> root, TreeSnapshots<S> prevSnapshots) {
		super(root, prevSnapshots);
	}
	
	/**
	 * The factory for mutable node.
	 */
	public static final <S extends TreeNodeSemantics<S>> TreeNodeFactory.Mutable<S> nodeFactory() {
		return new TreeNodeFactory.Mutable<>();
	}

	/**
	 * The mapper to mutable tree.
	 */
	public static final <S extends TreeNodeSemantics<S>> TreeMapper<MutableTree<S>, MutableTreeNode<S>, S> mapper() {
		return new TreeMapper<MutableTree<S>, MutableTreeNode<S>, S>(MutableTree::new, nodeFactory());
	}

	@Override
	public MutableTree<S> copy() {
		return map(mapper());
	}

}