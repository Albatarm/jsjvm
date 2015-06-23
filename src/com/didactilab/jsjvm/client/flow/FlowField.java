package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.JavaField;
import com.didactilab.jsjvm.client.classfile.Type;

public class FlowField extends AbstractMemberFlowObject implements FlowValue {

	private final FlowObject instance;
	private final JavaField field;

	/** Static part */
	public FlowField(JavaField field) {
		this(null, field);
	}
	
	public FlowField(FlowObject instance, JavaField field) {
		super(null);
		this.instance = instance;
		this.field = field;
	}
	
	@Override
	public String toSource() {
		return (instance != null ? instance.toSource() : field.getJavaClass().getJavaName()) + "." + field.getName();
	}
	
	@Override
	public Type getType() {
		return field.getType();
	}

}
