package maciek.example;

import java.util.LinkedList;
import java.util.List;

/**
 * Factory for tree node.
 * <p>
 * Defines tree implementation details unrelated to the tree structure.
 */
public interface TreeNodeFactory<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> {

	/**
	 * Creates a new node in the tree.
	 * 
	 * @return created node
	 */
	N createNode(N parent, List<N> children, S semantics);

	/**
	 * Creates a new node. Relations to parent and children are to be set later.
	 * 
	 * @return created node
	 */
	default N createNode(S semantics) {
		return createNode(null, new LinkedList<>(), semantics);
	}

	/**
	 * The tree node factory for immutable tree nodes.
	 */
	public static class Immutable<S extends TreeNodeSemantics<S>> implements TreeNodeFactory<ImmutableTreeNode<S>, S> {

		@Override
		public ImmutableTreeNode<S> createNode(ImmutableTreeNode<S> parent, List<ImmutableTreeNode<S>> children, S semantics) {
			return new ImmutableTreeNode<S>(parent, children, semantics);
		}

	}

	/**
	 * The tree node factory for mutable tree nodes.
	 * 
	 * @param <S> semantics type
	 */
	public static class Mutable<S extends TreeNodeSemantics<S>> implements TreeNodeFactory<MutableTreeNode<S>, S> {

		@Override
		public MutableTreeNode<S> createNode(MutableTreeNode<S> parent, List<MutableTreeNode<S>> children, S semantics) {
			return new MutableTreeNode<S>(parent, children, semantics);
		}

	}

}