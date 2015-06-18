package com.didactilab.jsjvm.client.classfile.attribute;

import java.io.IOException;

import com.didactilab.jsjvm.client.classfile.InvalidClassFileFormatException;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;

public abstract class Attribute {

	private final String name;
	private long size;
	
	public Attribute(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public long getSize() {
		return size;
	}
	
	public abstract void print(Printer printer);
	
	public void read(ConstantPool constants, Reader reader) throws IOException, InvalidClassFileFormatException {
		size = reader.readUInt32();
	}
	
}
