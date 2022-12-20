package maciek.example;

import java.util.Map;

/**
 * A tree with immutable structure.
 * <p>
 * Safe to use and optimized for tree querying.
 * <p>
 * Using immutable tree as a method parameter expresses better that it is IN parameter and won't be modified.
 * <p>
 * Provides equals and hashcode.
 * 
 * @param <S> semantics type
 */
public class ImmutableTree<S extends TreeNodeSemantics<S>> extends AbstractTree<ImmutableTree<S>, ImmutableTreeNode<S>, S> {
	
	/**
	 * Map representation of the tree.
	 */
	private Map<TreePath, S> map = asMap();

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
	public Map<TreePath, S> asMap() {
		return map;
	}

	@Override
	public ImmutableTree<S> copy() {
		return this;
	}
	
	@Override
	public int hashCode() {
		return map.hashCode();
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