package com.didactilab.jsjvm.client.flow;

import java.util.List;

import com.didactilab.jsjvm.client.classfile.JavaMethod;

public class FlowCall extends AbstractFlowObject {

	private final JavaMethod method;
	private final FlowObject instance;
	private final List<FlowObject> parameters;

	public FlowCall(JavaMethod method, List<FlowObject> parameters) {
		this(method, null, parameters);
	}
	
	public FlowCall(JavaMethod method, FlowObject instance, List<FlowObject> parameters) {
		this.method = method;
		this.instance = instance;
		this.parameters = parameters;
		
	}
	
	@Override
	public String toSource() {
		SourceBuilder sb = new SourceBuilder();
		if (method.isStatic()) {
			sb.append(method.getJavaClass().getJavaName());
		} else {
			sb.append(instance);
		}
		sb.append(".").append(method.getName());
		sb.append("(");
		sb.appendJoin(", ", parameters);
		sb.append(")");
		return sb.toSource();
	}
	
}
