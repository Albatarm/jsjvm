package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.PrimitiveType;
import com.didactilab.jsjvm.client.classfile.Type;

public class FlowIntConst extends AbstractFlowObject implements FlowValue {

	private final int value;

	public FlowIntConst(int value) {
		this.value = value;
	}
	
	@Override
	public String toSource() {
		return String.valueOf(value);
	}
	
	@Override
	public Type getType() {
		return PrimitiveType.INT;
	}
	
}
