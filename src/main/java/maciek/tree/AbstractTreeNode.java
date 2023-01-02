package maciek.tree;

import java.util.LinkedList;
import java.util.List;

/**
 * An abstract implementation of TreeNode.
 */
public abstract class AbstractTreeNode<N extends AbstractTreeNode<N, S>, S extends TreeNodeSemantics<S>> implements TreeNode<N, S> {

	/**
	 * The node's parent.
	 */
	protected N p;

	/**
	 * The node's children.
	 */
	protected List<N> ch;

	/**
	 * The node's semantics.
	 */
	protected S s;

	/**
	 * The distance to the root.
	 */
	protected int depth;

	/**
	 * Constructor for subclasses.
	 * 
	 * @param children    the actual children list
	 * @param semantics   the semantics copied on set
	 */
	protected AbstractTreeNode(N parent, List<N> children, S semantics) {
		p = parent;
		ch = children;
		s = semantics.copy(this);
	}

	/**
	 * Gets this.
	 * <p>
	 * For this implementation to operate on actual implementation type instead of abstract type.
	 */
	protected abstract N getThis();

	@Override
	public N parent() {
		return p;
	}
	
	@Override
	public N child(int idx) {
		return ch.get(idx);
	}
	
	@Override
	public N lastChild() {
		if (ch.isEmpty()) {
			return null;
		}
		else {
			return ch.get(ch.size() - 1);
		}
	}

	@Override
	public N left() {
		if (p == null)
			return null;

		return p.children().get(childIndex() - 1);
	}

	@Override
	public N right() {
		if (p == null)
			return null;

		return p.children().get(childIndex() + 1);
	}

	@Override
	public List<N> children() {
		return new LinkedList<>(ch);
	}

	@Override
	public S semantics() {
		return s.copy(this);
	}

	@Override
	public N root() {
		return p == null ? getThis() : p.root();
	}

	@Override
	public int depth() {
		return depth;
	}

	@Override
	public List<? extends N> descendants() {
		
		LinkedList<N> desc = new LinkedList<>();
		desc.addAll(ch);

		ch.stream().flatMap(ch -> ch.descendants().stream()).forEach(desc::add);

		return desc;
	}

	@Override
	public AbsoluteTreePath absoluteTreePath() {
		LinkedList<Integer> path = new LinkedList<>();
		for (N n = getThis(); n != null; n = n.p) {
			path.addFirst(n.childIndex());
		}
		return new AbsoluteTreePath(path);
	}

	@Override
	public ImmutableTree<S> subtree() {
		return ImmutableTree.<S>mapper().map(this);
	}

	/**
	 * This node child index in its parent children list.
	 */
	public int childIndex() {
		if (p == null) {
			return -1;
		}
		return p.children().indexOf(this);
	}

}
