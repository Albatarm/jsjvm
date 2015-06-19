package com.didactilab.jsjvm.client.classfile.descriptor;

public class PrimitiveType extends Type {
	public final char type;
	
	public PrimitiveType(char type) {
		this.type = type;
	}
	
	@Override
	public String getDescriptor() {
		return String.valueOf(type);
	}
}
