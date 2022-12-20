package maciek.example;

import java.util.Arrays;

/**
 * A fluent tree builder.
 * <p>
 * Maintains a current node cursor to indicate the place where the building continues.
 * 
 * @param <S> semantics type
 */
public class TreeBuilder<S extends TreeNodeSemantics<S>> implements TreeTransformation<S> {

	/**
	 * The tree node factory used internally by this builder.
	 */
	private final TreeNodeFactory.Mutable<S> nF = MutableTree.nodeFactory();

	/**
	 * The tree mapper used internally by this builder.
	 */
	private final TreeMapper<MutableTree<S>, MutableTreeNode<S>, S> mapper = MutableTree.mapper();

	/**
	 * The node indicated by the cursor.
	 */
	private MutableTreeNode<S> currentNode;

	/**
	 * The snapshots of the tree under transformation.
	 */
	private TreeSnapshots<S> treeSnapshots;

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
		currentNode = mapper.map(tree).root();

		treeSnapshots = tree.treeSnapshots()
				.copy()
				.takeSnapshot(tree);
	}

	/**
	 * Creates the builder and start building a tree from given root.
	 */
	public static final <S extends TreeNodeSemantics<S>> TreeBuilder<S> tree(S semantics) {
		return new TreeBuilder<S>().root(semantics);
	}

	// modify

	@Override
	public TreeBuilder<S> root(S semantics) {
		return setCursor(nF.createNode(semantics));
	}

	@Override
	public TreeBuilder<S> addChild(S semantics) {
		return addChild(nF.createNode(semantics));
	}

	@Override
	public TreeBuilder<S> addChild(S semantics, int idx) {
		return addChild(nF.createNode(semantics), idx);
	}

	@Override
	public TreeTransformation<S> addChild(ImmutableTree<S> tree) {
		return addChild(mapper.map(tree).root());
	}

	@Override
	public TreeTransformation<S> addChild(ImmutableTree<S> tree, int idx) {
		return addChild(mapper.map(tree).root());
	}

	@Override
	public TreeTransformation<S> addLeftSibling(S semantics) {
		int idx = getCurrentNodeChildIndex();
		goToParent();
		return addChild(semantics, idx);
	}

	@Override
	public TreeTransformation<S> addLeftSibling(ImmutableTree<S> tree) {
		int idx = getCurrentNodeChildIndex();
		goToParent();
		return addChild(tree, idx);
	}

	@Override
	public TreeTransformation<S> addRightSibling(S semantics) {
		int idx = getCurrentNodeChildIndex();
		goToParent();
		return addChild(semantics, --idx);
	}

	@Override
	public TreeTransformation<S> addRightSibling(ImmutableTree<S> tree) {
		int idx = getCurrentNodeChildIndex();
		goToParent();
		return addChild(tree, ++idx);
	}

	@Override
	public TreeBuilder<S> removeSubtree() {
		var p = currentNode.parent();
		currentNode.setParent(null, -1);
		return setCursor(p);
	}

	@Override
	public TreeBuilder<S> insertParent(S semantics) {
		var prevP = currentNode.parent();
		var p = nF.createNode(semantics);

		p.addChild(currentNode);
		prevP.addChild(p);

		return this;
	}

	@Override
	public TreeTransformation<S> move(TreePath relativePath) {
		relativePath.followFrom(currentNode).addChild(currentNode);
		return this;
	}

	@Override
	public TreeTransformation<S> move(TreePath relativePath, int idx) {
		relativePath.followFrom(currentNode).addChild(currentNode, idx);
		return this;
	}
	
	@Override
	public TreeTransformation<S> moveTo(TreePath absolutePath) {
		absolutePath.followFrom(currentNode.root()).addChild(currentNode);
		return this;
	}

	@Override
	public TreeTransformation<S> moveTo(TreePath absolutePath, int idx) {
		absolutePath.followFrom(currentNode.root()).addChild(currentNode, idx);
		return this;
	}

	// build

	@Override
	public ImmutableTree<S> build() {
		return build(ImmutableTree.mapper());
	}

	@Override
	public MutableTree<S> buildMutable() {
		return build(mapper);
	}

	@Override
	public <T2 extends Tree<T2, N2, S>, N2 extends TreeNode<N2, S>> T2 build(TreeMapper<T2, N2, S> mapper) {
		return mapper.map(getCursor().root(), treeSnapshots);
	}

	// cursor

	@Override
	public MutableTreeNode<S> getCursor() {
		return currentNode;
	}

	@Override
	public TreeTransformation<S> goTo(TreePath path) {
		return setCursor(path.followFrom(getCursor()));
	}

	@Override
	public TreeTransformation<S> goTo(Integer... pathSteps) {
		return goTo(new TreePath(Arrays.asList(pathSteps)));
	}

	@Override
	public TreeTransformation<S> goToRoot() {
		return setCursor(getCursor().root());
	}

	@Override
	public TreeTransformation<S> goToParent() {
		return setCursor(getCursor().parent());
	}

	@Override
	public TreeTransformation<S> goToFirstChild() {
		return setCursor(getCursor().children().get(0));
	}

	@Override
	public TreeTransformation<S> goToLastChild() {
		return setCursor(getCursor().children().get(getCursor().children().size() - 1));
	}

	@Override
	public TreeTransformation<S> goLeft() {
		return setCursor(getCursor().leftSibling());
	}

	@Override
	public TreeTransformation<S> goRight() {
		return setCursor(getCursor().rightSibling());
	}

	// private

	/**
	 * Sets the cursor to given node.
	 */
	private TreeBuilder<S> setCursor(MutableTreeNode<S> n) {
		currentNode = n;
		return this;
	}

	/**
	 * Adds the node as a current node's child.
	 */
	private TreeBuilder<S> addChild(MutableTreeNode<S> n) {
		currentNode.addChild(n);
		return this;
	}

	/**
	 * Adds the node as a current node's child at given index.
	 */
	private TreeBuilder<S> addChild(MutableTreeNode<S> n, int idx) {
		currentNode.addChild(n, idx);
		return this;
	}

	/**
	 * Gets the current node child index.
	 */
	private int getCurrentNodeChildIndex() {
		return getCursor().parent().children().indexOf(getCursor());
	}



}
