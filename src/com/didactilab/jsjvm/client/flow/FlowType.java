package com.didactilab.jsjvm.client.flow;

public class FlowType extends AbstractFlowObject {

	private final String type;

	public FlowType(String type) {
		this.type = type;
	}
	
	@Override
	public String toSource() {
		return type;
	}

}
