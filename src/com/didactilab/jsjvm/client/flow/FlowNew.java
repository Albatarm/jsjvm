package com.didactilab.jsjvm.client.flow;

import java.util.List;

import com.didactilab.jsjvm.client.classfile.JavaClass;

public class FlowNew implements FlowObject {

	private final JavaClass clazz;
	private List<FlowObject> parameters;

	public FlowNew(JavaClass clazz) {
		this.clazz = clazz;
	}
	
	public void setParameters(List<FlowObject> parameters) {
		this.parameters = parameters;
	}
	
	@Override
	public String toSource() {
		SourceBuilder sb = new SourceBuilder();
		sb.append("new ").append(clazz.getJavaName());
		sb.append("(");
		if (parameters != null && !parameters.isEmpty()) {
			sb.appendJoin(", ", parameters);
		}
		sb.append(")");
		return sb.toSource();
	}

	public JavaClass getClazz() {
		return clazz;
	}
	
}
