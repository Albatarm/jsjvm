package com.didactilab.jsjvm.client.classfile;

import java.util.ArrayList;
import java.util.List;

import com.didactilab.jsjvm.client.classfile.attribute.AbstractLocalVariableTable.Variable;
import com.didactilab.jsjvm.client.classfile.attribute.Code;
import com.didactilab.jsjvm.client.classfile.attribute.LocalVariableTable;
import com.didactilab.jsjvm.client.classfile.descriptor.DescriptorParser;
import com.didactilab.jsjvm.client.classfile.descriptor.Type;

public class JavaMethod extends JavaMember {
	
	public static class Parameter {
		public final int index;
		public final String name;
		public final Type type;
		
		public Parameter(int index, String name, Type type) {
			this.index = index;
			this.name = name;
			this.type = type;
		}
		
		public String getDescriptor() {
			return type.getDescriptor();
		}
		
		public int getIndex() {
			return index;
		}
		
		public Type getType() {
			return type;
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
	protected void afterRead() {
		code = getAttributes().get(Code.NAME, Code.class);
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
	
	public boolean isConstructor() {
		return "<init>".equals(getName());
	}
	
	public Type getReturnType() {
		if (parameters == null) {
			initParameters();
		}
		return returnType;
	}
	
	public Code getCodeAttribute() {
		return code;
	}
	
	private LocalVariableTable getCodeLocalVariableTable() {
		Code code = getCodeAttribute();
		return code != null ? code.getAttributes().get(LocalVariableTable.NAME, LocalVariableTable.class) : null;
	}
	
	private void initParameters() {
		LocalVariableTable varTable = getCodeLocalVariableTable();
		DescriptorParser parser = new DescriptorParser(getDescriptor());
		parser.parseMethod();
		ArrayList<Parameter> ps = new ArrayList<>();
		final List<Type> params = parser.getParameters();
		for (int i=0, c=params.size(); i<c; i++) {
			Type type = params.get(i);
			String name;
			if (varTable != null) {
				Variable var = varTable.getOnlyVariableAt(isStatic() ? i : i+1);
				name = var != null ? var.name : "param" + i;
			} else {
				name = "param" + i;
			}
			ps.add(new Parameter(i, name, type));
		}
		parameters = ps.toArray(new Parameter[ps.size()]);
		//
		returnType = parser.getReturnType();
	}
	
}
