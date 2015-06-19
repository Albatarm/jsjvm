package com.didactilab.jsjvm.client.classfile;

import java.util.ArrayList;

import com.didactilab.jsjvm.client.classfile.attribute.AbstractLocalVariableTable.Variable;
import com.didactilab.jsjvm.client.classfile.attribute.Code;
import com.didactilab.jsjvm.client.classfile.attribute.LocalVariableTable;
import com.didactilab.jsjvm.client.classfile.descriptor.DescriptorParser;
import com.didactilab.jsjvm.client.classfile.descriptor.Type;
import com.didactilab.jsjvm.client.debug.StringPrinter;

public class JavaMethod extends JavaMember {
	
	public static class Parameter {
		public final String name;
		public final Type type;
		
		public Parameter(String name, Type type) {
			this.name = name;
			this.type = type;
		}
		
		public String getDescriptor() {
			return type.getDescriptor();
		}
		
		@Override
		public String toString() {
			return type.getDescriptor() + " " + name; 
		}
	}
	
	private Parameter[] parameters;
	private Type returnType;
	
	private Code code;

	public JavaMethod(JavaClass javaClass) {
		super(javaClass);
	}

	@Override
	protected int getAllowedAccessFlagsMask() {
		return AccessFlags.ACC_METHOD;
	}

	@Override
	protected String getMemberTypeName() {
		return "method";
	}
	
	@Override
	public String toString() {
		return getName() + getDescriptor();
	}
	
	public Parameter[] getParameters() {
		if (parameters == null) {
			initParameters();
		}
		return parameters;
	}
	
	public boolean isStatic() {
		return (getAccessFlags() & AccessFlags.ACC_STATIC) != 0;
	}
	
	public Type getReturnType() {
		if (parameters == null) {
			initParameters();
		}
		return returnType;
	}
	
	protected Code getCode() {
		if (code == null) {
			code = getAttributes().get(Code.NAME, Code.class);
			if (code == null) {
				throw new IllegalArgumentException();
			}
		}
		return code;
	}
	
	private void initParameters() {
		LocalVariableTable varTable = getCode().getAttributes().get(LocalVariableTable.NAME, LocalVariableTable.class);
		if (varTable == null) {
			StringPrinter s = new StringPrinter();
			getAttributes().print(s);
			throw new IllegalArgumentException(s.toString());
		}
		DescriptorParser parser = new DescriptorParser(getDescriptor());
		parser.parseMethod();
		ArrayList<Parameter> ps = new ArrayList<>();
		for (Type type : parser.getParameters()) {
			String name;
			if (varTable != null) {
				Variable var = varTable.getOnlyVariableAt(isStatic() ? 0 : 1);
				name = var != null ? var.name : "x" + (ps.size() + 1);
			} else {
				name = "p" + (ps.size() + 1);
			}
			Parameter p = new Parameter(name, type);
			ps.add(p);
		}
		parameters = ps.toArray(new Parameter[ps.size()]);
		//
		returnType = parser.getReturnType();
	}
	
}
