package maciek.tree;

import java.util.List;

/**
 * A basic element in tree structure. The node has semantics assigned.
 * 
 * @param <N> the tree node type, for getting other tree nodes
 */
public interface TreeNode<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> {

	/**
	 * This node's parent.
	 */
	N parent();

	/**
	 * This node's left sibling.
	 */
	N leftSibling();

	/**
	 * This node's right sibling.
	 */
	N rightSibling();

	/**
	 * This node's children list copy.
	 */
	List<? extends N> children();

	/**
	 * The semantic of this node.
	 */
	S semantics();

	/**
	 * The root of the tree this node belongs to.
	 */
	N root();

	/**
	 * The descendant of this node localized by the relative path.
	 */
	N descendant(TreePath relativePath);

	/**
	 * The node's descendants.
	 */
	List<? extends N> descendants();

	/**
	 * The path to this node from the root.
	 */
	AbsoluteTreePath<S> pathFromRoot();

	/**
	 * The distance between this node and the root.
	 */
	int depth();

	/**
	 * The subtree containing this node and its descendants.
	 */
	ImmutableTree<S> subtree();

}
