package maciek.example;

// TODO add(TreeTransformation) - optimization

// TODO replace child

// TODO rethink what you need here

/**
 * A fluent tree transformation.
 * 
 * @param <S> semantics type
 */
public interface TreeTransformation<S extends TreeNodeSemantics<S>> {

	// modify

	/**
	 * Creates a root of the tree.
	 * <p>
	 * Sets the cursor to the created node.
	 */
	TreeTransformation<S> root(S semantics);

	/**
	 * Creates a new node as a current node's last child.
	 * <p>
	 * The cursor stays at the parent node.
	 */
	TreeTransformation<S> addChild(S semantics);

	/**
	 * Creates a new node as a current node's child at given index.
	 * <p>
	 * The cursor stays at the parent node.
	 */
	TreeTransformation<S> addChild(S semantics, int idx);

	/**
	 * Adds the tree as a current node's last child.
	 * <p>
	 * The cursor stays at the parent node.
	 */
	TreeTransformation<S> addChild(ImmutableTree<S> tree);

	/**
	 * Adds the tree as a current node's child at given index.
	 * <p>
	 * The cursor stays at the parent node.
	 */
	TreeTransformation<S> addChild(ImmutableTree<S> tree, int idx);

	/**
	 * Creates a new node as the current node left sibling.
	 * <p>
	 * The cursor stays at the node.
	 */
	TreeTransformation<S> addLeftSibling(S semantics);

	/**
	 * Adds the tree as the current node left sibling.
	 * <p>
	 * The cursor stays at the node.
	 */
	TreeTransformation<S> addLeftSibling(ImmutableTree<S> tree);

	/**
	 * Creates a new node as the current node right sibling.
	 * <p>
	 * The cursor stays at the node.
	 */
	TreeTransformation<S> addRightSibling(S semantics);

	/**
	 * Adds the tree as the current node right sibling.
	 * <p>
	 * The cursor stays at the node.
	 */
	TreeTransformation<S> addRightSibling(ImmutableTree<S> tree);

	/**
	 * Removes the current node subtree.
	 * <p>
	 * Moves the cursor to the removed subtree previous parent.
	 */
	TreeTransformation<S> removeSubtree();
	
	/**
	 * Inserts a node between current node and its parent.
	 * <p>
	 * The cursor stays at the child node.
	 */
	TreeTransformation<S> insertParent(S semantics);

	/**
	 * Removes the current node subtree and adds it as a last child of the node at given relative path.
	 * <p>
	 * The cursor stays at the moved node.
	 */
	TreeTransformation<S> move(TreePath relativePath);

	/**
	 * Removes the current node subtree and adds it as a child at given index of the node at given relative path.
	 * <p>
	 * The cursor stays at the moved node.
	 */
	TreeTransformation<S> move(TreePath relativePath, int idx);
	
	/**
	 * Removes the current node subtree and adds it as a last child of the node at given absolute path.
	 * <p>
	 * The cursor stays at the moved node.
	 */
	TreeTransformation<S> moveTo(TreePath absolute);

	/**
	 * Removes the current node subtree and adds it as a child at given index of the node at given absolute path.
	 * <p>
	 * The cursor stays at the moved node.
	 */
	TreeTransformation<S> moveTo(TreePath absolute, int idx);
	
	// build
	
	/**
	 * Builds the immutable tree.
	 */
	ImmutableTree<S> build();
	
	/**
	 * Builds the mutable tree.
	 */
	MutableTree<S> buildMutable();

	/**
	 * Build the tree.
	 * 
	 * @param <T> build tree type
	 * @param <N> build node type
	 */
	<T extends Tree<T, N, S>, N extends TreeNode<N, S>> T build(TreeMapper<T, N, S> mapper);

	// cursor

	/**
	 * Gets the current node.
	 */
	TreeNode<?, S> getCursor();

	/**
	 * Moves the cursor along given path.
	 */
	TreeTransformation<S> goTo(TreePath path);

	/**
	 * Moves the cursor along given path.
	 */
	TreeTransformation<S> goTo(Integer... pathSteps);

	/**
	 * Moves the cursor to the root;
	 */
	TreeTransformation<S> goToRoot();

	/**
	 * Moves the cursor to the current node's parent.
	 */
	TreeTransformation<S> goToParent();

	/**
	 * Moves the cursor to the current node's first child.
	 */
	TreeTransformation<S> goToFirstChild();

	/**
	 * Moves the cursor to the current node's first child.
	 */
	TreeTransformation<S> goToLastChild();

	/**
	 * Moves the cursor to the current node left siblings.
	 */
	TreeTransformation<S> goLeft();

	/**
	 * Moves the cursor to the current node right siblings.
	 */
	TreeTransformation<S> goRight();

}
