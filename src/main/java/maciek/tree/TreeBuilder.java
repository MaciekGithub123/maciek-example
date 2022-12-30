package maciek.tree;

import java.util.function.Consumer;

/**
 * A fluent tree builder.
 * <p>
 * Maintains a current node cursor to indicate the place where the tree changes
 * are applied.
 */
public class TreeBuilder<S extends TreeNodeSemantics<S>> {

	/**
	 * The tree node factory used internally by this builder.
	 */
	private final TreeNodeFactory.Mutable<S> nF = MutableTree.nodeFactory();

	/**
	 * The tree mapper used internally by this builder.
	 */
	private final TreeMapper<MutableTree<S>, MutableTreeNode<S>, S> mapper = MutableTree.mapper();
	
	/**
	 * The tree snapshots.
	 */
	private final TreeSnapshots<S> treeSnapshots;
	
	/**
	 * The tree being built by this builder.
	 */
	private MutableTree<S> treeUnderConstruction;

	/**
	 * The current tree location where the tree changes are applied.
	 */
	private TreeCursor<S> cursor;

	/**
	 * Creates the builder with empty tree structure.
	 * <p>
	 * The building must start with invoking {@link #root(TreeNodeSemantics)};
	 */
	public TreeBuilder() {
		treeSnapshots = TreeSnapshots.empty();
	}

	/**
	 * Creates the builder containing the tree structure.
	 */
	TreeBuilder(Tree<?, ?, S> tree) {
		cursor = new TreeCursor<>(mapper.map(tree).root());
		treeSnapshots = tree.treeSnapshots();
	}

	/**
	 * Creates a root of the tree.
	 */
	public TreeBuilder<S> root(S semantics) {
		cursor = new TreeCursor<>(nF.createNode(semantics));
		return this;
	}
	
	/**
	 * Adds tree node to giventree location, if location exists.
	 */
	public TreeBuilder<S> addNode(TreeLocation<S> location) {
		return null;
	}

	/**
	 * Creates a new node as a current node's last child.
	 */
	public TreeBuilder<S> addChild(S semantics) {
		cursor.get().parent().addChild(nF.createNode(semantics));
		return this;
	}

	/**
	 * Creates a new node as a current node's child at given index.
	 */
	public TreeBuilder<S> addChild(S semantics, int idx) {
		cursor.get().parent().addChild(nF.createNode(semantics), idx);
		return this;
	}

	/**
	 * Adds the tree as a current node's last child.
	 */
	public TreeBuilder<S> addChild(ImmutableTree<S> tree) {
		cursor.get().parent().addChild(mapper.map(tree).root());
		return this;
	}

	/**
	 * Adds the tree as a current node's child at given index.
	 */
	public TreeBuilder<S> addChild(ImmutableTree<S> tree, int idx) {
		cursor.get().parent().addChild(mapper.map(tree).root(), idx);
		return this;
	}

	/**
	 * Adds the tree as a current node's child at given index.
	 */
	public TreeBuilder<S> addLeftSibling(S semantics) {
		cursor.get().parent().addChild(nF.createNode(semantics), cursor.get().childIndex());
		return this;
	}

	/**
	 * Adds the tree as the current node left sibling.
	 */
	public TreeBuilder<S> addLeftSibling(ImmutableTree<S> tree) {
		cursor.get().parent().addChild(mapper.map(tree).root(), cursor.get().childIndex());
		return this;
	}

	/**
	 * Creates a new node as the current node right sibling.
	 */
	public TreeBuilder<S> addRightSibling(S semantics) {
		cursor.get().parent().addChild(nF.createNode(semantics), cursor.get().childIndex() + 1);
		return this;
	}

	/**
	 * Adds the tree as the current node right sibling.
	 */
	public TreeBuilder<S> addRightSibling(ImmutableTree<S> tree) {
		cursor.get().parent().addChild(mapper.map(tree).root(), cursor.get().childIndex() + 1);
		return this;
	}

	/**
	 * Removes the current node subtree.
	 * <p>
	 * Moves the cursor to the removed subtree previous parent.
	 */
	public TreeBuilder<S> removeSubtree() {
		moveCursor(c -> c.parent());
		cursor.getPrev().setParent(null, -1);
		return this;
	}

	/**
	 * Inserts a node between current node and its parent.
	 */
	public TreeBuilder<S> insertParent(S semantics) {
		var prevP = cursor.get().parent();
		var p = nF.createNode(semantics);

		p.addChild(cursor.get());
		prevP.addChild(p);

		return this;
	}

	/**
	 * Moves the cursor.
	 */
	public TreeBuilder<S> moveCursor(Consumer<TreeCursor<?, ?>> moveCursor) {
		moveCursor.accept(cursor);
		return this;
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
	 * <p>
	 * Takes the snapshot of the build tree.
	 * 
	 * @param <T> build tree type
	 * @param <N> build node type
	 */
	public <T2 extends Tree<T2, N2, S>, N2 extends TreeNode<N2, S>> T2 build(TreeMapper<T2, N2, S> mapper) {
		return mapper.map(cursor.get().root(), treeSnapshots);
	}

}
