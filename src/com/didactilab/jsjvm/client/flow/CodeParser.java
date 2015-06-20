package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.attribute.ArrayType;

public class CodeParser {

	private final byte[] code;
	private int position = 0;
	private int opCodeAddr;

	public CodeParser(byte[] code) {
		this.code = code;
	}
	
	public int nextOp() {
		opCodeAddr = position + 1;
		return code[position++] & 0xFF;
	}
	
	public int nextConstantPoolIndex() {
		int byte1 = code[position++] & 0xFF;
		int byte2 = code[position++] & 0xFF;
		return ((byte1 << 8) | byte2) & 0xFFFF;
	}
	
	public ArrayType nextArrayType() {
		int type = code[position++] & 0xFF;
		return ArrayType.valueOfCode(type);
	}
	
	public boolean hasMore() {
		return position < code.length;
	}
	
	public int getAddr() {
		return opCodeAddr;
	}
	
}
