package com.didactilab.jsjvm.client.classfile;

import com.didactilab.jsjvm.client.reader.DataViewReader;
import com.didactilab.jsjvm.client.reader.Reader;
import com.didactilab.jsjvm.client.runtime.constant.ConstantPool;
import com.google.gwt.typedarrays.shared.ArrayBuffer;
import com.google.gwt.typedarrays.shared.DataView;
import com.google.gwt.typedarrays.shared.TypedArrays;

public class ClassFileReader {

	private DataView view;
	private Reader reader;
	private ConstantPool constantPool = new ConstantPool();
	
	public ClassFileReader(ArrayBuffer buffer) {
		view = TypedArrays.createDataView(buffer);
		Reader = new DataViewReader(view);
	}
	
	private void readHeader() {
		long magic = view.getUint32(0);
		int minorVersion = view.getUint16(4);
		int majorVersion = view.getUint16(6);
		
		constantPool.read(TypedArrays.createDataView(view.buffer(), view.byteOffset() + 8));
		
	}
	
}
