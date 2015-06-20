package com.didactilab.jsjvm.client.classfile;

@SuppressWarnings("serial")
public class ClassNotFoundException extends Exception {

	public ClassNotFoundException(String msg) {
		super(msg);
	}
	
	public ClassNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
