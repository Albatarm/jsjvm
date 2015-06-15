package com.didactilab.jsjvm.client.reader;

public interface Reader {
	
	int getOffset();
	
	float readFloat();
	double readDouble();
	short readInt16();
	int readInt32();
	byte readInt8();
	long readInt64();
	int readUInt16();
	long readUInt32();
	short readUInt8();
	
}
