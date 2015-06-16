package com.didactilab.jsjvm.client.classfile;

import java.io.IOException;

import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;
import com.didactilab.jsjvm.client.runtime.InvalidClassFileFormatException;
import com.didactilab.jsjvm.client.runtime.constant.ConstantPool;

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
