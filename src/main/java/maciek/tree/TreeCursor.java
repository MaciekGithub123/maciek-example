package maciek.tree;

import java.util.function.Consumer;

/**
 * A tree node pointer. Stateless.
 * <p>
 * Exposes method for traversing the tree.
 * <p>
 * May indicate a null node.
 */
public class TreeCursor<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> implements TreeNavigable<TreeCursor<N, S>> {

	/**
	 * The traversed tree.
	 */
	private final Tree<?, N, S> tree;

	/**
	 * The current location.
	 */
	private final AbsoluteTreePath currentLoc;

	/**
	 * Creates the cursor indicating tree root.
	 */
	public TreeCursor(Tree<?, N, S> tree) {
		this(tree, AbsoluteTreePath.ROOT);
	}
	
	/**
	 * Creates the cursor indicating tree location at given path.
	 */
	public TreeCursor(Tree<?, N, S> tree, AbsoluteTreePath path) {
		this.tree = tree;
		this.currentLoc = path;
	}

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

	/**
	 * Gets the traversed tree.
	 */
	public Tree<?, N, S> getTree() {
		return tree;
	}
	
	@Override
	public AbsoluteTreePath path() {
		return currentLoc;
	}

	@Override
	public TreeCursor<N, S> path(AbsoluteTreePath path) {
		return new TreeCursor<>(tree, path);
	}

	@Override
	public TreeCursor<N, S> lastChild() {
		N n = tree.node(currentLoc);
		if (n != null) {
			N ch = n.lastChild();
			if (ch != null) {
				return new TreeCursor<>(tree, ch.path());
			}
		}
		throw new RuntimeException(); // TODO
	}

}
