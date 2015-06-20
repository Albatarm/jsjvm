package com.didactilab.jsjvm.client.flow;

public class FlowIntConst extends AbstractFlowObject {

	private final int value;

	public FlowIntConst(int value) {
		this.value = value;
	}
	
	@Override
	public String toSource() {
		return String.valueOf(value);
	}
	
}
