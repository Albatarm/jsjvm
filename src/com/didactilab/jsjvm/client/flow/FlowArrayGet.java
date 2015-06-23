package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.Type;

public class FlowArrayGet extends AbstractFlowObject implements FlowValue {

	private final FlowObject arrayRef;
	private final FlowObject index;
	private final Type type;

	public FlowArrayGet(FlowObject arrayRef, FlowObject index, Type type) {
		this.arrayRef = arrayRef;
		this.index = index;
		this.type = type;
	}

	@Override
	public String toSource() {
		return arrayRef.toSource() + "[" + index.toSource() + "]";
	}

	@Override
	public Type getType() {
		return type;
	}

}
