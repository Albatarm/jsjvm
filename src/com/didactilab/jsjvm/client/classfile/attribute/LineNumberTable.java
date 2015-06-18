package com.didactilab.jsjvm.client.classfile.attribute;

import java.io.IOException;

import com.didactilab.jsjvm.client.Factory;
import com.didactilab.jsjvm.client.classfile.InvalidClassFileFormatException;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;

public class LineNumberTable extends Attribute {
	
	private static class Line {
		
		public final int startPc;
		public final int lineNumber;
		
		public Line(int startPc, int lineNumber) {
			this.startPc = startPc;
			this.lineNumber = lineNumber;
		}
		
	}

	public static final String NAME = "LineNumberTable";
	
	public static final Factory<Attribute> FACTORY = new Factory<Attribute>() {
		@Override
		public Attribute create() {
			return new LineNumberTable();
		}
	};
	
	private Line[] lines;
	
	public LineNumberTable() {
		super(NAME);
	}

	@Override
	public void read(ConstantPool constants, Reader reader) throws IOException,
			InvalidClassFileFormatException {
		super.read(constants, reader);
		int lineNumberTableLength = reader.readUInt16();
		lines = new Line[lineNumberTableLength];
		for (int i = 0; i < lineNumberTableLength; i++) {
			int startPc = reader.readUInt16();
			int lineNumber = reader.readUInt16();
			lines[i] = new Line(startPc, lineNumber);
		}
	}
	
	@Override
	public void print(Printer printer) {
		for (Line line : lines) {
			printer.println(line.startPc, " => ", line.lineNumber);
		}
	}

}
