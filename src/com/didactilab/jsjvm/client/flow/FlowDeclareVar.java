package com.didactilab.jsjvm.client.flow;


public class FlowDeclareVar implements FlowVar {

	private final FlowVar local;
	private final FlowType type;

	public FlowDeclareVar(FlowLocal local, FlowType type) {
		this.local = local;
		this.type = type;
	}
	
	@Override
	public String toSource() {
		return type.toSource() + " " + local.toSource();
	}

}
