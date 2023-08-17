package maciek.tree2;

import java.util.Iterator;

public class DepthFirstTreeIterator<N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>>
		implements Iterator<TreeNode<N, S>> {
	
	N current;

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public TreeNode<N, S> next() {
		// TODO Auto-generated method stub
		return null;
	}

}
