package maciek.tree;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 * A location in the tree determined by a tree node query, that is a tree node predicate and a tree node
 * comparator.
 */
public class TreeNodeQuery<S extends TreeNodeSemantics<S>> implements TreeLocation<S> {

	/**
	 * Predicate the queried node must fulfill.
	 */
	private Predicate<TreeNode<?, S>> predicate;

	/**
	 * Determines the precedence if multiple nodes fulfills the predicate.
	 */
	private Comparator<TreeNode<?, S>> comparator;

	@Override
	public AbsoluteTreePath<S> get(Tree<?, ?, S> tree) {
		return queryNode(tree).absoluteTreePath();
	}

	/**
	 * Queries for the tree node.
	 */
	public TreeNode<?, S> queryNode(Tree<?, ?, S> tree) {

		TreeNode<?, S> node = tree.nodes()
				.stream()
				.filter(predicate)
				.sorted(comparator)
				.findFirst()
				.orElse(null);

		return node;
	}

}
