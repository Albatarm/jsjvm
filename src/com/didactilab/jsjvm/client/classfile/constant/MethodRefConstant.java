package com.didactilab.jsjvm.client.classfile.constant;

import java.io.IOException;

import com.didactilab.jsjvm.client.reader.Reader;

public class MethodRefConstant extends AbstractRefConstant {

	public MethodRefConstant(ConstantPool owner, Reader reader) throws IOException {
		super(owner, reader);
	}
	
	@Override
	protected String getType() {
		return "MethodRef";
	}

}
