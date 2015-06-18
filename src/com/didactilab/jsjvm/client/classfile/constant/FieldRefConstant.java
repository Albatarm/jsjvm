package com.didactilab.jsjvm.client.classfile.constant;

import java.io.IOException;

import com.didactilab.jsjvm.client.reader.Reader;

public class FieldRefConstant extends AbstractRefConstant {

	public FieldRefConstant(ConstantPool owner, Reader reader) throws IOException {
		super(owner, reader);
	}
	
	@Override
	protected String getType() {
		return "FieldRef";
	}
	
	@Override
	public String toString() {
		return "[" + getType() + "] " + getNameAndType().getDescriptor() + " " + getClassName().getName() + 
				"::" + getNameAndType().getName();
	}

}
