package maciek.tree;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 * A query for a node in the tree determined by the predicate and the comparator.
 */
public class TreeNodeQuery<S extends TreeNodeSemantics<S>> {

	/**
	 * Predicate the queried node must fulfill.
	 */
	private Predicate<TreeNode<?, S>> predicate;

	/**
	 * Determines the precedence if multiple nodes fulfills the predicate.
	 */
	private Comparator<TreeNode<?, S>> comparator;

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
