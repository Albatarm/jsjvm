package com.didactilab.jsjvm.client.classfile.descriptor;

public class ArrayDescType extends AbstractDescType {
	public final int dimension;
	public final DescType type;
	
	public ArrayDescType(int dimension, DescType type) {
		this.dimension = dimension;
		this.type = type;
	}
	
	@Override
	public String getDescriptor() {
		String dim = "";
		for (int i=0; i<dimension; i++) {
			dim += "[";
		}
		return dim + type.getDescriptor();
	}
}
