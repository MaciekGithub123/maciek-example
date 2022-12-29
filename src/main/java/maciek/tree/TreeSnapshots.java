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
	 * Copies the tree snapshots and adds the new one.
	 */
	public TreeSnapshots<S> copyAndTakeSanpshot(ImmutableTree<S> snapshot) {
		
		if (!snapshots.isEmpty() && snapshots.get(0).equals(snapshot)) {
			return this;
		}
		
		LinkedList<ImmutableTree<S>> listCopy = new LinkedList<>(snapshots);
		listCopy.add(0, snapshot);
		
		return new TreeSnapshots<>(listCopy);
	}

	/**
	 * Copies the tree snapshots and adds the new one.
	 */
	public TreeSnapshots<S> copyAndTakeSanpshot(Tree<?, ?, S> snapshot) {
		return copyAndTakeSanpshot(snapshot.immutable());
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

	@Override
	public Iterator<ImmutableTree<S>> iterator() {
		return all().iterator();
	}

}