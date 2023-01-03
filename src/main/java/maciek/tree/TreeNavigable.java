package maciek.tree;

/**
 * Anything that is aware of its tree location and enables navigation.
 */
public interface TreeNavigable<TN extends TreeNavigable<TN>> {
	
	/**
	 * Navigates to the path.
	 */
	TN path(AbsoluteTreePath absoluteTreePath);
	
	/**
	 * Navigates to the root.
	 */
	TN root();
	
	/**
	 * Navigates to the parent.
	 */
	TN parent();
	
	/**
	 * Navigates to the child at index.
	 */
	TN child(int idx);
	
	/**
	 * Navigates to the last child.
	 */
	TN lastChild();
	
	/**
	 * Navigates to the left sibling.
	 */
	TN left();
	
	/**
	 * Navigates to the right sibling.
	 */
	TN right();
	
}
