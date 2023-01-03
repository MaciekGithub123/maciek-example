package maciek.tree;

/**
 * Transforms the tree into another tree.
 */
public interface TreeTransformation<S extends TreeNodeSemantics<S>> {

	/**
	 * Transforms the tree into another tree.
	 */
	ImmutableTree<S> transform(ImmutableTree<S> tree);

}
