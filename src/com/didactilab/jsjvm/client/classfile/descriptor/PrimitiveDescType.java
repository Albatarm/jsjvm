package com.didactilab.jsjvm.client.classfile.descriptor;

import com.didactilab.jsjvm.client.classfile.PrimitiveType;

public class PrimitiveDescType extends DescType {
	
	public final char type;
	public final PrimitiveType primitive; 
	
	public PrimitiveDescType(char type) {
		this.type = type;
		primitive = PrimitiveType.valueOf(type);
	}
	
	@Override
	public String getDescriptor() {
		return String.valueOf(type);
	}
	
	public PrimitiveType getPrimitive() {
		return primitive;
	}
	
	public boolean isVoid() {
		return type == 'V';
	}
}
