package com.didactilab.jsjvm.client.classfile.descriptor;

public interface DescType {
	
	DescType VOID = new DescType() {
		@Override
		public String getDescriptor() {
			return "V";
		}
	};
	
	String getDescriptor();
}
