package maciek.tree;

/**
 * Defines a location in the tree that can be indicated by an absolute tree path.
 */
public interface TreeLocation<S extends TreeNodeSemantics<S>> {

	/**
	 * The absolute tree path for the location.
	 * <p>
	 * Can be null if given location doesn't exist in the tree.
	 */
	AbsoluteTreePath get(Tree<?, ?, S> tree);

}
