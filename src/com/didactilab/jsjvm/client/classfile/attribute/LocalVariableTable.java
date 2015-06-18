package com.didactilab.jsjvm.client.classfile.attribute;

import java.io.IOException;

import com.didactilab.jsjvm.client.Factory;
import com.didactilab.jsjvm.client.classfile.InvalidClassFileFormatException;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;

public class LocalVariableTable extends Attribute {
	
	public static class Variable {
		
		public final int startPc;
		public final int length;
		public final String name;
		public final String descriptor;
		public final int index;
		
		public Variable(int startPc, int length, String name, String descriptor, int index) {
			this.startPc = startPc;
			this.length = length;
			this.name = name;
			this.descriptor = descriptor;
			this.index = index;
		}
		
	}

	public static final String NAME = "LocalVariableTable";
	
	public static final Factory<Attribute> FACTORY = new Factory<Attribute>() {
		@Override
		public Attribute create() {
			return new LocalVariableTable();
		}
	};
	
	private Variable[] variables;
	
	public LocalVariableTable() {
		super(NAME);
	}

	@Override
	public void read(ConstantPool constants, Reader reader) throws IOException,
			InvalidClassFileFormatException {
		// TODO Auto-generated method stub
		super.read(constants, reader);
		int lengthVariableTable = reader.readUInt16();
		variables = new Variable[lengthVariableTable];
		for (int i = 0; i < lengthVariableTable; i++) {
			int startPc = reader.readUInt16();
			int length = reader.readUInt16();
			int nameIndex = reader.readUInt16();
			int descIndex = reader.readUInt16();
			int index = reader.readUInt16();
			variables[i] = new Variable(startPc, length, constants.getString(nameIndex), constants.getString(descIndex), index);
		}
	}
	
	@Override
	public void print(Printer printer) {
		for (Variable var : variables) {
			printer.println(var.descriptor, " ", var.name);
		}
	}
	
	public Variable getVariableAt(int addr, int index) {
		for (Variable var : variables) {
			if (var.index != index) {
				continue;
			}
			if (addr >= var.startPc && addr < var.startPc + var.length) {
				return var;
			}
		}
		return null;
	}

}
