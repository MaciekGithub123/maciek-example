package maciek.tree;

/**
 * A query for determining given tree location. 
 */
public interface TreeNodeQuery<S extends TreeNodeSemantics<S>> {
	
	/**
	 * Queries for the tree location.
	 * <p>
	 * If the location is not null then it is valid.
	 */
	AbsoluteTreePath query(Tree<?, ?, S> tree);

}
