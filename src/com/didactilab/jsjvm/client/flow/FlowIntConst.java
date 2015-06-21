package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.descriptor.DescType;
import com.didactilab.jsjvm.client.classfile.descriptor.PrimitiveDescType;

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
	public DescType getType() {
		return new PrimitiveDescType('I');
	}
	
}
