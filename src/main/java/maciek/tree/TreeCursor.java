package maciek.tree;

import java.util.function.Consumer;

/**
 * A tree node pointer.
 * <p>
 * Exposes method for traversing the tree.
 * <p>
 * May indicate a null node.
 * <p>
 * Return itself for method chaining.
 */
public class TreeCursor<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>>
		implements TreeLocation<S>, TreeNavigable<TreeCursor<N, S>> {

	/**
	 * The traversed tree.
	 */
	private Tree<?, N, S> tree;

	/**
	 * The current location.
	 */
	private AbsoluteTreePath currentLoc;

	/**
	 * Do something with the current node if the node at the current location exists.
	 */
	public TreeCursor<N, S> forCurrentNode(Consumer<N> consumer) {
		N n = tree.node(currentLoc);
		if (n != null) {
			consumer.accept(n);
		}
		return this;
	}

	@Override
	public TreeCursor<N, S> path(AbsoluteTreePath path) {
		currentLoc = path;
		return this;
	}

	@Override
	public TreeCursor<N, S> root() {
		currentLoc = currentLoc.root();
		return this;
	}

	@Override
	public TreeCursor<N, S> parent() {
		currentLoc = currentLoc.parent();
		return this;
	}

	@Override
	public TreeCursor<N, S> child(int idx) {
		currentLoc = currentLoc.child(idx);
		return this;
	}

	@Override
	public TreeCursor<N, S> lastChild() {
		N n = tree.node(currentLoc);
		if (n != null) {
			N ch = n.lastChild();
			if (ch != null) {
				currentLoc = ch.absoluteTreePath();
			}
		}
		return this;
	}

	@Override
	public TreeCursor<N, S> left() {
		currentLoc = currentLoc.left();
		return this;
	}

	@Override
	public TreeCursor<N, S> right() {
		currentLoc = currentLoc.right();
		return this;
	}

	@Override
	public AbsoluteTreePath get(Tree<?, ?, S> tree) {
		return currentLoc;
	}

}
