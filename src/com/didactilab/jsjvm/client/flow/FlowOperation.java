package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.Type;

public class FlowOperation extends AbstractFlowObject implements FlowValue {

	private final FlowObject left;
	private final String op;
	private final FlowObject right;

	public FlowOperation(FlowObject left, String op, FlowObject right) {
		this.left = left;
		this.op = op;
		this.right = right;
	}
	
	@Override
	public String toSource() {
		return left.toSource() + " " + op + " " + right.toSource();
	}

	@Override
	public Type getType() {
		if (left instanceof FlowValue) {
			return ((FlowValue) left).getType();
		}
		if (right instanceof FlowValue) {
			return ((FlowValue) right).getType();
		}
		throw new IllegalStateException();
	}

}
