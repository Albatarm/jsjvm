package com.didactilab.jsjvm.client.classfile.descriptor;

public abstract class DescType {
	public abstract String getDescriptor();
	
	@Override
	public String toString() {
		return getDescriptor();
	}
}
