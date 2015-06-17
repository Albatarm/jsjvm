package com.didactilab.jsjvm.client.classfile.attribute;

import java.io.IOException;

import com.didactilab.jsjvm.client.Factory;
import com.didactilab.jsjvm.client.classfile.Attribute;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;
import com.didactilab.jsjvm.client.runtime.InvalidClassFileFormatException;
import com.didactilab.jsjvm.client.runtime.constant.ConstantPool;

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
			InvalidClassFileFormatException {
		super.read(constants, reader);
		constant = constants.getString(reader.readUInt16());
	}

	@Override
	public void print(Printer printer) {
		printer.println("constant " + constant);
	}

}
