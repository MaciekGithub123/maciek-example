package maciek.tree;

/**
 * Modifies the tree.
 */
public interface TreeModification<S extends TreeNodeSemantics<S>> {

	/**
	 * Modifies the tree.
	 */
	void transform(MutableTree<S> tree);

}
