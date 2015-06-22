package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.JavaField;
import com.didactilab.jsjvm.client.classfile.Type;

public class FlowField extends AbstractFlowObject implements FlowValue {

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
	
	@Override
	public Type getType() {
		return field.getType();
	}

}
