package maciek.tree;

import java.util.List;

/**
 * A location in the tree determined by a tree path that starts at the root.
 */
public class AbsoluteTreePath<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>>
		extends TreePath implements TreeLocation<N, S> {

	/**
	 * @param consecutiveIndexes {@link TreePath#consecutiveIndexes}
	 */
	public AbsoluteTreePath(List<Integer> consecutiveIndexes) {
		super(consecutiveIndexes);
	}

	@Override
	public N get(Tree<?, N, S> tree) {
		return followFrom(tree.root());
	}

}
