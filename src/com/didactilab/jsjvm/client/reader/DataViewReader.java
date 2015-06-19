package com.didactilab.jsjvm.client.reader;

import java.io.IOException;

import com.google.gwt.typedarrays.shared.DataView;

public class DataViewReader implements Reader {

	private final DataView view;
	private int pos = 0;

	public DataViewReader(DataView view) {
		this.view = view;
	}
	
	@Override
	public int getOffset() {
		return pos;
	}

	@Override
	public float readFloat() throws IOException {
		float res = view.getFloat32(pos);
		pos += 4;
		return res;
	}

	@Override
	public double readDouble() throws IOException {
		double res = view.getFloat64(pos);
		pos += 8;
		return res;
	}

	@Override
	public short readInt16() throws IOException {
		short res = view.getInt16(pos);
		pos += 2;
		return res;
	}

	@Override
	public int readInt32() throws IOException {
		int res = view.getInt32(pos);
		pos += 4;
		return res;
	}

	@Override
	public byte readInt8() throws IOException {
		byte res = view.getInt8(pos);
		pos += 1;
		return res;
	}

	@Override
	public long readInt64() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int readUInt16() throws IOException {
		int res = view.getUint16(pos);
		pos += 2;
		return res;
	}

	@Override
	public long readUInt32() throws IOException {
		long res = view.getUint32(pos);
		pos += 4;
		return res;
	}

	@Override
	public short readUInt8() throws IOException {
		short res = view.getUint8(pos);
		pos += 1;
		return res;
	}

	@Override
	public byte[] readBytes(int length) throws IOException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public byte[] readBytes(long length) throws IOException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void skip(int bytes) throws IOException {
		pos += bytes;
	}

}
