package com.didactilab.jsjvm.client.classfile.attribute;

import java.io.IOException;

import com.didactilab.jsjvm.client.classfile.ClassFormatException;
import com.didactilab.jsjvm.client.classfile.constant.ClassConstant;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.classfile.constant.NameAndTypeConstant;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;
import com.didactilab.jsjvm.client.util.Factory;

public class EnclosingMethod extends Attribute {

	public static final String NAME = "EnclosingMethod";
	
	public static final Factory<Attribute> FACTORY = new Factory<Attribute>() {
		@Override
		public Attribute create() {
			return new EnclosingMethod();
		}
	};
	
	private ClassConstant clazz;
	private NameAndTypeConstant method;
	
	public EnclosingMethod() {
		super(NAME);
	}
	
	@Override
	public void read(ConstantPool constants, Reader reader) throws IOException,
			ClassFormatException {
		super.read(constants, reader);
		clazz = constants.get(reader.readUInt16(), ClassConstant.class);
		int methodIndex = reader.readUInt16();
		method = methodIndex != 0 ? constants.get(methodIndex, NameAndTypeConstant.class) : null;
	}

	@Override
	public void print(Printer printer) {
		printer.println("class " + clazz);
		if (method != null) {
			printer.println("method " + method);
		}
	}

}
