package com.didactilab.jsjvm.client.runtime.constant;

import com.didactilab.jsjvm.client.reader.Reader;

public class ClassInfoConstant extends AbstractConstant {
	
	private final int index;
	private Utf8InfoConstant utf8;
	
	public ClassInfoConstant(ConstantPool owner, Reader reader) {
		super(owner);
		index = reader.readUInt16();
	}
	
	public Utf8InfoConstant getName() {
		if (utf8 == null) {
			utf8 = getConstantPool().get(index, Utf8InfoConstant.class);
		}
		return utf8;
	}
	
}
