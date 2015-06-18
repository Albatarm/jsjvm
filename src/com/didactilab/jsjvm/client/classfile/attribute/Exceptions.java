package com.didactilab.jsjvm.client.classfile.attribute;

import java.io.IOException;

import com.didactilab.jsjvm.client.Factory;
import com.didactilab.jsjvm.client.classfile.InvalidClassFileFormatException;
import com.didactilab.jsjvm.client.classfile.constant.ClassConstant;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;

public class Exceptions extends Attribute {

	public static final String NAME = "Exceptions";
	
	public static final Factory<Attribute> FACTORY = new Factory<Attribute>() {
		@Override
		public Attribute create() {
			return new Exceptions();
		}
	};
	
	private ClassConstant[] exceptions;
	
	public Exceptions() {
		super(NAME);
	}
	
	@Override
	public void read(ConstantPool constants, Reader reader) throws IOException,
			InvalidClassFileFormatException {
		super.read(constants, reader);
		int length = reader.readUInt16();
		exceptions = new ClassConstant[length];
		for (int i = 0; i < length; i++) {
			exceptions[i] = constants.get(reader.readUInt16(), ClassConstant.class);
		}
	}

	@Override
	public void print(Printer printer) {
		for (ClassConstant classConstant : exceptions) {
			printer.println(classConstant);
		}
	}
	
	public ClassConstant[] getExceptions() {
		return exceptions;
	}

}
