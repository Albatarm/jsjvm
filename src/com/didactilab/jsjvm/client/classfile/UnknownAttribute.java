package com.didactilab.jsjvm.client.classfile;

import java.io.IOException;

import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;
import com.didactilab.jsjvm.client.runtime.InvalidClassFileFormatException;
import com.didactilab.jsjvm.client.runtime.constant.ConstantPool;

public class UnknownAttribute extends Attribute {
	
	public UnknownAttribute(String name) {
		super(name);
	}
	
	@Override
	public void read(ConstantPool constants, Reader reader) throws IOException,
			InvalidClassFileFormatException {
		super.read(constants, reader);
		reader.skip((int) getSize()); 
	}
	
	@Override
	public void print(Printer printer) {
		printer.println("size ", getSize());
	}
	
}