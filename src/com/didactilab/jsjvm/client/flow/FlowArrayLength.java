package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.PrimitiveType;
import com.didactilab.jsjvm.client.classfile.Type;

public class FlowArrayLength extends AbstractFlowObject implements FlowValue {

	private final FlowObject obj;

	public FlowArrayLength(FlowObject obj) {
		this.obj = obj;
	}
	
	@Override
	public String toSource() {
		return obj.toSource() + ".length";
	}

	@Override
	public Type getType() {
		return PrimitiveType.INT;
	}

}
