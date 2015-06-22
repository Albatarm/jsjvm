package com.didactilab.jsjvm.client.classfile;

public class ArrayType implements Type {

	private final int dimension;
	private final Type componentType;
	private final String descriptor;
	
	@Deprecated
	private String javaName;
	
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
		if (javaName == null) {
			String name = componentType.getJavaName();
			for (int i=0; i<dimension; i++) {
				name += "[]";
			}
			javaName = name;
		} 
		return javaName;
	}
	
}
