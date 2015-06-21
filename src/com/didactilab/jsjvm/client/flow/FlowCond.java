package com.didactilab.jsjvm.client.flow;

public class FlowCond implements FlowObject {

	private final FlowObject left;
	private final String op;
	private final FlowObject right;

	public FlowCond(FlowObject left, String op, FlowObject right) {
		this.left = left;
		this.op = op;
		this.right = right;
	}
	
	@Override
	public String toSource() {
		return left.toSource() + " " + op + " " + right.toSource();
	}

}
