package com.didactilab.jsjvm.client.classfile.descriptor;

public abstract class AbstractDescType implements DescType {
	
	@Override
	public String toString() {
		return getDescriptor();
	}

}
