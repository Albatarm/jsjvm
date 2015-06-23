package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.Type;

public class FlowNegation extends AbstractFlowObject implements FlowValue {

	private final FlowObject value;

	public FlowNegation(FlowObject value) {
		this.value = value;
	}

	@Override
	public String toSource() {
		return "-" + value.toSource();
	}

	@Override
	public Type getType() {
		if (value instanceof FlowValue) {
			return ((FlowValue) value).getType();
		} else {
			throw new IllegalStateException();
		}
	}

}
