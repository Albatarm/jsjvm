package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.Type;


public class FlowDeclareVar implements FlowVar {

	private final FlowVar local;
	private final FlowType type;

	public FlowDeclareVar(FlowLocal local) {
		this.local = local;
		this.type = new FlowType(local.getType().getJavaName());
	}
	
	@Override
	public String toSource() {
		return type.toSource() + " " + local.toSource();
	}

	@Override
	public Type getType() {
		return local.getType();
	}

}
