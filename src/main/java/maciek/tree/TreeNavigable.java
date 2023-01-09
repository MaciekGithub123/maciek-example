package maciek.tree;

/**
 * Anything that is aware of its tree location and enables navigation.
 * <p>
 * Returns this , for method chaining.
 */
public interface TreeNavigable<TN extends TreeNavigable<TN>> {

	/**
	 * The current location.
	 */
	AbsoluteTreePath path();

	/**
	 * Navigates to the path.
	 */
	TN path(AbsoluteTreePath path);
	
	/**
	 * Navigates to the last child.
	 */
	TN lastChild();

	/**
	 * Navigates to the root.
	 */
	default TN root() {
		return path(AbsoluteTreePath.ROOT);
	}

	/**
	 * Navigates to the parent.
	 */
	default TN parent() {
		return path(path().parent());
	}

	/**
	 * Navigates to the child at index.
	 */
	default TN child(int idx) {
		return path(path().child(idx));
	}

	/**
	 * Navigates to the left sibling.
	 */
	default TN left() {
		return path(path().left());
	}

	/**
	 * Navigates to the right sibling.
	 */
	default TN right() {
		return path(path().right());
	}

}
