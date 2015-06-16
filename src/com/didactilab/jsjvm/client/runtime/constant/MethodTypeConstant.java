package com.didactilab.jsjvm.client.runtime.constant;

import java.io.IOException;

import com.didactilab.jsjvm.client.reader.Reader;

public class MethodTypeConstant extends AbstractConstant {
	
	public final String type;
	
	public MethodTypeConstant(ConstantPool owner, Reader reader) throws IOException {
		super(owner);
		int index = reader.readUInt16();
		type = getString(index);
	}

}
