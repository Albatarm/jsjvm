package com.didactilab.jsjvm.client.flow;

public class FlowLocal implements FlowVar {

	private final String name;

	public FlowLocal(String name) {
		this.name = name;
	}
	
	@Override
	public String toSource() {
		return name;
	}

}
