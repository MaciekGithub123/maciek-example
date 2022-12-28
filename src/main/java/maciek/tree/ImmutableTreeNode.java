package maciek.tree;

import java.util.List;

/**
 * Immutable tree node implementation.
 * <p>
 * Safe to use and optimized for tree querying.
 */
public class ImmutableTreeNode<S extends TreeNodeSemantics<S>> extends AbstractTreeNode<ImmutableTreeNode<S>, S> {

	/**
	 * The root cache.
	 */
	private ImmutableTreeNode<S> rootCache;

	/**
	 * The left sibling cache.
	 */
	private ImmutableTreeNode<S> leftSiblingCache;

	/**
	 * The right sibling cache.
	 */
	private ImmutableTreeNode<S> rightSiblingCache;

	/**
	 * The descendants cache.
	 */
	private List<? extends ImmutableTreeNode<S>> descendantsCache;

	/**
	 * The path from the root to this node cache.
	 */
	private TreePath pathFromRootCache;

	/**
	 * Protected constructor. Use the factory instead.
	 */
	protected ImmutableTreeNode(ImmutableTreeNode<S> parent, List<ImmutableTreeNode<S>> children, S semantics) {
		super(parent, children, semantics);
	}

	@Override
	protected ImmutableTreeNode<S> getThis() {
		return this;
	}

	@Override
	public ImmutableTreeNode<S> root() {
		if (rootCache != null) {
			return rootCache;
		}
		return rootCache = super.root();
	}

	@Override
	public ImmutableTreeNode<S> leftSibling() {
		if (leftSiblingCache != null) {
			return leftSiblingCache;
		}
		return leftSiblingCache = super.leftSibling();
	}

	@Override
	public ImmutableTreeNode<S> rightSibling() {
		if (rightSiblingCache != null) {
			return rightSiblingCache;
		}
		return rightSiblingCache = super.rightSibling();
	}

	@Override
	public List<? extends ImmutableTreeNode<S>> descendants() {
		if (descendantsCache != null) {
			return descendantsCache;
		}
		return descendantsCache = super.descendants();
	}

	@Override
	public TreePath pathFromRoot() {
		if (pathFromRootCache != null) {
			return pathFromRootCache;
		}
		return pathFromRootCache = super.pathFromRoot();
	}

}
