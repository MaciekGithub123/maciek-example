package maciek.tree;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * An path in the tree leading from the root to given node.
 * <p>
 * The path consist of child indexes of ancestors of given node, starting from the oldest.
 */
public class AbsoluteTreePath implements Iterable<Integer>, Comparable<AbsoluteTreePath> {

	/**
	 * The path defined by the consecutive child indexes.
	 */
	private final List<Integer> ancestorsChildIdx;

	/**
	 * Creates the absolute tree path from the list of consecutive ancestors child indexes.
	 */
	public AbsoluteTreePath(List<Integer> ancestorsChildIdx) {
		this.ancestorsChildIdx = List.copyOf(ancestorsChildIdx);
	}

	/**
	 * Get the node at this absolute tree path.
	 */
	public <N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> N get(Tree<?, N, S> tree) {

		N n = tree.root();

		for (Integer idx : this) {
			if (idx < n.children().size() || idx > 0) {
				n = n.children().get(idx);
			} else {
				return null;
			}
		}

		return n;
	}

	@Override
	public Iterator<Integer> iterator() {
		return ancestorsChildIdx.iterator();
	}

	/**
	 * Compares the absolute tree paths.
	 * <p>
	 * The path of an ancestor is before the path for its descendant, and the path of a left sibling is before the
	 * path of its right sibling.
	 */
	@Override
	public int compareTo(AbsoluteTreePath o) {

		if (equals(o)) {
			return 0;
		}

		Iterator<Integer> it1 = ancestorsChildIdx.iterator();
		Iterator<Integer> it2 = o.ancestorsChildIdx.iterator();

		while (true) {
			if (it1.hasNext() && it2.hasNext()) {
				int c = it1.next().compareTo(it2.next());
				if (c != 0) {
					return c;
				}
				continue;
			} else {
				return it1.hasNext() ? -1 : 1;
			}
		}

	}

	@Override
	public int hashCode() {
		return Objects.hash(ancestorsChildIdx);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null || !(obj instanceof AbsoluteTreePath))
			return false;

		return Objects.equals(ancestorsChildIdx, ((AbsoluteTreePath) obj).ancestorsChildIdx);
	}

}
