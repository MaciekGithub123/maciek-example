package maciek.tree;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 * A query for the location of the first node determined by the predicate.
 * <p>
 * If multiple nodes are found the precedence is determined by the comparator.
 */
public class TreeNodeQueryImpl<S extends TreeNodeSemantics<S>> implements TreeNodeQuery<S> {

	/**
	 * Predicate the queried node must fulfill.
	 */
	private Predicate<TreeNode<?, S>> predicate;

	/**
	 * Determines the precedence if multiple nodes fulfills the predicate.
	 */
	private Comparator<TreeNode<?, S>> comparator;

	@Override
	public AbsoluteTreePath query(Tree<?, ?, S> tree) {

		AbsoluteTreePath path = tree.nodes()
				.stream()
				.filter(predicate)
				.sorted(comparator)
				.findFirst()
				.map(n -> n.path())
				.orElse(null);

		return path;
	}

}
