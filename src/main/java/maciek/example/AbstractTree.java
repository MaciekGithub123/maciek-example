package maciek.example;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Abstract tree implementation.
 * 
 * @param <T> this tree type
 * @param <N> this tree nodes type
 * @param <S> semantics type
 */
public abstract class AbstractTree<T extends Tree<T, N, S>, N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> implements Tree<T, N, S> {

	/**
	 * The tree snapshots.
	 */
	private final TreeSnapshots<S> treeSnapshots;

	/**
	 * The tree root.
	 */
	private N root;

	/**
	 * Protected constructor.
	 * 
	 * @param prevSnapshots the previous snapshots copied on set
	 */
	protected AbstractTree(N root, TreeSnapshots<S> treeSnapshots) {
		this.root = root;
		this.treeSnapshots = treeSnapshots.copy();
	}

	@Override
	public N root() {
		return root;
	}

	@Override
	public TreeSnapshots<S> treeSnapshots() {
		return treeSnapshots;
	}

	@Override
	public List<N> nodes() {
		return Stream.concat(Stream.of(root), root.descendants().stream()).collect(Collectors.toList());
	}
	
	@Override
	public Map<TreePath, S> asMap() {
		return nodes().stream().collect(Collectors.toMap(n -> n.pathFromRoot(), n -> n.semantics()));
	}

	@Override
	public List<N> recentlyAdded(int stagesAgo) {

		List<TreePath> prevPaths = treeSnapshots.all()
				.stream()
				.limit(stagesAgo)
				.flatMap(t -> t.nodes().stream())
				.map(TreeNode::pathFromRoot)
				.distinct()
				.collect(Collectors.toList());

		List<N> recentlyAdded = nodes()
				.stream()
				.filter(n -> !prevPaths.contains(n.pathFromRoot()))
				.collect(Collectors.toList());

		return recentlyAdded;
	}

	@Override
	public TreeTransformation<S> transform() {
		return new TreeBuilder<>(this);
	}

	@Override
	public <T2 extends Tree<T2, N2, S>, N2 extends TreeNode<N2, S>> T2 map(TreeMapper<T2, N2, S> mapper) {
		return mapper.map(this);
	}

	@Override
	public Iterator<N> iterator() {
		return nodes().iterator();
	}
	
}
