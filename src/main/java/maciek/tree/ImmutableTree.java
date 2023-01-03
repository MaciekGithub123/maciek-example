package maciek.tree;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * A tree with immutable structure.
 * <p>
 * Safe to use and optimized for tree querying.
 * <p>
 * Using immutable tree as a method parameter expresses better that it is IN parameter and won't be modified.
 * <p>
 * Provides equals and hashcode.
 */
public class ImmutableTree<S extends TreeNodeSemantics<S>>
		extends AbstractTree<ImmutableTree<S>, ImmutableTreeNode<S>, S> {

	/**
	 * The cache of tree nodes.
	 */
	private Map<AbsoluteTreePath, ImmutableTreeNode<S>> nodesCache;
	
	/**
	 * The cache of tree semantics.
	 */
	private Map<AbsoluteTreePath, S> semanticsCache;

	/**
	 * Protected constructor.
	 */
	protected ImmutableTree(ImmutableTreeNode<S> root, TreeSnapshots<S> treeSnapshots) {
		super(root, treeSnapshots);
	}

	/**
	 * The factory for immutable node.
	 */
	public static final <S extends TreeNodeSemantics<S>> TreeNodeFactory.Immutable<S> nodeFactory() {
		return new TreeNodeFactory.Immutable<>();
	}

	/**
	 * The mapper to immutable tree.
	 */
	public static final <S extends TreeNodeSemantics<S>> TreeMapper<ImmutableTree<S>, ImmutableTreeNode<S>, S> mapper() {
		return new TreeMapper<ImmutableTree<S>, ImmutableTreeNode<S>, S>(ImmutableTree::new, nodeFactory());
	}

	@Override
	public ImmutableTreeNode<S> node(AbsoluteTreePath path) {
		if (nodesCache == null) {
			nodesCache = nodes().stream().collect(Collectors.toMap(n -> n.absoluteTreePath(), n -> n));
		}
		return nodesCache.get(path);
	}

	@Override
	public Map<AbsoluteTreePath, S> asMap() {
		if (semanticsCache == null) {
			semanticsCache = nodes().stream().collect(Collectors.toMap(n -> n.absoluteTreePath(), n -> n.semantics()));
		}
		return semanticsCache;
	}

	@Override
	public ImmutableTree<S> copy() {
		return this;
	}

	@Override
	public ImmutableTree<S> immutable() {
		return this;
	}

	@Override
	public int hashCode() {
		return nodesCache.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ImmutableTree)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		@SuppressWarnings("unchecked")
		ImmutableTree<S> o = (ImmutableTree<S>) obj;

		return asMap().equals(o.asMap());
	}

}