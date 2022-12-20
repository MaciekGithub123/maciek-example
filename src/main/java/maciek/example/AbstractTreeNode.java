package maciek.example;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An abstract implementation of TreeNode.
 * 
 * @param <N> this node type
 * @param <S> semantics type
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
	public N leftSibling() {
		if (p == null)
			return null;

		return p.children().get(childIndex() - 1);
	}

	@Override
	public N rightSibling() {
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
	public N descendant(TreePath relativePath) {
		return relativePath.followFrom(getThis());
	}

	@Override
	public List<? extends N> descendants() {

		List<N> desc = children()
				.stream()
				.flatMap(ch -> ch.children().stream())
				.collect(Collectors.toList());

		return desc;
	}

	@Override
	public TreePath pathFromRoot() {
		if (parent() == null) {
			return new TreePath(List.of());
		}
		return parent().pathFromRoot().addStep(parent().childIndex());
	}

	@Override
	public ImmutableTree<S> subtree() {
		return ImmutableTree.<S>mapper().map(this);
	}

	/**
	 * Gets this node child index at its parent children list. Convenience method.
	 */
	public int childIndex() {
		if (parent() == null) {
			return -1;
		}
		return parent().children().indexOf(this);
	}

}