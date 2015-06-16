package com.didactilab.jsjvm.client.runtime.constant;

import java.io.IOException;

import com.didactilab.jsjvm.client.reader.Reader;

public class ClassConstant extends AbstractConstant {

	private final int index;
	private String name;
	
	public ClassConstant(ConstantPool owner, Reader reader) throws IOException {
		super(owner);
		index = reader.readUInt16();
	}
	
	public String getName() {
		if (name == null) {
			name = getString(index);
		}
		return name;
	}
	
	@Override
	public String toString() {
		return "[class] " + getName();
	}
	
}
