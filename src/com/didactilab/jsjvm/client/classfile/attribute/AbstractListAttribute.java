package com.didactilab.jsjvm.client.classfile.attribute;

import com.didactilab.jsjvm.client.debug.Printer;

public abstract class AbstractListAttribute<T> extends Attribute {

	public AbstractListAttribute(String name) {
		super(name);
	}
	
	@Override
	public void print(Printer printer) {
		for (T item : getList()) {
			printer.println(item);
		}
	}
	
	protected abstract T[] getList();

	public T get(int index) {
		return getList()[index];
	}
	
	public int getCount() {
		return getList().length;
	}
	
}
