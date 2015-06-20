package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.descriptor.Type;

public class FlowDeclareVar implements FlowVar {

	private final FlowVar local;
	private final Type type;

	public FlowDeclareVar(FlowLocal local, Type type) {
		this.local = local;
		this.type = type;
	}
	
	@Override
	public String toSource() {
		return getStringType() + " " + local.toSource();
	}
	
	private String getStringType() {
		return type.getDescriptor();
	}

}
