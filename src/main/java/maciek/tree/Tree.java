package maciek.tree;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A tree of nodes with semantics assigned to the nodes.
 * <p>
 * A semantic tree may be useful for:
 * <ul>
 * <li>parsing,
 * <li>symbolic manipulation,
 * <li>best move search,
 * <li>dynamic property objects (instead of maps),
 * </ul>
 * and many more.
 * <p>
 * Using tree instead of tree node as a method parameter expresses better that the parameter represents
 * the whole tree instead e.g. node, subtree or other tree part.
 */
public interface Tree<T extends Tree<T, N, S>, N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>>
		extends Iterable<N> {

	/**
	 * The tree root.
	 */
	N root();

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
	 * Copies the tree and performs the tree transformation.
	 */
	default T transform(TreeTransformation<T, N, S> treeTransformation) {
		return treeTransformation.transform(copy());
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
	default Map<TreePath, S> asMap() {
		return nodes().stream().collect(Collectors.toMap(n -> n.pathFromRoot(), n -> n.semantics()));
	}

	/**
	 * The builder with this tree structure.
	 */
	default TreeBuilder<S> toBuilder() {
		return new TreeBuilder<>(this);
	}
	
	/**
	 * The nodes added only in recent transformations,
	 * 
	 * @param snapshotsAgo how many recent transformations are to be considered
	 */
	default List<N> recentlyAdded(int snapshotsAgo) {

		List<TreePath> prevPaths = treeSnapshots().all()
				.stream()
				.limit(snapshotsAgo)
				.flatMap(t -> t.nodes().stream())
				.map(TreeNode::pathFromRoot)
				.distinct()
				.collect(Collectors.toList());

		List<N> recentlyAdded = nodes()
				.stream()
				.filter(n -> !prevPaths.contains(n.pathFromRoot()))
				.collect(Collectors.toList());

		return recentlyAdded;
	}

}
