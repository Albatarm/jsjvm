package com.didactilab.jsjvm.client.flow;

public class FlowThis extends AbstractFlowObject implements FlowVar {

	@Override
	public String toSource() {
		return "this";
	}
	
	@Override
	public String toString() {
		return toSource();
	}

}
