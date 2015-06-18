package com.didactilab.jsjvm.client.classfile.attribute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.didactilab.jsjvm.client.Factory;
import com.didactilab.jsjvm.client.classfile.InvalidClassFileFormatException;
import com.didactilab.jsjvm.client.classfile.OpCodeData;
import com.didactilab.jsjvm.client.classfile.attribute.LocalVariableTable.Variable;
import com.didactilab.jsjvm.client.classfile.constant.ClassConstant;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.debug.IndentedPrinter;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;

public class Code extends Attribute {
	
	public static class Exception {
		
		public final int startPc;
		public final int endPc;
		public final int handlerPc;
		public final ClassConstant catchType;
		
		public Exception(Reader reader, ConstantPool constants) throws IOException {
			this.startPc = reader.readUInt16();
			this.endPc = reader.readUInt16();
			this.handlerPc = reader.readUInt16();
			int catchTypeIndex = reader.readUInt16();
			this.catchType = catchTypeIndex != 0 ? constants.get(catchTypeIndex, ClassConstant.class) : null;
		}
		
	}
	
	private static class Instruction {
		
		public final int addr;
		public final String text;
		
		public Instruction(int addr, String text) {
			this.addr = addr;
			this.text = text;
		}
		
	}
	
	private static class Labels {
		
		private final HashMap<Integer, String> map = new HashMap<>();
		
		private int labelCounter = 1;
		private int handlerCounter = 1;
		private int positionCounter = 1;
		
		public String get(int addr) {
			return map.get(addr);
		}
		
		public String putLabel(int addr) {
			String label = map.get(addr);
			if (label == null) {
				label = "@@label" + (labelCounter++);
				map.put(addr, label);
			}
			return label;
		}
		
		public String putHandler(int addr) {
			String label = map.get(addr);
			if (label == null) {
				label = "@@handler" + (handlerCounter++);
				map.put(addr, label);
			}
			return label;
		}
		
		public String putPosition(int addr) {
			String label = map.get(addr);
			if (label == null) {
				label = "@@pos" + (positionCounter++);
				map.put(addr, label);
			}
			return label;
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
		
		ArrayList<Instruction> instructions = new ArrayList<>();
		Labels labels = new Labels();
		
		LocalVariableTable variableTable = attributes.get(LocalVariableTable.NAME, LocalVariableTable.class);
		
		int pos = 0;
		while (pos < code.length) {
			int oppos = pos;
			int op = code[pos++] & 0xFF;
			OpCodeData opCode = OpCodeData.valueOfCode(op);
			String text = opCode.name;
			for (OpCodeData.Param param : opCode.params) {
				switch (param) {
					case ATYPE:
						{
							int type = code[pos++] & 0xFF;
							ArrayType arrayType = ArrayType.valueOfCode(type);
							text += " " + arrayType.name;
						}
						break;
					case BRANCH_OFFSET:
						{
							int byte1 = code[pos++];
							int byte2 = code[pos++];
							int offset = (byte1 << 8) | byte2;
							int newpos = oppos + offset;
							String label = labels.putLabel(newpos);
							text += " " + label;
						}
						break;
					case BRANCH_OFFSET_W:
						{
							int byte1 = code[pos++];
							int byte2 = code[pos++];
							int byte3 = code[pos++];
							int byte4 = code[pos++];
							int offset = (byte1 << 24) | (byte2 << 16) | (byte3 << 8) | byte4;
							int newpos = oppos + offset;
							String label = labels.putLabel(newpos);
							text += " " + label;
						}
						break;
					case BYTE_VALUE:
						{
							int value = code[pos++];
							text += " value=" + value;
						}
						break;
					case CONSTANT_POOL_INDEX:
						{
							int byte1 = code[pos++] & 0xFF;
							int byte2 = code[pos++] & 0xFF;
							int index = ((byte1 << 8) | byte2) & 0xFFFF;
							text += " " + constants.get(index, Object.class);
						}
						break;
					case CONSTANT_POOL_INDEX_BYTE:
						{
							int index = code[pos++] & 0xFF;
							text += " " + constants.get(index, Object.class);
						}
						break;
					case COUNT:
						{
							int count = code[pos++] & 0xFF;
							text += " count=" + count;
						}
						break;
					case DIMENSIONS:
						{
							int dim = code[pos++] & 0xFF;
							text += " dimensions=" + dim;
						}
						break;
					case LOCAL_VARIABLE_INDEX:
					case LOCAL_VARIABLE_INDEX_AS_RETURN_ADDRESS:
						{
							int index = code[pos++] & 0xFF;
							String name;
							if (variableTable != null) {
								Variable var = variableTable.getVariableAt(oppos, index);
								name = var != null ? var.name : String.valueOf(index);
							} else {
								name = String.valueOf(index);
							}
							text += " #" + name;
						}
						break;
					/*case LOOKUPSWITCH:
						break;*/
					case NULL:
						pos++;
						break;
					case SHORT_VALUE:
						{
							int byte1 = code[pos++];
							int byte2 = code[pos++];
							int value = (byte1 << 8) | byte2;
							text += " value=" + value;
						}
						break;
					/*case TABLESWITCH:
						break;
					case WIDE:
						break;*/
					default:
						pos += opCode.getParamSize();
						break;
				}
			}
			
			instructions.add(new Instruction(oppos, text));
		}
		
		for (Exception ex : exceptionTable) {
			labels.putPosition(ex.startPc);
			labels.putPosition(ex.endPc);
			labels.putHandler(ex.handlerPc);
		}
		
		Printer p = new IndentedPrinter(printer, "  ");
		for (Instruction i : instructions) {
			String label = labels.get(i.addr);
			if (label != null) {
				p.println(label, ":");
			}
			p.println("  ", i.text);
		}
		
		if (exceptionTable.length != 0) {
			printer.println("ExceptionTable :");
			for (Exception ex : exceptionTable) {
				printer.println("  from ", labels.get(ex.startPc), " to ", labels.get(ex.endPc), " => ", 
						labels.get(ex.handlerPc), " for ", ex.catchType != null ? ex.catchType : "*");
			}
		}
		
		if (!attributes.isEmpty()) {
			attributes.print(printer);
		}
		
	}

}
