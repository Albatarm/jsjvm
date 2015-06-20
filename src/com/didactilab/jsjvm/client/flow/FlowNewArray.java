package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.attribute.ArrayType;

public class FlowNewArray implements FlowObject {

	private final ArrayType atype;
	private final FlowObject length;

	public FlowNewArray(ArrayType atype, FlowObject length) {
		this.atype = atype;
		this.length = length;
	}
	
	@Override
	public String toSource() {
		return "new " + atype.name + "[" + length.toSource() + "]";
	}

}
