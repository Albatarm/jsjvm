package com.didactilab.jsjvm.client.classfile;

@SuppressWarnings("serial")
public class ClassFormatException extends Exception {
	
	public ClassFormatException(String msg) {
		super(msg);
	}
	
	public ClassFormatException(Exception cause) {
		super(cause);
	}

}
