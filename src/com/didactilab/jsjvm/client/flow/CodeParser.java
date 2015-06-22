package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.attribute.CodeArrayType;

public class CodeParser {

	private final byte[] code;
	private int position = 0;
	private int lastOpPosition;

	public CodeParser(byte[] code) {
		this.code = code;
	}
	
	public int nextOp() {
		lastOpPosition = position;
		return code[position++] & 0xFF;
	}
	
	public int getPosition() {
		return position;
	}
	
	public int nextConstantPoolIndex() {
		return nextUnsignedShort();
	}
	
	public int nextConstantPoolIndexByte() {
		return nextUnsignedByte();
	}
	
	public CodeArrayType nextArrayType() {
		return CodeArrayType.valueOfCode(nextUnsignedByte());
	}
	
	public int nextBranchOffset() {
		return nextSignedShort();
	}
	
	public boolean hasMore() {
		return position < code.length;
	}
	
	public int getLastOpPosition() {
		return lastOpPosition;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	private int nextUnsignedByte() {
		return code[position++] & 0xFF;
	}
	
	private int nextUnsignedShort() {
		int byte1 = code[position++] & 0xFF;
		int byte2 = code[position++] & 0xFF;
		return ((byte1 << 8) | byte2) & 0xFFFF;
	}
	
	private int nextSignedShort() {
		int byte1 = code[position++];
		int byte2 = code[position++];
		return (byte1 << 8) | byte2;
	}
	
}
