package maciek.tree;

import java.util.List;
import java.util.Map;

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
public interface Tree<T extends Tree<T, N, S>, N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> extends Iterable<N> {

	/**
	 * The tree root.
	 */
	N root();

	/**
	 * The previous snapshots.
	 */
	TreeSnapshots<S> treeSnapshots();

	/**
	 * All the tree nodes.
	 */
	List<N> nodes();
	
	/**
	 * The map representation of the tree.
	 */
	Map<TreePath, S> asMap();

	/**
	 * The nodes added only in recent transformations,
	 * 
	 * @param snapshotsAgo how many recent transformations are to be considered
	 */
	List<N> recentlyAdded(int snapshotsAgo);

	/**
	 * Copies the tree.
	 */
	T copy();

	/**
	 * Starts the tree transformation.
	 */
	TreeBuilder<S> transform();
	
	/**
	 * Maps the tree structure and semantics to a different tree implementation.
	 * 
	 * @param <T2> mapped tree type
	 * @param <N2> mapped node type
	 */
	<T2 extends Tree<T2, N2, S>, N2 extends TreeNode<N2, S>> T2 map(TreeMapper<T2, N2, S> mapper);

}
