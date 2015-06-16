package com.didactilab.jsjvm.client.runtime.constant;

import java.io.IOException;

import com.didactilab.jsjvm.client.reader.Reader;

public class InterfaceMethodRefConstant extends AbstractRefConstant {

	public InterfaceMethodRefConstant(ConstantPool owner, Reader reader) throws IOException {
		super(owner, reader);
	}
	
	@Override
	protected String getType() {
		return "InterfaceMethodRef";
	}

}
