package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.Type;

public class FlowInc extends AbstractFlowObject implements FlowValue {

	private final FlowVar var;
	private final int value;

	public FlowInc(FlowVar var, int value) {
		this.var = var;
		this.value = value;
	}

	@Override
	public String toSource() {
		if (value == 1) {
			return var.toSource() + "++";
		} else if (value == -1) {
			return var.toSource() + "--";
		} else if (value < 0) {
			return "(" + var.toSource() + " -= " + value + ")";
		} else if (value > 0) {
			return "(" + var.toSource() + " += " + value + ")";
		} else {
			return var.toSource();
		}
	}

	@Override
	public Type getType() {
		return var.getType();
	}

}
