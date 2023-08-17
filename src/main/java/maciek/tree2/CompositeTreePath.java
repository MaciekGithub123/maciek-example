package maciek.tree2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CompositeTreePath implements TreePath {

	private final List<TreePath> treePaths;

	public CompositeTreePath(List<TreePath> treePaths) {
		this.treePaths = treePaths;
	}

	@Override
	public <N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> TreeNode<N, S> followFrom(TreeNode<N, S> node) {
		for (TreePath p : treePaths) {
			node = p.followFrom(node);
		}
		return node;
	}

	@Override
	public TreePath reverse() {

		List<TreePath> reversed = treePaths
				.stream()
				.map(p -> p.reverse())
				.collect(Collectors.toCollection(ArrayList::new));

		Collections.reverse(reversed);

		return new CompositeTreePath(reversed);
	}

}
