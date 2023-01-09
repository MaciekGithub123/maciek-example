package maciek.tree;

/**
 * Modifies the tree.
 */
public interface TreeModification<S extends TreeNodeSemantics<S>> {

	/**
	 * Modifies the tree.
	 * 
	 * @return true if the tree was modified, false otherwise
	 */
	boolean modify(MutableTree<S> tree);

	/**
	 * Repeats this modification as long as the tree can be modified by it.
	 */
	default void modifyAll(MutableTree<S> tree) {
		while (true) {
			if (!modify(tree)) {
				return;
			}
		}
	}

}
