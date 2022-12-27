package maciek.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Maps the tree structure and semantics to a different implementation.
 * 
 * @param <T> the target tree type
 * @param <N> the target tree node type
 * @param <S> the target tree nodes semantics type
 */
public class TreeMapper<T extends Tree<T, N, S>, N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> {

	/**
	 * The constructor of the mapped tree.
	 */
	private BiFunction<N, TreeSnapshots<S>, T> treeConstructor;

	/**
	 * The new node factory.
	 */
	private TreeNodeFactory<N, S> nodeFactory;

	/**
	 * Public constructor.
	 */
	public TreeMapper(BiFunction<N, TreeSnapshots<S>, T> treeConstructor, TreeNodeFactory<N, S> nodeFactory) {
		this.treeConstructor = treeConstructor;
		this.nodeFactory = nodeFactory;
	}

	/**
	 * Maps the subtree.
	 * 
	 * @param tree a tree which structure and semantics is to be mapped.
	 */
	public T map(Tree<?, ?, S> tree) {
		return map(tree.root(), tree.treeSnapshots());
	}
	
	/**
	 * Maps the subtree.
	 * 
	 * @param subtreeRoot a subtree which structure and semantics is to be mapped.
	 * @param treeSnapshots the tree history to be set on the new tree
	 */
	public T map(TreeNode<?, S> subtreeRoot, TreeSnapshots<S> treeSnapshots) {
		return treeConstructor.apply(mapSubtree(subtreeRoot, null), treeSnapshots);
	}
	
	/**
	 * Maps the subtree.
	 * 
	 * @param subtreeRoot a subtree which structure and semantics is to be mapped.
	 */
	public T map(TreeNode<?, S> subtreeRoot) {
		return treeConstructor.apply(mapSubtree(subtreeRoot, null), TreeSnapshots.empty());
	}

	/**
	 * Maps the subtree.
	 * 
	 * @param parent the subtree root parent
	 * 
	 * @return the mapped subtree root
	 */
	private N mapSubtree(TreeNode<?, S> subtreeRoot, N parent) {

		List<N> children = new ArrayList<>(subtreeRoot.children().size());
		N node = nodeFactory.createNode(parent, children, subtreeRoot.semantics());

		for (TreeNode<?, S> ch : subtreeRoot.children()) {
			children.add(mapSubtree(ch, node));
		}

		return node;
	}

}
