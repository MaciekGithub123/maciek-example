package maciek.tree2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * An abstract implementation of TreeNode.
 * 
 * @param <N> this node type
 * @param <S> semantics type
 */
public class MutableTreeNode<S extends TreeNodeSemantics<S>> implements TreeNode<MutableTreeNode<S>, S> {

	private Map<TreePathStep, MutableTreeNode<S>> relatives = new HashMap<>();

	@Override
	public MutableTreeNode<S> get(TreePath path) {
		return relatives.get(path);
	}

	public void set(TreePathStep step, MutableTreeNode<S> node) {
		var prev = relatives.get(step);
		if (prev != null) {
			prev.set(step.reverse(), null);
		}
		relatives.put(step, node);
		if (node != null) {
			node.set(step.reverse(), this);
		}
	}

	@Override
	public MutableTreeNode<S> copy() {
		return copy(new HashMap<>());
	}
	
	private MutableTreeNode<S> copy(Map<MutableTreeNode<S>, MutableTreeNode<S>> copies) {
		var copy = copies.get(this);
		if (copy != null) {
			return copy;
		}
		copy = new MutableTreeNode<>();
		copies.put(this, copy);
		for (var e : copy.relatives.entrySet()) {
			relatives.put(e.getKey(), e.getValue().copy(copies));
		}
		return copy;
	}

}
