package com.didactilab.jsjvm.client.flow;

public class FlowReturn implements FlowObject {

	private final FlowObject obj;

	public FlowReturn(FlowObject obj) {
		this.obj = obj;
	}
	
	@Override
	public String toSource() {
		return "return " + obj.toSource();
	}

}
