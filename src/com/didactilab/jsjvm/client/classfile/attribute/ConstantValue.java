package com.didactilab.jsjvm.client.classfile.attribute;

import java.io.IOException;

import com.didactilab.jsjvm.client.classfile.ClassFormatException;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;
import com.didactilab.jsjvm.client.util.Factory;

public class ConstantValue extends Attribute {

	public static final String NAME = "ConstantValue";
	
	public static final Factory<Attribute> FACTORY = new Factory<Attribute>() {
		@Override
		public Attribute create() {
			return new ConstantValue();
		}
	};
	
	private Object constant;
	
	public ConstantValue() {
		super(NAME);
	}
	
	@Override
	public void read(ConstantPool constants, Reader reader) throws IOException,
			ClassFormatException {
		super.read(constants, reader);
		constant = constants.get(reader.readUInt16(), Object.class);
	}

	@Override
	public void print(Printer printer) {
		printer.println("constant " + constant);
	}

}
