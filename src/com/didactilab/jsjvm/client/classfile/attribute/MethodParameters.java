package com.didactilab.jsjvm.client.classfile.attribute;

import java.io.IOException;

import com.didactilab.jsjvm.client.Factory;
import com.didactilab.jsjvm.client.classfile.InvalidClassFileFormatException;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;

public class MethodParameters extends Attribute {
	
	public static class MethodParameter {
		public final String name;
		public final int accessFlags;
		
		public MethodParameter(String name, int accessFlags) {
			this.name = name;
			this.accessFlags = accessFlags;
		}
		
		@Override
		public String toString() {
			return (name != null ? name : "[no_name]") + " " + Integer.toHexString(accessFlags);
		}
	}

	public static final String NAME = "MethodParameters";
	
	public static final Factory<Attribute> FACTORY = new Factory<Attribute>() {
		@Override
		public Attribute create() {
			return new MethodParameters();
		}
	};
	
	private MethodParameter[] parameters;
	
	public MethodParameters() {
		super(NAME);
	}
	
	@Override
	public void read(ConstantPool constants, Reader reader) throws IOException,
			InvalidClassFileFormatException {
		super.read(constants, reader);
		int count = reader.readUInt16();
		parameters = new MethodParameter[count];
		for (int i = 0; i < count; i++) {
			int nameIndex = reader.readUInt16();
			int accessFlags = reader.readUInt16();
			parameters[i] = new MethodParameter(nameIndex != 0 ? constants.getString(nameIndex) : null, accessFlags);
		}
	}

	@Override
	public void print(Printer printer) {
		for (MethodParameter param : parameters) {
			printer.println(param);
		}
	}

}
