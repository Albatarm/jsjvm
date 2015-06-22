package com.didactilab.jsjvm.client.classfile;

import java.util.ArrayList;
import java.util.List;

import com.didactilab.jsjvm.client.classfile.attribute.AbstractLocalVariableTable.Variable;
import com.didactilab.jsjvm.client.classfile.attribute.Code;
import com.didactilab.jsjvm.client.classfile.attribute.LocalVariableTable;
import com.didactilab.jsjvm.client.classfile.descriptor.DescriptorParser;
import com.didactilab.jsjvm.client.classfile.descriptor.DescType;

public class JavaMethod extends JavaMember {
	
	public class Parameter {
		public final int index;
		public final String name;
		public final DescType descriptorType;
		private Type resolvedType;
		
		public Parameter(int index, String name, DescType descriptorType) {
			this.index = index;
			this.name = name;
			this.descriptorType = descriptorType;
		}
		
		public String getDescriptor() {
			return descriptorType.getDescriptor();
		}
		
		public int getIndex() {
			return index;
		}
		
		@Override
		public String toString() {
			return descriptorType.getDescriptor() + " " + name; 
		}
		
		public Type getType() {
			return resolvedType;
		}
		
		private void resolve() throws ClassNotFoundException {
			resolvedType = toType(descriptorType);
		}
	}
	
	private Parameter[] parameters;
	private DescType returnDescType;
	
	private Code code;
	
	private Type resolvedReturnType;

	public JavaMethod(JavaClass javaClass) {
		super(javaClass);
	}
	
	@Override
	protected void afterRead() {
		code = getAttributes().get(Code.NAME, Code.class);
		initParameters();
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
		return parameters;
	}
	
	public boolean isStatic() {
		return (getAccessFlags() & AccessFlags.ACC_STATIC) != 0;
	}
	
	public boolean isConstructor() {
		return "<init>".equals(getName());
	}
	
	public DescType getReturnDescType() {
		return returnDescType;
	}
	
	public Type getReturnType() {
		return resolvedReturnType;
	}
	
	public Code getCodeAttribute() {
		return code;
	}
	
	public void resolve() throws ClassNotFoundException {
		for (Parameter parameter : parameters) {
			parameter.resolve();
		}
		resolvedReturnType = toType(returnDescType);
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
		final List<DescType> params = parser.getParameters();
		for (int i=0, c=params.size(); i<c; i++) {
			DescType type = params.get(i);
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
		returnDescType = parser.getReturnType();
	}
	
}
