package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.JavaMethod;
import com.didactilab.jsjvm.client.classfile.Type;
import com.didactilab.jsjvm.client.classfile.JavaMethod.Parameter;

public class FlowParameter extends AbstractFlowObject implements FlowVar, FlowValue {

	private final Parameter param;

	public FlowParameter(JavaMethod.Parameter param) {
		this.param = param;
	}
	
	@Override
	public String toSource() {
		return param.name;
	}
	
	@Override
	public Type getType() {
		return param.getType();
	}

}
