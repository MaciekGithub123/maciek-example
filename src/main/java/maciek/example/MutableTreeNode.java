package maciek.example;

import java.util.List;

/**
 * A tree node implementation for tree which structure may be modified.
 *
 * @param <S> semantics type
 */
public class MutableTreeNode<S extends TreeNodeSemantics<S>> extends AbstractTreeNode<MutableTreeNode<S>, S> {

	/**
	 * Protected constructor. Use the factory instead.
	 */
	protected MutableTreeNode(MutableTreeNode<S> parent, List<MutableTreeNode<S>> children, S semantics) {
		super(parent, children, semantics);
	}

	@Override
	protected MutableTreeNode<S> getThis() {
		return this;
	}

	/**
	 * Sets this subtree parent and adds this as its child at given index.
	 * <p>
	 * For null parent parameter the subtree is removed from the parent tree. The
	 * child index parameter is then ignored.
	 */
	public void setParent(MutableTreeNode<S> parent, int childIdx) {
		if (parent == p && childIndex() == childIdx) {
			return;
		}
		if (p != null) {
			p.removeChild(childIndex());
		}
		if (parent == null) {
			p = null;
			depth = 0;
		} else {
			p = parent;
			depth = parent.depth() + 1;

			p.addChild(this, childIdx);
		}
	}

	/**
	 * Adds the child subtree at given index and sets this node as its parent.
	 */
	public void addChild(MutableTreeNode<S> child, int childIdx) {
		if (ch.size() > childIdx && children().get(childIdx) == child) {
			return;
		}
		ch.add(childIdx, child);

		child.setParent(this, childIdx);
	}

	/**
	 * Adds the last child subtree and sets this node as its parent.
	 */
	public void addChild(MutableTreeNode<S> child) {
		ch.add(child);

		child.setParent(this, ch.size() - 1);
	}

	/**
	 * Removes the child subtree from the tree.
	 */
	public void removeChild(int childIdx) {
		MutableTreeNode<S> child = children().get(childIdx);
		if (child == null) {
			return;
		}
		ch.remove(childIdx);

		child.setParent(null, -1);
	}

}
