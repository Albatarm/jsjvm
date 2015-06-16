package com.didactilab.jsjvm.client.runtime.constant;

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

}
