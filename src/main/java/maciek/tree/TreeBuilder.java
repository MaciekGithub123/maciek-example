package maciek.tree;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * A fluent tree builder.
 * <p>
 * Maintains a current node cursor to indicate the place where the building
 * continues.
 * <p>
 * If not stated otherwise the cursor stays at the present node if not moved.
 * 
 * @param <B> the builder implementation type
 * @param <S> the tree nodes semantics type
 */
public abstract class TreeBuilder<B extends TreeBuilder<B, S>, S extends TreeNodeSemantics<S>> {

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
	 * The node indicated by the cursor.
	 */
	private TreeCursor<MutableTreeNode<S>, S> cursor;

	/**
	 * Private constructor.
	 * <p>
	 * Use {@link fromRoot} instead.
	 */
	TreeBuilder() {
		treeSnapshots = TreeSnapshots.empty();
	}

	/**
	 * Creates the builder containing the tree structure.
	 */
	TreeBuilder(Tree<?, ?, S> tree) {
		cursor = new TreeCursor<>(mapper.map(tree).root());

		treeSnapshots = tree.treeSnapshots()
				.copy()
				.takeSnapshot(tree);
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
		return setCursor(nF.createNode(semantics));
	}

	/**
	 * Creates a new node as a current node's last child.
	 */
	public B addChild(S semantics) {
		return addChild(nF.createNode(semantics));
	}

	/**
	 * Creates a new node as a current node's child at given index.
	 */
	public B addChild(S semantics, int idx) {
		return addChild(nF.createNode(semantics), idx);
	}

	/**
	 * Adds the tree as a current node's last child.
	 */
	public B addChild(ImmutableTree<S> tree) {
		return addChild(mapper.map(tree).root());
	}

	/**
	 * Adds the tree as a current node's child at given index.
	 */
	public B addChild(ImmutableTree<S> tree, int idx) {
		return addChild(mapper.map(tree).root());
	}

	/**
	 * Adds the tree as a current node's child at given index.
	 */
	public B addLeftSibling(S semantics) {
		int idx = getCurrentNodeChildIndex();
		goToParent();
		return addChild(semantics, idx);
	}

	/**
	 * Adds the tree as the current node left sibling.
	 */
	public B addLeftSibling(ImmutableTree<S> tree) {
		int idx = getCurrentNodeChildIndex();
		goToParent();
		return addChild(tree, idx);
	}

	/**
	 * Creates a new node as the current node right sibling.
	 */
	public B addRightSibling(S semantics) {
		int idx = getCurrentNodeChildIndex();
		goToParent();
		return addChild(semantics, --idx);
	}

	/**
	 * Adds the tree as the current node right sibling.
	 */
	public B addRightSibling(ImmutableTree<S> tree) {
		int idx = getCurrentNodeChildIndex();
		goToParent();
		return addChild(tree, ++idx);
	}

	/**
	 * Removes the current node subtree.
	 * <p>
	 * Moves the cursor to the removed subtree previous parent.
	 */
	public B removeSubtree() {
		var p = cursor.parent();
		cursor.setParent(null, -1);
		return setCursor(p);
	}

	/**
	 * Inserts a node between current node and its parent.
	 */
	public B insertParent(S semantics) {
		var prevP = cursor.parent();
		var p = nF.createNode(semantics);

		p.addChild(cursor);
		prevP.addChild(p);

		return getThis();
	}

	/**
	 * Removes the current node subtree and adds it as a last child of the node at
	 * given relative path.
	 * <p>
	 * The cursor remains at the moved node.
	 */
	public B moveAlong(TreePath relativePath) {
		relativePath.followFrom(cursor).addChild(cursor);
		return getThis();
	}

	/**
	 * Removes the current node subtree and adds it as a child at given index of the
	 * node at given relative path.
	 * <p>
	 * The cursor remains at the moved node.
	 */
	public B moveAlong(TreePath relativePath, int idx) {
		relativePath.followFrom(cursor).addChild(cursor, idx);
		return getThis();
	}

	/**
	 * Removes the current node subtree and adds it as a last child of the node at given absolute path.
	 * <p>
	 * The cursor remains at the moved node.
	 */
	public B moveTo(TreePath absolutePath) {
		absolutePath.followFrom(cursor.root()).addChild(cursor);
		return getThis();
	}

	/**
	 * Removes the current node subtree and adds it as a child at given index of the node at given absolute path.
	 * <p>
	 * The cursor remains at the moved node.
	 */
	public B moveTo(TreePath absolutePath, int idx) {
		absolutePath.followFrom(cursor.root()).addChild(cursor, idx);
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
		return mapper.map(cursor.root(), treeSnapshots);
	}

	/**
	 * Moves  
	 */
	public B moveCursor(Consumer<TreeCursor<?, ?>> moveCursor) {
		moveCursor.accept(cursor);
		return getThis();
	}

	/**
	 * Gets the current node child index.
	 */
	private int getCurrentNodeChildIndex() {
		return cursor.parent().children().indexOf(cursor);
	}

}
