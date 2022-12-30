package maciek.tree;

/**
 * Defines a location in the tree.
 * <p>
 * If location is present in the tree it produces absolute path.
 * <p>
 * The location can point to a node or to an empty place.
 */
public interface TreeLocation<S extends TreeNodeSemantics<S>> {
	
	/**
	 * Gets absolute path of the location.
	 * <p>
	 * Can be null if given location doesn't exist in the tree. 
	 */
	AbsoluteTreePath<S> get(Tree<?, ?, S> tree);

}
