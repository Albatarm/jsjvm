package com.didactilab.jsjvm.client.classfile;


public class JavaField extends JavaMember {

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

	
	
}
