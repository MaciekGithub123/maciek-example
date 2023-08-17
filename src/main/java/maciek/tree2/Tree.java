package maciek.tree2;

import java.util.Collection;
import java.util.Iterator;
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
 * 
 * @param <T> this tree type
 * @param <N> this tree nodes type
 * @param <S> this tree nodes semantics type
 */
public interface Tree<T extends Tree<T, N, S>, N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> extends Iterable<N> {

	/**
	 * The tree root.
	 */
	N root();

	/**
	 * All the tree nodes.
	 */
	Collection<N> nodes();
	
	/**
	 * Copies the tree.
	 */
	T copy();
	
	@Override
	default Iterator<N> iterator() {
		return nodes().iterator();
	}
	
}
