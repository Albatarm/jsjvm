package com.didactilab.jsjvm.client.flow;

import java.util.List;

import com.didactilab.jsjvm.client.classfile.constant.MethodRefConstant;

public class FlowCall implements FlowObject {

	private MethodRefConstant method;
	private FlowObject instance;
	private List<FlowObject> parameters;

	public FlowCall(MethodRefConstant method, FlowObject instance, List<FlowObject> parameters) {
		this.method = method;
		this.instance = instance;
		this.parameters = parameters;
		
	}
	
	@Override
	public String toString() {
		return toSource();
	}
	
	@Override
	public String toSource() {
		SourceBuilder sb = new SourceBuilder();
		sb.append(instance).append(".").append(method.getNameAndType().getName());
		sb.append("(");
		sb.appendJoin(", ", parameters);
		sb.append(")");
		return sb.toSource();
	}
	
}
