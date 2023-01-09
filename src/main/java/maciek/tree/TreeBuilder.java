package maciek.tree;

import java.util.List;

/**
 * A fluent tree builder.
 * <p>
 * Maintains a current node cursor to indicate the place where the changes to the tree are applied.
 */
public class TreeBuilder<S extends TreeNodeSemantics<S>> implements TreeNavigable<TreeBuilder<S>> {

	/**
	 * The tree node factory used internally by this builder.
	 */
	private final TreeNodeFactory.Mutable<S> nF = MutableTree.nodeFactory();

	/**
	 * The tree mapper used internally by this builder.
	 */
	private final TreeMapper<MutableTree<S>, MutableTreeNode<S>, S> mapper = MutableTree.mapper();

	/**
	 * The tree being built by this builder.
	 */
	private MutableTree<S> tree;

	/**
	 * The current tree location where the tree changes are applied.
	 */
	private TreeCursor<MutableTreeNode<S>, S> cursor;

	/**
	 * Creates the builder with empty tree structure.
	 * <p>
	 * The building must start with invoking {@link #root(TreeNodeSemantics)};
	 */
	public TreeBuilder() {
	}

	/**
	 * Creates the builder mapping the tree structure.
	 */
	TreeBuilder(Tree<?, ?, S> tree) {
		cursor = new TreeCursor<>(mapper.map(tree));
	}
	
	// structure

	/**
	 * Creates a root of the tree.
	 */
	public TreeBuilder<S> root(S semantics) {
		cursor = new TreeCursor<>(new MutableTree<>(nF.createNode(semantics), new TreeSnapshots<>(List.of())));
		return this;
	}

	/**
	 * Creates a new node as a current node's last child.
	 */
	public TreeBuilder<S> addChild(S semantics) {
		cursor.forCurrentNode(n -> n.parent().addChild(nF.createNode(semantics)));
		return this;
	}

	/**
	 * Creates a new node as a current node's child at given index.
	 */
	public TreeBuilder<S> addChild(S semantics, int idx) {
		cursor.forCurrentNode(n -> n.parent().addChild(nF.createNode(semantics), idx));
		return this;
	}

	/**
	 * Adds the tree as a current node's last child.
	 */
	public TreeBuilder<S> addChild(ImmutableTree<S> tree) {
		cursor.forCurrentNode(n -> n.parent().addChild(mapper.map(tree).root()));
		return this;
	}

	/**
	 * Adds the tree as a current node's child at given index.
	 */
	public TreeBuilder<S> addChild(ImmutableTree<S> tree, int idx) {
		cursor.forCurrentNode(n -> n.parent().addChild(mapper.map(tree).root(), idx));
		return this;
	}

	/**
	 * Adds the tree as a current node's last child.
	 */
	public TreeBuilder<S> addChild(TreeBuilder<S> tree) {
		return addChild(tree.build());
	}

	/**
	 * Adds the tree as a current node's child at given index.
	 */
	public TreeBuilder<S> addChild(TreeBuilder<S> tree, int idx) {
		return addChild(tree.build(), idx);
	}

	/**
	 * Adds the tree as a current node's child at given index.
	 */
	public TreeBuilder<S> addLeftSibling(S semantics) {
		cursor.forCurrentNode(n -> n.parent().addChild(nF.createNode(semantics), n.childIndex()));
		return this;
	}

	/**
	 * Adds the tree as the current node left sibling.
	 */
	public TreeBuilder<S> addLeftSibling(ImmutableTree<S> tree) {
		cursor.forCurrentNode(n -> n.parent().addChild(mapper.map(tree).root(), n.childIndex()));
		return this;
	}

	/**
	 * Adds the tree as the current node left sibling.
	 */
	public TreeBuilder<S> addLeftSibling(TreeBuilder<S> tree) {
		return addLeftSibling(tree.build());
	}

	/**
	 * Creates a new node as the current node right sibling.
	 */
	public TreeBuilder<S> addRightSibling(S semantics) {
		cursor.forCurrentNode(n -> n.parent().addChild(nF.createNode(semantics), n.childIndex() + 1));
		return this;
	}

	/**
	 * Adds the tree as the current node right sibling.
	 */
	public TreeBuilder<S> addRightSibling(ImmutableTree<S> tree) {
		cursor.forCurrentNode(n -> n.parent().addChild(mapper.map(tree).root(), n.childIndex() + 1));
		return this;
	}

	/**
	 * Adds the tree as the current node right sibling.
	 */
	public TreeBuilder<S> addRightSibling(TreeBuilder<S> tree) {
		return addRightSibling(tree);
	}

	/**
	 * Removes the current node's subtree.
	 */
	public TreeBuilder<S> removeSubtree() {
		cursor.forCurrentNode(n -> n.setParent(null, -1));
		return this;
	}
	
	// navigation
	
	@Override
	public AbsoluteTreePath path() {
		return cursor.path();
	}

	@Override
	public TreeBuilder<S> path(AbsoluteTreePath path) {
		cursor = cursor.path(path);
		return this;
	}

	@Override
	public TreeBuilder<S> lastChild() {
		cursor = cursor.lastChild();
		return this;
	}

	// building

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
		return mapper.map(cursor.getTree().root(), tree.treeSnapshots());
	}
	
}
