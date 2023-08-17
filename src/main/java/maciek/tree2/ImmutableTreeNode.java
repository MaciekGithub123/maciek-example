package maciek.tree2;

import java.util.HashMap;
import java.util.Map;

// TODO equals and hash code

public class ImmutableTreeNode <S extends TreeNodeSemantics<S>> implements TreeNode<ImmutableTreeNode<S>, S> {
	
	private TreeNode<?, S> source;
	
	private Map<TreePath, ImmutableTreeNode<S>> cache = new HashMap<>();

	public ImmutableTreeNode(TreeNode<?, S> source) {
		this.source = source.copy();
	}

	@Override
	public ImmutableTreeNode<S> get(TreePath path) {
		ImmutableTreeNode<S> i = cache.get(path);
		if (i != null) {
			return i;
		}
		if (cache.containsKey(path)) {
			return null;
		}
		TreeNode<?, S> m = path.followFrom(source);
		if (m == null) {
			cache.put(path, null);
		}
		else {
			i = new ImmutableTreeNode<>(m);
			cache.put(path, i);
		}
		return i;
	}

	@Override
	public ImmutableTreeNode<S> copy() {
		return this;
	}

}
