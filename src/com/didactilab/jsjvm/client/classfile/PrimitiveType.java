package com.didactilab.jsjvm.client.classfile;

public enum PrimitiveType implements Type {
	BYTE		('B', "byte"),
	SHORT		('S', "short"),
	INT			('I', "int"),
	LONG		('J', "long"),
	FLOAT		('F', "float"),
	DOUBLE		('D', "double"),
	BOOLEAN		('Z', "boolean"),
	CHAR		('C', "char");
	
	public final char sign;
	public final String name;

	private PrimitiveType(char sign, String name) {
		this.sign = sign;
		this.name = name;
	}
	
	@Override
	public String getJavaName() {
		return name;
	}
	
	public static PrimitiveType valueOf(char sign) {
		for (PrimitiveType p : values()) {
			if (p.sign == sign) {
				return p;
			}
		}
		return null;
	}
}