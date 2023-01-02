package maciek.tree;

import java.util.List;

/**
 * A basic element in tree structure.
 * <p>
 * The node may have a single parent and multiple children.
 * <p>
 * The node has semantics assigned.
 * 
 * @param <N> the tree node type, for getting other tree nodes
 */
public interface TreeNode<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> extends TreeNavigable<N> {

	/**
	 * This node's children list copy.
	 */
	List<? extends N> children();

	/**
	 * The semantic of this node.
	 */
	S semantics();

	/**
	 * The descendants of this node.
	 */
	List<? extends N> descendants();

	/**
	 * The absolute tree path of this node.
	 */
	AbsoluteTreePath absoluteTreePath();

	/**
	 * The distance between this node and the root.
	 */
	int depth();

	/**
	 * The subtree containing this node and its descendants.
	 */
	ImmutableTree<S> subtree();

}
