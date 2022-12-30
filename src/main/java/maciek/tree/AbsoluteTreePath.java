package maciek.tree;

import java.util.LinkedList;
import java.util.List;

/**
 * A location in the tree defined by the absolute path.
 */
public class AbsoluteTreePath<S extends TreeNodeSemantics<S>> extends TreePath implements TreeCursor<S> {

	/**
	 * @param consecutiveIndexes {@link TreePath#consecutiveIndexes}
	 */
	public AbsoluteTreePath(List<Integer> consecutiveIndexes) {
		super(consecutiveIndexes);
	}

	/**
	 * @param treePath tree path from the root
	 */
	public AbsoluteTreePath(TreePath treePath) {
		super(treePath);
	}

	@Override
	public AbsoluteTreePath<S> get(Tree<?, ?, S> tree) {
		return this;
	}
	
	@Override
	public AbsoluteTreePath<S> addStep(Integer step) {
		return new AbsoluteTreePath<>(super.addStep(step));
	}
	
	@Override
	public AbsoluteTreePath<S> removeStep() {
		return new AbsoluteTreePath<>(super.removeStep());
	}
	
	@Override
	public AbsoluteTreePath<S> replaceStep(Integer replacementStep) {
		return new AbsoluteTreePath<>(super.replaceStep(replacementStep));
	}

	@Override
	public TreeCursor<S> find(TreePath path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeCursor<S> root() {
		return new AbsoluteTreePath<>(List.of());
	}

	@Override
	public TreeCursor<S> parent() {
		return removeStep();
	}

	@Override
	public TreeCursor<S> firstChild() {
		return addStep(0);
	}

	@Override
	public TreeCursor<S> lastChild() {
		return addStep(-2);
	}

	@Override
	public TreeCursor<S> leftSibling() {
		conse
		return null;
	}

	@Override
	public TreeCursor<S> rightSibling() {
		// TODO Auto-generated method stub
		return null;
	}

}
