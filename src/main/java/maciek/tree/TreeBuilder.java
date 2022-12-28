package maciek.tree;

/**
 * A fluent tree builder.
 * <p>
 * Maintains a current node cursor to indicate the place where the tree changes
 * are applied.
 * <p>
 * If not stated otherwise the cursor stays at the present node if not moved.
 */
public class TreeBuilder<S extends TreeNodeSemantics<S>> extends AbstractTreeBuilder<TreeBuilder<S>, S> {
	
	/**
	 * Creates the builder with empty tree structure.
	 * <p>
	 * The building must start from invoking {@link #root(TreeNodeSemantics)};
	 */
	public TreeBuilder() {
		super();
	}

	/**
	 * Creates the builder containing the tree structure.
	 */
	public TreeBuilder(Tree<?, ?, S> tree) {
		super(tree);
	}

	@Override
	protected TreeBuilder<S> getThis() {
		return this;
	}

}
