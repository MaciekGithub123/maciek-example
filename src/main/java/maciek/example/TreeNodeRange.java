package maciek.example;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A range of consecutive sibling nodes.
 */
public class TreeNodeRange<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> {

	/**
	 * First node after the start of the range.
	 */
	private final N first;

	/**
	 * Last node before end of the range.
	 */
	private final N last;

	/**
	 * The length of the range.
	 */
	private final int length;

	/**
	 * Creates a range between given nodes, the nodes included.
	 */
	public static <N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> TreeNodeRange<N, S> rangeBetween(N first, N last) {
		return new TreeNodeRange<>(first, last);
	}

	/**
	 * Creates a range of given length;
	 */
	public static <N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> TreeNodeRange<N, S> rangeOfLength(N first, int length) {
		return new TreeNodeRange<>(first, length);
	}

	/**
	 * Creates a range between given nodes, the nodes included.
	 */
	private TreeNodeRange(N first, N last) {
		int length = 0;
		for (N n = first; n != last; n = n.rightSibling()) {
			++length;
		}
		this.first = first;
		this.last = last;
		this.length = length;
	}

	/**
	 * Creates a range of given length;
	 */
	private TreeNodeRange(N first, int length) {
		N n = first;
		if (length < 1) {
			for (int i = 1; i > length && n != null; i--) {
				n = n.leftSibling();
			}
		} else {
			for (int i = 1; i < length && n != null; i++) {
				n = n.rightSibling();
			}
		}
		this.first = first;
		this.last = n;
		this.length = length;
	}

	/**
	 * First node after the start of the range.
	 */
	public N first() {
		return first;
	}

	/**
	 * Last node before end of the range.
	 */
	public N last() {
		return last;
	}

	/**
	 * The length of the range.
	 */
	public int length() {
		return length;
	}
	
	/**
	 * All nodes within the range.
	 */
	public List<N> nodes() {
		List<N> list = new LinkedList<>();
		for (N n = first; list.size() < length; n = n.rightSibling()) {
			list.add(n);
		}
		return list;
	}

	/**
	 * Ranges are equal if indicate the same range in the same tree.
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj == null || !(obj instanceof TreeNodeRange)) {
			return false;
		}
		if (obj == this) {
			return true;
		}

		TreeNodeRange<?, ?> other = (TreeNodeRange<?, ?>) obj;

		return first == other.first && last == other.last && length == other.length;
	}

	@Override
	public int hashCode() {

		int hashCode = new HashCodeBuilder()
				.append(first)
				.append(last)
				.append(length)
				.hashCode();

		return hashCode;
	}

}
