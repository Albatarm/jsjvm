package com.didactilab.jsjvm.client.classfile.attribute;

import java.io.IOException;

import com.didactilab.jsjvm.client.Factory;
import com.didactilab.jsjvm.client.classfile.Attribute;
import com.didactilab.jsjvm.client.classfile.Attributes;
import com.didactilab.jsjvm.client.classfile.OpCodeData;
import com.didactilab.jsjvm.client.debug.IndentedPrinter;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;
import com.didactilab.jsjvm.client.runtime.InvalidClassFileFormatException;
import com.didactilab.jsjvm.client.runtime.constant.ClassConstant;
import com.didactilab.jsjvm.client.runtime.constant.ConstantPool;

public class Code extends Attribute {
	
	public static class Exception {
		
		private final int startPc;
		private final int endPc;
		private final int handlerPc;
		private ClassConstant catchType;
		
		public Exception(Reader reader, ConstantPool constants) throws IOException {
			this.startPc = reader.readUInt16();
			this.endPc = reader.readUInt16();
			this.handlerPc = reader.readUInt16();
			this.catchType = constants.get(reader.readUInt16(), ClassConstant.class);
		}
		
	}

	public static final String NAME = "Code";
	
	public static final Factory<Attribute> FACTORY = new Factory<Attribute>() {
		@Override
		public Attribute create() {
			return new Code();
		}
	};
	
	private int maxStack;
	private int maxLocals;
	
	private byte[] code;
	
	private Exception[] exceptionTable;
	private Attributes attributes;
	
	private ConstantPool constants;
	
	public Code() {
		super(NAME);
	}
	
	@Override
	public void read(ConstantPool constants, Reader reader) throws IOException,
			InvalidClassFileFormatException {
		this.constants = constants;
		super.read(constants, reader);
		maxStack = reader.readUInt16();
		maxLocals = reader.readUInt16();
		long codeSize = reader.readUInt32();
		code = reader.readBytes((int) codeSize);
		int exceptionTableLength = reader.readUInt16();
		exceptionTable = new Exception[exceptionTableLength];
		for (int i = 0; i < exceptionTableLength; i++) {
			exceptionTable[i] = new Exception(reader, constants);
		}
		attributes = new Attributes(constants);
		attributes.read(reader);
	}

	@Override
	public void print(Printer printer) {
		printer.println("max stack : ", maxStack);
		printer.println("max locals : ", maxLocals);
		printer.println("code :");
		Printer p = new IndentedPrinter(printer, "   ");
		int pos = 0;
		while (pos < code.length) {
			int op = code[pos++] & 0xFF;
			OpCodeData opCode = OpCodeData.valueOfCode(op);
			p.print(opCode.name);
			pos += opCode.getParamSize();
			p.println();
		}
		
	}

}
