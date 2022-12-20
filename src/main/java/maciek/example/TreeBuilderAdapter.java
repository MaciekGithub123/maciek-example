package maciek.example;

import static maciek.example.TreeNodeSemanticsAdapter.val;

/**
 * Tree builder adapter for simple values that don't implement TreeNodeSemantics.
 * 
 * @see {@link TreeBuilder}
 */
public class TreeBuilderAdapter<V> {

	private TreeTransformation<TreeNodeSemanticsAdapter<V>> builder;

	// modify

	public TreeBuilderAdapter(TreeTransformation<TreeNodeSemanticsAdapter<V>> builder) {
		this.builder = builder;
	}

	/**
	 * Creates the builder and start building a tree from given root.
	 */
	public static final <V> TreeBuilderAdapter<V> adapt(TreeTransformation<TreeNodeSemanticsAdapter<V>> treeTransformation) {
		return new TreeBuilderAdapter<V>(treeTransformation);
	}

	/**
	 * Creates the builder and start building a tree from given root.
	 */
	public static final <V> TreeBuilderAdapter<V> tree(V semantics) {
		return new TreeBuilderAdapter<V>(new TreeBuilder<>()).root(semantics);
	}

	/**
	 * Creates a root of the tree.
	 * <p>
	 * Sets the cursor to the created node.
	 */
	public TreeBuilderAdapter<V> root(V semantics) {
		builder.root(val(semantics));
		return this;
	}

	/**
	 * Creates a new node as a current node's last child.
	 * <p>
	 * The cursor stays at the parent node.
	 */
	public TreeBuilderAdapter<V> addChild(V semantics) {
		builder.addChild(val(semantics));
		return null;
	}

	/**
	 * Creates a new node as a current node's child at given index.
	 * <p>
	 * The cursor stays at the parent node.
	 */
	public TreeBuilderAdapter<V> addChild(V semantics, int idx) {
		builder.addChild(val(semantics), idx);
		return null;
	}

	/**
	 * Adds the tree as a current node's last child.
	 * <p>
	 * The cursor stays at the parent node.
	 */
	public TreeBuilderAdapter<V> addChild(TreeBuilderAdapter<V> tree) {
		builder.addChild(tree.build());
		return this;
	}

	/**
	 * Adds the tree as a current node's child at given index.
	 * <p>
	 * The cursor stays at the parent node.
	 */
	public TreeBuilderAdapter<V> addChild(TreeBuilderAdapter<V> tree, int idx) {
		builder.addChild(tree.build(), idx);
		return this;
	}

	/**
	 * Creates a new node as a current node's first child.
	 * <p>
	 * The cursor stays at the parent node.
	 */
	public TreeBuilderAdapter<V> addFirstChild(V semantics) {
		builder.addChild(val(semantics));
		return this;
	}

	/**
	 * Adds the tree as a current node's first child.
	 * <p>
	 * The cursor stays at the parent node.
	 */
	public TreeBuilderAdapter<V> addFirstChild(TreeBuilderAdapter<V> tree) {
		builder.addChild(tree.build());
		return this;
	}

	/**
	 * Creates a new node as the current node left sibling.
	 * <p>
	 * The cursor stays at the node.
	 */
	public TreeBuilderAdapter<V> addLeftSibling(V semantics) {
		builder.addLeftSibling(val(semantics));
		return this;
	}

	/**
	 * Adds the tree as the current node left sibling.
	 * <p>
	 * The cursor stays at the node.
	 */
	public TreeBuilderAdapter<V> addLeftSibling(TreeBuilderAdapter<V> tree) {
		builder.addLeftSibling(tree.build());
		return this;
	}

	/**
	 * Creates a new node as the current node right sibling.
	 * <p>
	 * The cursor stays at the node.
	 */
	public TreeBuilderAdapter<V> addRightSibling(V semantics) {
		builder.addRightSibling(val(semantics));
		return this;
	}

	/**
	 * Adds the tree as the current node right sibling.
	 * <p>
	 * The cursor stays at the node.
	 */
	public TreeBuilderAdapter<V> addRightSibling(TreeBuilderAdapter<V> tree) {
		builder.addRightSibling(tree.build());
		return this;
	}

