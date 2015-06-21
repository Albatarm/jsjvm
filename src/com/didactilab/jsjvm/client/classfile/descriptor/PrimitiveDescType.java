package com.didactilab.jsjvm.client.classfile.descriptor;

public class PrimitiveDescType extends DescType {
	public final char type;
	
	public PrimitiveDescType(char type) {
		this.type = type;
	}
	
	@Override
	public String getDescriptor() {
		return String.valueOf(type);
	}
	
	public String getTypeName() {
		return String.valueOf(type);
	}
}
