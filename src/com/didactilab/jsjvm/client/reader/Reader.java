package com.didactilab.jsjvm.client.reader;

import java.io.IOException;

public interface Reader {
	
	int getOffset();
	
	float readFloat() throws IOException;
	double readDouble() throws IOException;
	short readInt16() throws IOException;
	int readInt32() throws IOException;
	byte readInt8() throws IOException;
	long readInt64() throws IOException;
	int readUInt16() throws IOException;
	long readUInt32() throws IOException;
	short readUInt8() throws IOException;
	byte[] readBytes(int length) throws IOException;
	byte[] readBytes(long length) throws IOException;
	
	void skip(int bytes) throws IOException;
	
}
