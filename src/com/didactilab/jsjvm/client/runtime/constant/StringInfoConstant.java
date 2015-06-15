package com.didactilab.jsjvm.client.runtime.constant;

import com.didactilab.jsjvm.client.reader.Reader;

public class StringInfoConstant extends AbstractValueConstant {

	private final int utf8Index;
	
	public StringInfoConstant(ConstantPool owner, Reader reader) {
		super(owner);
		utf8Index = reader.readUInt16();
	}

	public Utf8InfoConstant getString() {
		return getConstantPool().get(utf8Index, Utf8InfoConstant.class);
	}
	
	public String value() {
		return getString().value();
	}
	
}
