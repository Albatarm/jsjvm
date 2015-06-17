package com.didactilab.jsjvm.client.classfile.attribute;

public enum ArrayType {

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
	
	private ArrayType(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static ArrayType valueOfCode(int code) {
		for (ArrayType arrayType : values()) {
			if (arrayType.code == code) {
				return arrayType;
			}
		}
		return null;
	}
	
}
