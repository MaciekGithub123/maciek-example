package maciek.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The tree structure changes history.
 * <p>
 * A tree snapshot is taken before each transformation.
 */
public class TreeSnapshots<S extends TreeNodeSemantics<S>> implements Iterable<ImmutableTree<S>> {

	private List<ImmutableTree<S>> snapshots;

	/**
	 * Creates empty snapshot collection.
	 */
	public static final <S extends TreeNodeSemantics<S>> TreeSnapshots<S> empty() {
		return new TreeSnapshots<>(List.of());
	}

	/**
	 * Public constructor.
	 * <p>
	 * Copies the list on set.
	 */
	public TreeSnapshots(List<ImmutableTree<S>> list) {
		this.snapshots = new LinkedList<>(list);
	}

	/**
	 * Takes snapshot.
	 * 
	 * @this for method chaining
	 */
	public TreeSnapshots<S> takeSnapshot(Tree<?, ?, S> snapshot) {
		if (!snapshots.isEmpty() && snapshots.get(0).equals(snapshot)) {
			return this;
		}
		snapshots.add(0, snapshot.map(ImmutableTree.mapper()));
		return this;
	}

	/**
	 * Retrieves the most recent snapshot.
	 */
	public ImmutableTree<S> getRecent() {
		return snapshots.get(0);
	}

	/**
	 * Gets the copy all the snapshots. The recent one are before the later one.
	 */
	public List<ImmutableTree<S>> all() {
		return List.copyOf(snapshots);
	}

	/**
	 * Copies the whole snapshot collection.
	 */
	public TreeSnapshots<S> copy() {
		return new TreeSnapshots<>(snapshots);
	}

	@Override
	public Iterator<ImmutableTree<S>> iterator() {
		return all().iterator();
	}

}