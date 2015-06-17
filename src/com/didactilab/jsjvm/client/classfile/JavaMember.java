package com.didactilab.jsjvm.client.classfile;

import java.io.IOException;

import com.didactilab.jsjvm.client.debug.IndentedPrinter;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;
import com.didactilab.jsjvm.client.runtime.InvalidClassFileFormatException;

public abstract class JavaMember {

	private final JavaClass javaClass;
	
	private int accessFlags;
	private String name;
	private String descriptor;
	
	private Attributes attributes;
	
	public JavaMember(JavaClass javaClass) {
		this.javaClass = javaClass;
		attributes = new Attributes(javaClass.getConstantPool());
	}
	
	public void read(Reader reader) throws IOException, InvalidClassFileFormatException {
		accessFlags = reader.readUInt16();
		
		if ((accessFlags & getAllowedAccessFlagsMask()) != accessFlags) {
			throw new InvalidClassFileFormatException("The access flags of a " + getMemberTypeName() + " is not valid : " + Integer.toHexString(accessFlags));
		}
		
		name = javaClass.getConstantPool().getString(reader.readUInt16());
		descriptor = javaClass.getConstantPool().getString(reader.readUInt16());
		attributes.read(reader);
	}
	
	public void print(Printer printer) {
		printer.println(getMemberTypeName(), " ", name, ":", descriptor);
		attributes.print(new IndentedPrinter(printer, "   "));
		
	}
	
	protected abstract int getAllowedAccessFlagsMask();
	protected abstract String getMemberTypeName();
	
}
