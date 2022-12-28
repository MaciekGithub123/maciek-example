package maciek.tree;

import java.util.function.Consumer;

/**
 * Abstract tree builder implementation.
 * 
 * @param <B> the builder implementation type for method chaining
 */
public abstract class AbstractTreeBuilder<B extends AbstractTreeBuilder<B, S>, S extends TreeNodeSemantics<S>> {

	/**
	 * The tree node factory used internally by this builder.
	 */
	private final TreeNodeFactory.Mutable<S> nF = MutableTree.nodeFactory();

	/**
	 * The tree mapper used internally by this builder.
	 */
	private final TreeMapper<MutableTree<S>, MutableTreeNode<S>, S> mapper = MutableTree.mapper();

	/**
	 * The previous snapshots of the tree under transformation.
	 */
	private final TreeSnapshots<S> treeSnapshots;

	/**
	 * The current node to which the changes are applied.
	 */
	private TreeCursor<MutableTreeNode<S>, S> cursor;

	/**
	 * Creates the builder with empty tree structure.
	 * <p>
	 * The building must start from invoking {@link #root(TreeNodeSemantics)};
	 */
	protected AbstractTreeBuilder() {
		treeSnapshots = TreeSnapshots.empty();
	}

	/**
	 * Creates the builder containing the tree structure.
	 */
	protected AbstractTreeBuilder(Tree<?, ?, S> tree) {
		cursor = new TreeCursor<>(mapper.map(tree).root());

		treeSnapshots = tree.treeSnapshots().copy().takeSnapshot(tree);
	}

	/**
	 * Gets this.
	 * <p>
	 * For method chaining.
	 */
	protected abstract B getThis();

	/**
	 * Creates a root of the tree.
	 */
	public B root(S semantics) {
		cursor = new TreeCursor<>(nF.createNode(semantics));
		return getThis();
	}

	/**
	 * Creates a new node as a current node's last child.
	 */
	public B addChild(S semantics) {
		cursor.get().parent().addChild(nF.createNode(semantics));
		return getThis();
	}

	/**
	 * Creates a new node as a current node's child at given index.
	 */
	public B addChild(S semantics, int idx) {
		cursor.get().parent().addChild(nF.createNode(semantics), idx);
		return getThis();
	}

	/**
	 * Adds the tree as a current node's last child.
	 */
	public B addChild(ImmutableTree<S> tree) {
		cursor.get().parent().addChild(mapper.map(tree).root());
		return getThis();
	}

	/**
	 * Adds the tree as a current node's child at given index.
	 */
	public B addChild(ImmutableTree<S> tree, int idx) {
		cursor.get().parent().addChild(mapper.map(tree).root(), idx);
		return getThis();
	}

	/**
	 * Adds the tree as a current node's child at given index.
	 */
	public B addLeftSibling(S semantics) {
		cursor.get().parent().addChild(nF.createNode(semantics), cursor.get().childIndex());
		return getThis();
	}

	/**
	 * Adds the tree as the current node left sibling.
	 */
	public B addLeftSibling(ImmutableTree<S> tree) {
		cursor.get().parent().addChild(mapper.map(tree).root(), cursor.get().childIndex());
		return getThis();
	}

	/**
	 * Creates a new node as the current node right sibling.
	 */
	public B addRightSibling(S semantics) {
		cursor.get().parent().addChild(nF.createNode(semantics), cursor.get().childIndex() + 1);
		return getThis();
	}

	/**
	 * Adds the tree as the current node right sibling.
	 */
	public B addRightSibling(ImmutableTree<S> tree) {
		cursor.get().parent().addChild(mapper.map(tree).root(), cursor.get().childIndex() + 1);
		return getThis();
	}

	/**
	 * Removes the current node subtree.
	 * <p>
	 * Moves the cursor to the removed subtree previous parent.
	 */
	public B removeSubtree() {
		moveCursor(c -> c.goToParent());
		cursor.getPrev().setParent(null, -1);
		return getThis();
	}

	/**
	 * Inserts a node between current node and its parent.
	 */
	public B insertParent(S semantics) {
		var prevP = cursor.get().parent();
		var p = nF.createNode(semantics);

		p.addChild(cursor.get());
		prevP.addChild(p);

		return getThis();
	}
	
	/**
	 * Moves the cursor.
	 */
	public B moveCursor(Consumer<TreeCursor<?, ?>> moveCursor) {
		moveCursor.accept(cursor);
		return getThis();
	}

	/**
	 * Builds the immutable tree.
	 */
	public ImmutableTree<S> build() {
		return build(ImmutableTree.mapper());
	}

	/**
	 * Builds the mutable tree.
	 */
	public MutableTree<S> buildMutable() {
		return build(mapper);
	}

	/**
	 * Build the tree.
	 * 
	 * @param <T> build tree type
	 * @param <N> build node type
	 */
	public <T2 extends Tree<T2, N2, S>, N2 extends TreeNode<N2, S>> T2 build(TreeMapper<T2, N2, S> mapper) {
		return mapper.map(cursor.get().root(), treeSnapshots);
	}
	

}
