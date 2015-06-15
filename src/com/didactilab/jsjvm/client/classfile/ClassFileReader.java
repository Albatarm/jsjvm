package com.didactilab.jsjvm.client.classfile;

import com.google.gwt.typedarrays.shared.ArrayBuffer;
import com.google.gwt.typedarrays.shared.DataView;
import com.google.gwt.typedarrays.shared.TypedArrays;

public class ClassFileReader {

	private DataView view;
	
	public ClassFileReader(ArrayBuffer buffer) {
		view = TypedArrays.createDataView(buffer);
	}
	
	private void readHeader() {
		long magic = view.getUint32(0);
		
	}
	
}
