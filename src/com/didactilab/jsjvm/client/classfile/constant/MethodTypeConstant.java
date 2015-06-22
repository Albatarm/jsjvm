package com.didactilab.jsjvm.client.classfile.constant;

import java.io.IOException;

import com.didactilab.jsjvm.client.reader.Reader;

public class MethodTypeConstant extends AbstractConstant {
	
	private int typeIndex;
	private String type;
	
	public MethodTypeConstant(ConstantPool owner, Reader reader) throws IOException {
		super(owner);
		typeIndex = reader.readUInt16();
	}

	public String getType() {
		if (type == null) {
			type = getString(typeIndex);
		}
		return type;
	}
	
}
