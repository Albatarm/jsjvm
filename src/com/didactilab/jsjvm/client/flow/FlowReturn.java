package com.didactilab.jsjvm.client.flow;

public class FlowReturn extends AbstractFlowObject implements FlowFinalInstruction {

	private final FlowObject obj;

	public FlowReturn(FlowObject obj) {
		this.obj = obj;
	}
	
	@Override
	public String toSource() {
		return "return " + obj.toSource();
	}

}
