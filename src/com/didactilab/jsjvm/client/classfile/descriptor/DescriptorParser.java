package com.didactilab.jsjvm.client.classfile.descriptor;

import java.util.ArrayList;
import java.util.List;

public class DescriptorParser {
	
	private final ArrayList<Type> list = new ArrayList<>();
	
	private final String descriptor;
	private final int len;
	private int pos = 0;
	
	private Type returnType;
	
	public DescriptorParser(String descriptor) {
		this.descriptor = descriptor;
		this.len = descriptor.length();
	}
	
	private String nextToken() {
		if (pos < len) {
			char type = descriptor.charAt(pos++);
			switch (type) {
				case 'B':
				case 'C':
				case 'D':
				case 'F':
				case 'I':
				case 'J':
				case 'S':
				case 'Z':
					return String.valueOf(type);
				case 'L':
					char ch = descriptor.charAt(pos++);
					String className = "";
					while (ch != ';') {
						className += ch;
						ch = descriptor.charAt(pos++);
					}
					return "L" + className;
				case '[':
					return "[";
				case '(':
					return "(";
				case ')':
					return ")";
				case 'V':
					return "V";
				default:
					throw new IllegalArgumentException("Invalid char '" + type + "' in descriptor");
			}
		} else {
			return null;
		}
	}
	
	private Type parseType(boolean acceptVoid) {
		String token = nextToken();
		char firstChar = token.charAt(0);
		if (firstChar == '[') {
			int dimension = 1;
			String componentType = nextToken();
			while ("[".equals(componentType)) {
				dimension++;
				componentType = nextToken();
			}
			return new ArrayType(dimension, createBaseType(componentType));
		} else if (acceptVoid && firstChar == 'V') {
			return new VoidType();
		} else {
			return createBaseType(token);
		}
	}
	
	public Type parseField() {
		return parseType(false);
	}
	
	public void parseMethod() {
		checkToken("(");
		String token = nextToken();
		while (!")".equals(token)) {
			char firstChar = token.charAt(0);
			if (firstChar == '[') {
				int dimension = 1;
				String componentType = nextToken();
				while ("[".equals(componentType)) {
					dimension++;
					componentType = nextToken();
				}
				list.add(new ArrayType(dimension, createBaseType(componentType)));
			} else {
				list.add(createBaseType(token));
			}
			token = nextToken();
		}
		returnType = parseType(true);
	}
	
	private Type createBaseType(String type) {
		char first = type.charAt(0);
		return first == 'L' ? new ObjectType(type.substring(1)) : new PrimitiveType(first);
	}
	
	private void checkToken(String wanted) {
		String token = nextToken();
		if (!wanted.equals(token)) {
			throw new IllegalArgumentException("Invalid method description format, wanted : " + wanted);
		}
	}
	
	public List<Type> getParameters() {
		return list;
	}
	
	public Type getReturnType() {
		return returnType;
	}
	
}
