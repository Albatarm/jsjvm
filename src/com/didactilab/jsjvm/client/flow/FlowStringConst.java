package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.Type;

public class FlowStringConst extends AbstractFlowObject implements FlowValue {

	private final String value;
	private final Type stringType;

	public FlowStringConst(String value, Type stringType) {
		this.value = value;
		this.stringType = stringType;
	}
	
	@Override
	public String toSource() {
		return "\"" + value + "\"";
	}
	
	@Override
	public Type getType() {
		return stringType;
	}

}
