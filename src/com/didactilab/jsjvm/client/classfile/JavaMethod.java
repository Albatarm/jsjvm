package com.didactilab.jsjvm.client.classfile;

public class JavaMethod extends JavaMember {

	public JavaMethod(JavaClass javaClass) {
		super(javaClass);
	}

	@Override
	protected int getAllowedAccessFlagsMask() {
		return AccessFlags.ACC_METHOD;
	}

	@Override
	protected String getMemberTypeName() {
		return "method";
	}
	
}
