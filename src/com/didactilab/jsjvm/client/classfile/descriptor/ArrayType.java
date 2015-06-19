package com.didactilab.jsjvm.client.classfile.descriptor;

public class ArrayType extends Type {
	public final int dimension;
	public final Type type;
	
	public ArrayType(int dimension, Type type) {
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
