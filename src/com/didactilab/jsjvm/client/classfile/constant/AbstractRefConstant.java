package com.didactilab.jsjvm.client.classfile.constant;

import java.io.IOException;

import com.didactilab.jsjvm.client.reader.Reader;

public abstract class AbstractRefConstant extends AbstractConstant {
	
	private final int classIndex;
	private final int nameAndTypeIndex;
	
	private ClassConstant className;
	private NameAndTypeConstant nameAndType;
	
	public AbstractRefConstant(ConstantPool owner, Reader reader) throws IOException {
		super(owner);
		classIndex = reader.readUInt16();
		nameAndTypeIndex = reader.readUInt16();
	}
	
	public ClassConstant getClassName() {
		if (className == null) {
			className = getConstant(classIndex, ClassConstant.class);
		}
		return className;
	}
	
	public NameAndTypeConstant getNameAndType() {
		if (nameAndType == null) {
			nameAndType = getConstant(nameAndTypeIndex, NameAndTypeConstant.class);
		}
		return nameAndType;
	}
	
	protected abstract String getType();
	
	@Override
	public String toString() {
		return "[" + getType() + "] " + getClassName().getName() + "::" + getNameAndType().getName() + 
				getNameAndType().getDescriptor();
	}
	
}
