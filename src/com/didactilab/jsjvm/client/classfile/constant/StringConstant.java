package com.didactilab.jsjvm.client.classfile.constant;

import java.io.IOException;

import com.didactilab.jsjvm.client.reader.Reader;

public class StringConstant extends AbstractConstant {

	private final int stringIndex;
	private String string;
	
	public StringConstant(ConstantPool owner, Reader reader) throws IOException {
		super(owner);
		stringIndex = reader.readUInt16();
	}
	
	public String value() {
		if (string == null) {
			string = getString(stringIndex);
		}
		return string;
	}
	
	@Override
	public String toString() {
		return "[String] \"" + value() + "\"";
	}

}
