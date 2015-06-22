package com.didactilab.jsjvm.client.classfile;

public class ArrayType implements Type {

	private final int dimension;
	private final Type componentType;
	private final String descriptor;
	
	public ArrayType(int dimension, Type componentType, String descriptor) {
		this.dimension = dimension;
		this.componentType = componentType;
		this.descriptor = descriptor;
	}
	
	public Type getComponentType() {
		return componentType;
	}
	
	public int getDimension() {
		return dimension;
	}
	
	public String getDescriptor() {
		return descriptor;
	}
	
	@Deprecated
	public String getJavaName() {
		//String name = componentType instanceof PrimitiveType ? ((PrimitiveType) componentType).name : ()
	}
	
}
