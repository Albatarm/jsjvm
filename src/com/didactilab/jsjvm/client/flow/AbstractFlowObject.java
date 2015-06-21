package com.didactilab.jsjvm.client.flow;

public abstract class AbstractFlowObject implements FlowObject {

	@Override
	public String toString() {
		return getClass().getSimpleName() + "|" + toSource();
	}
	
}
