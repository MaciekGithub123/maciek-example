package maciek.tree2;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
 * @param <S> the tree nodes semantics type
 */
public class ImmutableTree<S extends TreeNodeSemantics<S>> implements Tree<ImmutableTree<S>, ImmutableTreeNode<S>, S> {
	
	/**
	 * Map representation of the tree.
	 */
	private Map<TreePath, ImmutableTreeNode<S>> map = asMap();
	
	@Override
	public ImmutableTreeNode<S> root() {
		return map.get(TreePathStep.THIS);
	}

	@Override
	public Collection<ImmutableTreeNode<S>> nodes() {
		return map.values();
	}
	
	private Map<TreePath, S> createMap() {
		
		var root = root();
		
		
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