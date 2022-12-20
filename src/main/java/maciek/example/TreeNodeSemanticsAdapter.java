package maciek.example;

import java.util.Objects;

/**
 * Adapter for very simple immutable value-like semantics.
 *
 * @param <V> the actual value
 */
public class TreeNodeSemanticsAdapter<V> implements TreeNodeSemantics<TreeNodeSemanticsAdapter<V>> {
	
	private final V value;
	
	private TreeNodeSemanticsAdapter(V value) {
		this.value = value;
	}
	
	public static final <V> TreeNodeSemanticsAdapter<V> val(V v) {
		return new TreeNodeSemanticsAdapter<V>(v);
	}
	
	@Override
	public TreeNodeSemanticsAdapter<V> copy(TreeNode<?, TreeNodeSemanticsAdapter<V>> newNode) {
		return this;
	}
	
	/**
	 * The value.
	 */
	public V value() {
		return value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		@SuppressWarnings("rawtypes")
		TreeNodeSemanticsAdapter other = (TreeNodeSemanticsAdapter) obj;
		return Objects.equals(value, other.value);
	}
	
	

}
