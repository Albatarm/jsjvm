package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.Type;

public class FlowLocal extends AbstractFlowObject implements FlowVar {

	private final String name;
	private final Type type;

	public FlowLocal(String name, Type type) {
		this.name = name;
		this.type = type;
	}
	
	@Override
	public String toSource() {
		return name;
	}
	
	@Override
	public Type getType() {
		return type;
	}

}
