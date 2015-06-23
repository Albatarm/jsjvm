package com.didactilab.jsjvm.client.classfile.attribute;

import java.io.IOException;

import com.didactilab.jsjvm.client.classfile.ClassFormatException;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;

public abstract class AbstractLocalVariableTable extends Attribute {
	
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
		
		@Override
		public String toString() {
			return index + " => " + "[" + startPc + ".." + (startPc + length) + "[ " + descriptor + " " + name;
		}
		
	}
	
	private Variable[] variables;
	
	public AbstractLocalVariableTable(String name) {
		super(name);
	}

	@Override
	public void read(ConstantPool constants, Reader reader) throws IOException,
			ClassFormatException {
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
			printer.println(var);
		}
	}
	
	public Variable getVariableAt(int addr, int index, int variation) {
		for (Variable var : variables) {
			if (var.index != index) {
				continue;
			}
			if (addr >= var.startPc - variation && addr < var.startPc + var.length + variation) {
				return var;
			}
		}
		return null;
	}
	
	public Variable getVariableAt(int addr, int index) {
		return getVariableAt(addr, index, 0);
	}
	
	public Variable getOnlyVariableAt(int index) {
		Variable found = null;
		for (Variable var : variables) {
			if (var.index == index) {
				if (found == null) {
					found = var;
				} else {
					return null;
				}
			}
		}
		return found;
	}

}
