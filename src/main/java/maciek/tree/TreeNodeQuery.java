package maciek.tree;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 * A location in the tree determined by a the tree node predicate.
 */
public class TreeNodeQuery<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> implements TreeLocation<N, S> {

	/**
	 * Predicate the queried node must fulfill.
	 */
	private Predicate<N> predicate;

	/**
	 * Determines the precedence for nodes fulfilling the predicate.
	 */
	private Comparator<N> comparator;

	@Override
	public N get(Tree<?, N, S> tree) {

		N node = tree.nodes()
				.stream()
				.filter(predicate)
				.sorted(comparator)
				.findFirst()
				.orElse(null);

		return node;
	}

}
