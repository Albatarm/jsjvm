package com.didactilab.jsjvm.client.runtime.constant;


public abstract class AbstractConstant {

	private final ConstantPool owner;

	public AbstractConstant(ConstantPool owner) {
		this.owner = owner;
	}
	
	public ConstantPool getConstantPool() {
		return owner;
	}
	
	protected <T> T getConstant(int index, Class<T> constantClass) {
		return owner.get(index, constantClass);
	}
	
	protected String getString(int index) {
		return getConstant(index, String.class);
	}
	
}
