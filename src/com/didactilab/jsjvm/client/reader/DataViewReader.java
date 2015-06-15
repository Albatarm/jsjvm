package com.didactilab.jsjvm.client.reader;

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
	public float readFloat() {
		float res = view.getFloat32(pos);
		pos += 4;
		return res;
	}

	@Override
	public double readDouble() {
		double res = view.getFloat64(pos);
		pos += 8;
		return res;
	}

	@Override
	public short readInt16() {
		short res = view.getInt16(pos);
		pos += 2;
		return res;
	}

	@Override
	public int readInt32() {
		int res = view.getInt32(pos);
		pos += 4;
		return res;
	}

	@Override
	public byte readInt8() {
		byte res = view.getInt8(pos);
		pos += 1;
		return res;
	}

	@Override
	public long readInt64() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int readUInt16() {
		int res = view.getUint16(pos);
		pos += 2;
		return res;
	}

	@Override
	public long readUInt32() {
		long res = view.getUint32(pos);
		pos += 4;
		return res;
	}

	@Override
	public short readUInt8() {
		short res = view.getUint8(pos);
		pos += 1;
		return res;
	}

}
