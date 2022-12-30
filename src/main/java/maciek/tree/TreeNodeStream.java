package maciek.tree;

/**
 * A stream of nodes of the tree.
 * <p>
 * Enables declarative type implementation of tree transformation.
 * 
 * @see {@link java.util.stream.Stream}
 */
public class TreeNodeStream<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> {
	
	TreeNodeStream<N, S> filter(Predicate<N> predicate);
	
	TreeNodeStream<N, S> filter(Predicate<N> predicate);

}
