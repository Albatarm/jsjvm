package com.didactilab.jsjvm.client.classfile;

import java.io.IOException;
import java.util.HashSet;

import com.didactilab.jsjvm.client.classfile.attribute.Attribute;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;

public class UnknownAttribute extends Attribute {
	
	public static final HashSet<String> UNKNOWN_ATTRIBUTES = new HashSet<>();
	
	public UnknownAttribute(String name) {
		super(name);
		UNKNOWN_ATTRIBUTES.add(name);
	}
	
	@Override
	public void read(ConstantPool constants, Reader reader) throws IOException,
			ClassFormatException {
		super.read(constants, reader);
		reader.skip((int) getSize()); 
	}
	
	@Override
	public void print(Printer printer) {
		printer.println("size ", getSize());
	}
	
}
