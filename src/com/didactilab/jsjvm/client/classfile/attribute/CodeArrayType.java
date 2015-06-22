package com.didactilab.jsjvm.client.classfile.attribute;

public enum CodeArrayType {

	T_BOOLEAN	(4, "boolean"),
	T_CHAR		(5, "char"),
	T_FLOAT		(6, "float"),
	T_DOUBLE	(7, "double"),
	T_BYTE		(8, "byte"),
	T_SHORT		(9, "short"),
	T_INT		(10, "int"),
	T_LONG		(11, "long");
	
	public final int code;
	public final String name;
	
	private CodeArrayType(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static CodeArrayType valueOfCode(int code) {
		for (CodeArrayType arrayType : values()) {
			if (arrayType.code == code) {
				return arrayType;
			}
		}
		return null;
	}
	
}
