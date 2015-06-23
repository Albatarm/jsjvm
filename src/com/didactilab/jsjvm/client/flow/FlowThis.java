package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.Type;

public class FlowThis extends AbstractFlowObject implements FlowVar {

	private final Type thisType;

	public FlowThis(Type thisType) {
		this.thisType = thisType;
	}
	
	@Override
	public String toSource() {
		return "this";
	}
	
	@Override
	public String toString() {
		return toSource();
	}
	
	@Override
	public Type getType() {
		return thisType;
	}

}
