package com.didactilab.jsjvm.client.reader;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReader implements Reader {

	private DataInputStream stream;
	private int pos = 0;
	
	public FileReader(File file) throws FileNotFoundException {
		stream = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
	}
	
	@Override
	public int getOffset() {
		return pos;
	}

	@Override
	public float readFloat() throws IOException {
		float res = stream.readFloat();
		pos += Float.BYTES;
		return res;
	}

	@Override
	public double readDouble() throws IOException {
		double res = stream.readDouble();
		pos += Double.BYTES;
		return res;
	}

	@Override
	public short readInt16() throws IOException {
		short res = stream.readShort();
		pos += Short.BYTES;
		return res;
	}

	@Override
	public int readInt32() throws IOException {
		int res = stream.readInt();
		pos += Integer.BYTES;
		return res;
	}

	@Override
	public byte readInt8() throws IOException {
		byte res = stream.readByte();
		pos += Byte.BYTES;
		return res;
	}

	@Override
	public long readInt64() throws IOException {
		long res = stream.readLong();
		pos += Long.BYTES;
		return res;
	}

	@Override
	public int readUInt16() throws IOException {
		int res = stream.readUnsignedShort();
		pos += Short.BYTES;
		return res;
	}

	@Override
	public long readUInt32() throws IOException {
		int ch1 = stream.read();
        int ch2 = stream.read();
        int ch3 = stream.read();
        int ch4 = stream.read();
        if ((ch1 | ch2 | ch3 | ch4) < 0)
            throw new EOFException();
        long res = (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0);
		pos += Integer.BYTES;
		return res;
	}

	@Override
	public short readUInt8() throws IOException {
		short res = (short) stream.readUnsignedByte();
		pos += Byte.BYTES;
		return res;
	}

	@Override
	public byte[] readBytes(int length) throws IOException {
		byte[] res = new byte[length];
		stream.read(res);
		return res;
	}
	
	@Override
	public byte[] readBytes(long length) throws IOException {
		return readBytes((int) length);
	}
	
	@Override
	public void skip(int bytes) throws IOException {
		stream.skipBytes(bytes);
	}
	
	@Override
	public void close() {
		try {
			stream.close();
		} catch (IOException e) {
		}
	}

}
