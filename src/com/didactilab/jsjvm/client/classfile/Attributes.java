package com.didactilab.jsjvm.client.classfile;

import java.io.IOException;
import java.util.HashMap;

import com.didactilab.jsjvm.client.Factory;
import com.didactilab.jsjvm.client.classfile.attribute.Code;
import com.didactilab.jsjvm.client.classfile.attribute.SourceFile;
import com.didactilab.jsjvm.client.debug.IndentedPrinter;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;
import com.didactilab.jsjvm.client.runtime.InvalidClassFileFormatException;
import com.didactilab.jsjvm.client.runtime.constant.ConstantPool;

public class Attributes {
	
	private static HashMap<String, Factory<Attribute>> factories = new HashMap<>();
	
	static {
		factory(SourceFile.NAME, SourceFile.FACTORY);
		factory(Code.NAME, Code.FACTORY);
	}
	
	private static void factory(String name, Factory<Attribute> factory) {
		factories.put(name, factory);
	}

	private final ConstantPool constants;
	private Attribute[] attributes;

	public Attributes(ConstantPool constants) {
		this.constants = constants;
	}
	
	public void read(Reader reader) throws IOException, InvalidClassFileFormatException {
		int count = reader.readUInt16();
		attributes = new Attribute[count];
		for (int i = 0; i < count; i++) {
			String name = constants.getString(reader.readUInt16());
			Factory<Attribute> factory = factories.get(name);
			Attribute attr = factory != null ? factory.create() : new UnknownAttribute(name);
			attr.read(constants, reader);
			attributes[i] = attr;
		}
	}
	
	public void print(Printer printer) {
		printer.println("attributes[", attributes.length, "]:");
		for (int i = 0; i < attributes.length; i++) {
			Attribute attr = attributes[i];
			printer.println("   - ", attr.getName(), " : ");
			attr.print(new IndentedPrinter(printer, "      "));
		}
	}
	
}
