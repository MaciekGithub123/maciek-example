package maciek.tree2;

public enum TreePathStep implements TreePath {
	
	THIS {
		@Override
		public TreePathStep reverse() {
			return THIS;
		}
	},
	LEFT {
		@Override
		public TreePathStep reverse() {
			return RIGHT;
		}
	},
	RIGHT {
		@Override
		public TreePathStep reverse() {
			return LEFT;
		}
	},
	UP {
		@Override
		public TreePathStep reverse() {
			return DOWN;
		}
	},
	DOWN {
		@Override
		public TreePathStep reverse() {
			return UP;
		}
	};
	
	@Override
	public abstract TreePathStep reverse();

	@Override
	public <N extends TreeNode<N, S>, S extends TreeNodeSemantics<S>> N followFrom(TreeNode<N, S> n) {
		return n.get(this);
	}


}
