package com.didactilab.jsjvm.client.flow;

public class FlowFloatConst extends AbstractFlowObject {

	private final float value;

	public FlowFloatConst(float value) {
		this.value = value;
	}
	
	@Override
	public String toSource() {
		return String.valueOf(value);
	}

}
