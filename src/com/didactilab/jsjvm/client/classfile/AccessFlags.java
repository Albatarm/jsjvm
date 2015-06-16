package com.didactilab.jsjvm.client.classfile;

public interface AccessFlags {

	int ACC_PUBLIC 		= 0x0001;
	int ACC_PRIVATE 	= 0x0002;
	int ACC_PROTECTED 	= 0x0004;
	int ACC_STATIC 		= 0x0008;
	int ACC_FINAL 		= 0x0010;
	int ACC_SUPER 		= 0x0020;
	int ACC_SYNCHRONIZED = 0x0020;
	int ACC_VOLATILE 	= 0x0040;
	int ACC_BRIDGE		= 0x0040;
	int ACC_TRANSIENT 	= 0x0080;
	int ACC_VARARGS		= 0x0080;
	int ACC_NATIVE		= 0x0100;
	int ACC_INTERFACE 	= 0x0200;
	int ACC_ABSTRACT 	= 0x0400;
	int ACC_STRICT		= 0x0800;
	int ACC_SYNTHETIC 	= 0x1000;
	int ACC_ANNOTATION 	= 0x2000;
	int ACC_ENUM 		= 0x4000;
	
	
	int ACC_CLASS = ACC_PUBLIC | ACC_FINAL | ACC_SUPER | ACC_INTERFACE | 
			ACC_ABSTRACT | ACC_SYNTHETIC | ACC_ANNOTATION | ACC_ENUM;
	int ACC_FIELD = ACC_PUBLIC | ACC_PRIVATE | ACC_PROTECTED | ACC_STATIC | 
			ACC_VOLATILE | ACC_TRANSIENT | ACC_SYNTHETIC | ACC_ENUM;
	int ACC_METHOD = ACC_PUBLIC | ACC_PRIVATE | ACC_PROTECTED | ACC_STATIC |
			ACC_FINAL | ACC_SYNCHRONIZED | ACC_BRIDGE | ACC_VARARGS |
			ACC_NATIVE | ACC_ABSTRACT | ACC_STRICT | ACC_SYNTHETIC;
	
}