	/**
	 * Removes the current node subtree.
	 * <p>
	 * Moves the cursor to the removed subtree previous parent.
	 */
	public TreeBuilderAdapter<V> removeSubtree() {
		builder.removeSubtree();
		return this;
	}

	/**
	 * Inserts a node between current node and its parent.
	 * <p>
	 * The cursor stays at the child node.
	 */
	public TreeBuilderAdapter<V> insertParent(V semantics) {
		builder.insertParent(val(semantics));
		return this;
	}

	/**
	 * Removes the current node subtree and adds it as a last child of the node at given relative path.
	 * <p>
	 * The cursor stays at the moved node.
	 */
	public TreeBuilderAdapter<V> move(TreePath relativePath) {
		move(relativePath);
		return this;
	}

	/**
	 * Removes the current node subtree and adds it as a child at given index of the node at given relative path.
	 * <p>
	 * The cursor stays at the moved node.
	 */
	public TreeBuilderAdapter<V> move(TreePath relativePath, int idx) {
		move(relativePath, idx);
		return this;
	}
	
	/**
	 * Removes the current node subtree and adds it as a last child of the node at given absolute path.
	 * <p>
	 * The cursor stays at the moved node.
	 */
	public TreeBuilderAdapter<V> moveTo(TreePath absolutePath) {
		moveTo(absolutePath);
		return this;
	}

	/**
	 * Removes the current node subtree and adds it as a child at given index of the node at given absolute path.
	 * <p>
	 * The cursor stays at the moved node.
	 */
	public TreeBuilderAdapter<V> moveTo(TreePath absolutePath, int idx) {
		moveTo(absolutePath, idx);
		return this;
	}

	// build

	/**
	 * Builds the immutable tree.
	 */
	public ImmutableTree<TreeNodeSemanticsAdapter<V>> build() {
		return builder.build();
	}

	/**
	 * Builds the mutable tree.
	 */
	public MutableTree<TreeNodeSemanticsAdapter<V>> buildMutable() {
		return builder.buildMutable();
	}

	/**
	 * Build the tree.
	 * 
	 * @param <T> build tree type
	 * @param <N> build node type
	 */
	public <T extends Tree<T, N, TreeNodeSemanticsAdapter<V>>, N extends TreeNode<N, TreeNodeSemanticsAdapter<V>>> T build(
			TreeMapper<T, N, TreeNodeSemanticsAdapter<V>> mapper) {
		return builder.build(mapper);
	}

	// cursor

	/**
	 * Gets the current node.
	 */
	public TreeNode<?, TreeNodeSemanticsAdapter<V>> getCursor() {
		return builder.getCursor();
	}

	/**
	 * Moves the cursor along given path.
	 */
	public TreeBuilderAdapter<V> goTo(TreePath path) {
		goTo(path);
		return this;
	}

	/**
	 * Moves the cursor along given path.
	 */
	public TreeBuilderAdapter<V> goTo(Integer... pathSteps) {
		goTo(pathSteps);
		return this;
	}

	/**
	 * Moves the cursor to the root;
	 */
	public TreeBuilderAdapter<V> goToRoot() {
		goToRoot();
		return this;
	}

	/**
	 * Moves the cursor to the current node's parent.
	 */
	public TreeBuilderAdapter<V> goToParent() {
		goToParent();
		return this;
	}

	/**
	 * Moves the cursor to the current node's first child.
	 */
	public TreeBuilderAdapter<V> goToFirstChild() {
		goToFirstChild();
		return this;
	}

	/**
	 * Moves the cursor to the current node's first child.
	 */
	public TreeBuilderAdapter<V> goToLastChild() {
		goToLastChild();
		return this;
	}

	/**
	 * Moves the cursor to the current node left siblings.
	 */
	public TreeBuilderAdapter<V> goLeft() {
		goLeft();
		return this;
	}

	/**
	 * Moves the cursor to the current node right siblings.
	 */
	public TreeBuilderAdapter<V> goRight() {
		goRight();
		return this;
	}

}
