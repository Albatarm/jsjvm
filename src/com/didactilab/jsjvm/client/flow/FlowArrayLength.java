package com.didactilab.jsjvm.client.flow;

public class FlowArrayLength implements FlowObject {

	private final FlowObject obj;

	public FlowArrayLength(FlowObject obj) {
		this.obj = obj;
	}
	
	@Override
	public String toSource() {
		return obj.toSource() + ".length";
	}

}
