package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.JavaField;

public class FlowField extends AbstractFlowObject {

	private final FlowObject instance;
	private final JavaField field;

	public FlowField(FlowObject instance, JavaField field) {
		this.instance = instance;
		this.field = field;
	}
	
	@Override
	public String toSource() {
		return instance.toSource() + "." + field.getName();
	}

}
