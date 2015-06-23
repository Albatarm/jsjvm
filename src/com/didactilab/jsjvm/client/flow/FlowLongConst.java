package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.PrimitiveType;
import com.didactilab.jsjvm.client.classfile.Type;

public class FlowLongConst extends AbstractFlowObject implements FlowValue {

	private final long value;

	public FlowLongConst(long value) {
		this.value = value;
	}
	
	@Override
	public String toSource() {
		return String.valueOf(value);
	}

	@Override
	public Type getType() {
		return PrimitiveType.LONG;
	}

}
