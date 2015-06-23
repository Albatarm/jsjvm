package com.didactilab.jsjvm.client.flow;

public class FlowLongCond extends AbstractFlowObject implements FlowIntermediate {

	public final FlowObject left;
	public final FlowObject right;

	public FlowLongCond(FlowObject left, FlowObject right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String toSource() {
		return "";
	}

}
