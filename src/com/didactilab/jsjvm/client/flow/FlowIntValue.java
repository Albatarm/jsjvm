package com.didactilab.jsjvm.client.flow;

public class FlowIntValue implements FlowObject {

	private final int value;

	public FlowIntValue(int value) {
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
