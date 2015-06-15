package com.didactilab.jsjvm.client.runtime.constant;

import java.util.ArrayList;

import com.didactilab.jsjvm.client.reader.Reader;

public class ConstantPool {
	
	private ArrayList<Constant> list;
	
	public void read(Reader reader) {
		int length = reader.readUInt16();
		list = new ArrayList<>(length);
		
		for (int i=0; i<length; i++) {
			int type = reader.readUInt8();
			Constant constant;
			switch (type) {
				case Constant.CONSTANT_CLASS: constant = new ClassInfoConstant(this, reader); break;
				case Constant.CONSTANT_FIELDREF: constant = new FieldrefInfoConstant(this, reader); break;
				case Constant.CONSTANT_METHODREF: constant = new MethodrefInfoConstant(this, reader); break;
				case Constant.CONSTANT_INTERFACEMETHODREF: constant = new InterfaceMethodrefInfoConstant(this, reader); break;
				case Constant.CONSTANT_STRING: constant = new StringInfoConstant(this, reader); break;
			}
			list.add(constant);
		}
	}
	
	public <T extends Constant> T get(int index, Class<T> constantClass) {
		
	}
	
}
