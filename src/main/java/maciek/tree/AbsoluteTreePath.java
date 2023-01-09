package maciek.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * An path in the tree from the root to given node.
 * <p>
 * Defines the location of the tree node.
 * <p>
 * The path consist of child indexes of consecutive ancestors of given node, starting from the root.
 * <p>
 * Can be thought of as a tree node address.
 */
public class AbsoluteTreePath implements Comparable<AbsoluteTreePath>, TreeNavigable<AbsoluteTreePath> {

	/**
	 * A path to root.
	 */
	public static final AbsoluteTreePath ROOT = new AbsoluteTreePath(List.of());

	/**
	 * The consecutive ancestors child indexes.
	 */
	private final List<Integer> ancestorsChildIdx;

	/**
	 * Creates the absolute tree path from the list of consecutive ancestors child indexes.
	 */
	public AbsoluteTreePath(List<Integer> ancestorsChildIdx) {
		this.ancestorsChildIdx = List.copyOf(ancestorsChildIdx);
	}

	/**
	 * Get the tree node corresponding to this absolute tree path.
	 */
	public <N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> N get(Tree<?, N, S> tree) {
		return get(tree.root());
	}

	/**
	 * Get the tree node corresponding to this absolute tree path.
	 */
	<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> N get(N root) {
		N n = root;
		for (Integer idx : ancestorsChildIdx) {
			n = n.child(idx);
			if (n == null) {
				return null;
			}
		}
		return n;
	}

	/**
	 * The consecutive ancestors child indexes composing the path.
	 */
	public LinkedList<Integer> getIndexes() {
		return new LinkedList<>(ancestorsChildIdx);
	}

	/**
	 * Compares the absolute tree paths.
	 * <p>
	 * The order is: ancestors before descendants and left siblings before right.
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

	// navigation

	@Override
	public AbsoluteTreePath path() {
		return this;
	}

	/**
	 * The given path.
	 */
	@Override
	public AbsoluteTreePath path(AbsoluteTreePath absoluteTreePath) {
		return absoluteTreePath;
	}

	/**
	 * The path of the root.
	 */
	@Override
	public AbsoluteTreePath root() {
		return new AbsoluteTreePath(List.of());
	}

	/**
	 * The path of the parent.
	 */
	@Override
	public AbsoluteTreePath parent() {
		return new AbsoluteTreePath(ancestorsChildIdx.subList(0, ancestorsChildIdx.size() - 1));
	}

	/**
	 * The path of the child at index.
	 */
	@Override
	public AbsoluteTreePath child(int idx) {

		List<Integer> childPath = new ArrayList<>(ancestorsChildIdx.size() + 1);
		childPath.addAll(ancestorsChildIdx);
		childPath.add(idx);

		return new AbsoluteTreePath(childPath);
	}

	/**
	 * Unsupported. Sorry.
	 */
	@Override
	public AbsoluteTreePath lastChild() {
		throw new UnsupportedOperationException();
	}

	/**
	 * The path of the left sibling.
	 */
	@Override
	public AbsoluteTreePath left() {

		List<Integer> leftPath = new ArrayList<>(ancestorsChildIdx.size());
		leftPath.addAll(ancestorsChildIdx);
		leftPath.add(leftPath.remove(ancestorsChildIdx.size() - 1));

		return new AbsoluteTreePath(leftPath);
	}

	/**
	 * The path of the right sibling.
	 */
	@Override
	public AbsoluteTreePath right() {

		List<Integer> rightPath = new ArrayList<>(ancestorsChildIdx.size());
		rightPath.addAll(ancestorsChildIdx);
		rightPath.add(rightPath.remove(ancestorsChildIdx.size() + 1));

		return new AbsoluteTreePath(rightPath);
	}

}
