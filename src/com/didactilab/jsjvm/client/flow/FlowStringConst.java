package com.didactilab.jsjvm.client.flow;

public class FlowStringConst extends AbstractFlowObject {

	private final String value;

	public FlowStringConst(String value) {
		this.value = value;
	}
	
	@Override
	public String toSource() {
		return "\"" + value + "\"";
	}

}
