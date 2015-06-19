package com.didactilab.jsjvm.client.classfile.descriptor;

public class ObjectType extends Type {
	public final String name;
	
	public ObjectType(String name) {
		this.name = name;
	}
	
	@Override
	public String getDescriptor() {
		return "L" + name + ";";
	}
}
