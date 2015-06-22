package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.attribute.CodeArrayType;

public class FlowNewArray implements FlowObject {

	private final CodeArrayType atype;
	private final FlowObject length;

	public FlowNewArray(CodeArrayType atype, FlowObject length) {
		this.atype = atype;
		this.length = length;
	}
	
	@Override
	public String toSource() {
		return "new " + atype.name + "[" + length.toSource() + "]";
	}

}
