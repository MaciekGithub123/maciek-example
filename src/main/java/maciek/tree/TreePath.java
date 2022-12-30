package maciek.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A path in the tree.
 * <p>
 * The path is made of consecutive steps which represents direct relation between nodes.
 * <p>
 * E.g. in the tree below, the path from the root to n5 is ->child(2)->child(0)
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
	 * The path defined by the consecutive steps.
	 */
	protected final List<TreePathStep> steps;

	/**
	 * @param steps {@link #consecutiveIndexes}
	 */
	public TreePath(List<TreePathStep> steps) {
		this.steps = List.copyOf(steps);
	}

	/**
	 * Constructor for subclasses extending TreePath contract.
	 */
	protected TreePath(TreePath treePath) {
		this.steps = List.copyOf(treePath.steps);
	}

	/**
	 * Follows the tree path starting from given node.
	 */
	public <N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> N followFrom(N node) {

		for (TreePathStep step : steps) {
			node = step.getNextNode(node);
		}
		return node;
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
	 * Creates tree path with last element removed.
	 */
	public TreePath removeStep() {

		LinkedList<Integer> indexes = new LinkedList<>(consecutiveIndexes);
		indexes.removeLast();

		return new TreePath(indexes);
	}

	/**
	 * Creates tree path with last element changed.
	 */
	public TreePath replaceStep(Integer replacementStep) {

		LinkedList<Integer> indexes = new LinkedList<>(consecutiveIndexes);
		indexes.removeLast();
		indexes.add(replacementStep);

		return new TreePath(indexes);
	}

	@Override
	public Iterator<Integer> iterator() {
		return consecutiveIndexes.iterator();
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
