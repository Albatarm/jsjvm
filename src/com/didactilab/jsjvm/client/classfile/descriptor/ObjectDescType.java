package com.didactilab.jsjvm.client.classfile.descriptor;

public class ObjectDescType extends AbstractDescType {
	public final String name;
	
	public ObjectDescType(String name) {
		this.name = name;
	}
	
	@Override
	public String getDescriptor() {
		return "L" + name + ";";
	}
}
