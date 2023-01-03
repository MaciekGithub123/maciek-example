package maciek.tree;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A semantic tree.
 * <p>
 * Each node can have single parent and multiple children. Each node can have semantics of given type
 * assigned.
 * <p>
 * A semantic tree may be useful for:
 * <ul>
 * <li>parsing,
 * <li>symbolic manipulation,
 * <li>best move search,
 * <li>dynamic property objects (instead of maps),
 * </ul>
 * and many more.
 */
public interface Tree<T extends Tree<T, N, S>, N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>>
		extends Iterable<N> {

	/**
	 * The tree root.
	 */
	N root();
	
	/**
	 * The tree node at given path.
	 */
	N node(AbsoluteTreePath path);

	/**
	 * The tree nodes stream.
	 */
	TreeNodeStream<N, S> stream();

	/**
	 * The tree snapshots.
	 */
	TreeSnapshots<S> treeSnapshots();

	/**
	 * The tree copy.
	 */
	T copy();

	/**
	 * All the nodes of the tree.
	 */
	default List<N> nodes() {
		return Stream.concat(Stream.of(root()), root().descendants().stream()).collect(Collectors.toList());
	}

	/**
	 * Iterator over the tree nodes.
	 */
	default Iterator<N> iterator() {
		return nodes().iterator();
	}

	/**
	 * Transforms the tree.
	 */
	default ImmutableTree<S> transform(TreeTransformation<S> treeTransformation) {
		return treeTransformation.transform(immutable());
	}

	/**
	 * Maps the tree to immutable tree.
	 * <p>
	 * Copies the tree if not immutable.
	 */
	default ImmutableTree<S> immutable() {
		return map(ImmutableTree.mapper());
	}

	/**
	 * Maps the tree to mutable tree.
	 * <p>
	 * Copies the tree if not mutable.
	 */
	default MutableTree<S> mutable() {
		return map(MutableTree.mapper());
	}

	/**
	 * Maps the tree structure and semantics to a different implementation.
	 */
	default <T2 extends Tree<T2, N2, S>, N2 extends TreeNode<N2, S>> T2 map(TreeMapper<T2, N2, S> mapper) {
		return mapper.map(this);
	}

	/**
	 * The map representation of the tree.
	 */
	default Map<AbsoluteTreePath, S> asMap() {
		return nodes().stream().collect(Collectors.toMap(n -> n.absoluteTreePath(), n -> n.semantics()));
	}

	/**
	 * The builder with this tree structure.
	 */
	default TreeBuilder<S> toBuilder() {
		return new TreeBuilder<>(this);
	}

	/**
	 * The tree nodes added only in recent transformations.
	 * 
	 * @param snapshotsAgo how many recent transformations are to be considered
	 */
	default List<N> recentlyAdded(int snapshotsAgo) {

		List<AbsoluteTreePath> prevPaths = treeSnapshots().all()
				.stream()
				.limit(snapshotsAgo)
				.flatMap(t -> t.nodes().stream())
				.map(TreeNode::absoluteTreePath)
				.distinct()
				.collect(Collectors.toList());

		List<N> recentlyAdded = nodes()
				.stream()
				.filter(n -> !prevPaths.contains(n.absoluteTreePath()))
				.collect(Collectors.toList());

		return recentlyAdded;
	}

}
