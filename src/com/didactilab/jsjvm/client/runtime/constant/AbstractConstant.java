package com.didactilab.jsjvm.client.runtime.constant;

public abstract class AbstractConstant implements Constant {

	private final ConstantPool owner;

	public AbstractConstant(ConstantPool owner) {
		this.owner = owner;
	}
	
	public ConstantPool getConstantPool() {
		return owner;
	}
	
	protected <T extends Constant> T getConstant(int index, Class<T> constantClass) {
		return owner.get(index, constantClass);
	}
	
}
