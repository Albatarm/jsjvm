package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.JavaField;
import com.didactilab.jsjvm.client.classfile.descriptor.DescType;
import com.didactilab.jsjvm.client.classfile.descriptor.DescriptorParser;

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
	public DescType getType() {
		DescriptorParser parser = new DescriptorParser(field.getDescriptor());
		return parser.parseField();
	}

}
