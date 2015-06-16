package com.didactilab.jsjvm.client.runtime.constant;

import java.io.IOException;

import com.didactilab.jsjvm.client.reader.Reader;

public class NameAndTypeConstant extends AbstractConstant {
	
	private final int nameIndex;
	private final int descIndex;
	
	private String name;
	private String descriptor;
	
	public NameAndTypeConstant(ConstantPool owner, Reader reader) throws IOException {
		super(owner);
		nameIndex = reader.readUInt16();
		descIndex = reader.readUInt16();
	}
	
	public String getName() {
		if (name == null) {
			name = getString(nameIndex);
		}
		return name;
	}
	
	public String getDescriptor() {
		if (descriptor == null) {
			descriptor = getString(descIndex);
		}
		return descriptor;
	}
	
	@Override
	public String toString() {
		return "[NameAndType] " + getName() + getDescriptor();
	}

}
