package com.didactilab.jsjvm.client.flow;

public class FlowIntConst implements FlowObject {

	private final int value;

	public FlowIntConst(int value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
	@Override
	public String toSource() {
		return String.valueOf(value);
	}
	
}
