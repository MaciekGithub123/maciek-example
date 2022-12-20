package maciek.example;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A path in the tree.
 * <p>
 * The path is made up of steps. A step toward a child is indicated by the child index. A step toward the
 * parent is indicated as -1.
 * <p>
 * E.g on for the following example tree the path from the root to n5 is [2, 0] and from the n4 to n5 is
 * [-1, -1, 2, 0].
 * 
 * <pre>
 * root -|- n1
 *       |
 *       |- n2 - n4
 *       |
 *       |- n3 - n5
 * 
 * </pre>
 */
public class TreePath implements Iterable<Integer>, Comparable<TreePath> {

	/**
	 * The path defined by the consecutive child indexes.
	 */
	private final List<Integer> consecutiveIndexes;

	/**
	 * @param consecutiveIndexes {@link #consecutiveIndexes}
	 */
	public TreePath(List<Integer> consecutiveIndexes) {
		this.consecutiveIndexes = List.copyOf(consecutiveIndexes);
	}

	@Override
	public Iterator<Integer> iterator() {
		return consecutiveIndexes.iterator();
	}

	/**
	 * Creates tree path with one additional element added added to this tree path.
	 */
	public TreePath addStep(Integer step) {

		LinkedList<Integer> indexes = new LinkedList<>(consecutiveIndexes);
		indexes.add(step);

		return new TreePath(indexes);
	}

	/**
	 * Follows the tree path starting from given node.
	 */
	public <N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> N followFrom(N node) {

		for (Integer idx : this) {
			if (idx == -1) {
				node = node.parent();
				if (node == null) {
					return null;
				}
				continue;
			}
			if (idx < node.children().size() || idx > 0) {
				node = node.children().get(idx);
				continue;
			}
			return null;
		}

		return node;
	}

	/**
	 * Compares the tree paths of nodes in the tree.
	 * <p>
	 * The path for an ancestor is before the path for its descendant, and the path a left sibling is before
	 * the path for its right sibling.
	 */
	@Override
	public int compareTo(TreePath o) {

		if (equals(o)) {
			return 0;
		}

		Iterator<Integer> it1 = consecutiveIndexes.iterator();
		Iterator<Integer> it2 = o.consecutiveIndexes.iterator();

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
		return Objects.hash(consecutiveIndexes);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null || !(obj instanceof TreePath))
			return false;

		return Objects.equals(consecutiveIndexes, ((TreePath) obj).consecutiveIndexes);
	}
	
}
