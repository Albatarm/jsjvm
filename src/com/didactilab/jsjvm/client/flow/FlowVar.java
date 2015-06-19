package com.didactilab.jsjvm.client.flow;

public abstract class FlowVar implements FlowObject {
	
	public final String name;
	
	public FlowVar(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public String toSource() {
		return name;
	}
	
}
