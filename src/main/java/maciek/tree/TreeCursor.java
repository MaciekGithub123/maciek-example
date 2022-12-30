package maciek.tree;

/**
 * A tree node pointer. Exposes method for traversing the tree.
 */
public interface TreeCursor<S extends TreeNodeSemantics<S>> extends TreeLocation<S> {

	/**
	 * The pointer along given relative path from its current location.
	 */
	TreeCursor<S> find(TreePath path);

	/**
	 * The pointer indicating the root of the tree.
	 */
	TreeCursor<S> root();

	/**
	 * The pointer indicating the parent of it current location.
	 */
	TreeCursor<S> parent();

	/**
	 * The pointer indicating the first child of it current location.
	 */
	TreeCursor<S> firstChild();

	/**
	 * The pointer indicating the last child of it current location.
	 */
	TreeCursor<S> lastChild();

	/**
	 * The pointer indicating the left sibling of it current location.
	 */
	TreeCursor<S> leftSibling();

	/**
	 * The pointer indicating the right sibling of it current location.
	 */
	TreeCursor<S> rightSibling();
	
}
