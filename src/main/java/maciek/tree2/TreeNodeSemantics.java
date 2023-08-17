package maciek.tree2;

/**
 * Semantic layer over a tree of nodes.
 * <p>
 * In its simplest form a node semantics may be an enum or single value.
 * Alternatively it may be complex object which is aware of its tree relations.
 * <p>
 * Semantics is immutable and always copy on get and set.
 */
public interface TreeNodeSemantics<S extends TreeNodeSemantics<S>> {

	/**
	 * Copies the semantics.
	 */
	S copy(TreeNode<?, S> newNode);

}
