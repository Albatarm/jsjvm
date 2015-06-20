package com.didactilab.jsjvm.client.flow;

public class FlowAssign implements FlowObject {

	private final FlowObject var;
	private final FlowObject value;

	public FlowAssign(FlowObject var, FlowObject value) {
		this.var = var;
		this.value = value;
	}
	
	@Override
	public String toSource() {
		return var.toSource() + " = " + value.toSource();
	}

}
