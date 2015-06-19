package com.didactilab.jsjvm.client.classfile.attribute;

import java.io.IOException;

import com.didactilab.jsjvm.client.Factory;
import com.didactilab.jsjvm.client.classfile.InvalidClassFileFormatException;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;

public class SourceDebugExtension extends Attribute {

	public static final String NAME = "SourceDebugExtension";
	
	public static final Factory<Attribute> FACTORY = new Factory<Attribute>() {
		@Override
		public Attribute create() {
			return new SourceDebugExtension();
		}
	};
	
	private String debugExtension;
	
	public SourceDebugExtension() {
		super(NAME);
	}
	
	@Override
	public void read(ConstantPool constants, Reader reader) throws IOException,
			InvalidClassFileFormatException {
		super.read(constants, reader);
		byte[] utf8 = reader.readBytes(getSize());
		debugExtension = new String(utf8, "UTF-8");
	}

	@Override
	public void print(Printer printer) {
		printer.println(debugExtension);
	}

}
