package com.didactilab.jsjvm.client.classfile;

import com.didactilab.jsjvm.client.classfile.descriptor.DescType;
import com.didactilab.jsjvm.client.classfile.descriptor.DescriptorParser;


public class JavaField extends JavaMember {
	
	private Type type;

	public JavaField(JavaClass javaClass) {
		super(javaClass);
	}

	@Override
	protected int getAllowedAccessFlagsMask() {
		return AccessFlags.ACC_FIELD;
	}

	@Override
	protected String getMemberTypeName() {
		return "field";
	}
	
	@Override
	public String toString() {
		return getDescriptor() + " " + getName();
	}

	public void resolve() throws ClassNotFoundException {
		DescriptorParser parser = new DescriptorParser(getDescriptor());
		DescType t = parser.parseField();
		type = toType(t);
	}
	
	public Type getType() {
		return type;
	}
	
}
