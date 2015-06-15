package com.didactilab.jsjvm.client.runtime.constant;

import com.didactilab.jsjvm.client.reader.Reader;

public abstract class AbstractRefInfoConstant extends AbstractConstant {
	
	private final int classIndex;
	private final int nameAndTypeIndex;
	
	private ClassInfoConstant classInfo;
	private NameAndTypeInfoConstant nameAndTypeInfo;
	
	public AbstractRefInfoConstant(ConstantPool owner, Reader reader) {
		super(owner);
		classIndex = reader.readUInt16();
		nameAndTypeIndex = reader.readUInt16();
	}

	public ClassInfoConstant getClassInfo() {
		if (classInfo == null) {
			classInfo = getConstant(classIndex, ClassInfoConstant.class);
		}
		return classInfo;
	}
	
	public NameAndTypeInfoConstant getNameAndType() {
		if (nameAndTypeInfo == null) {
			nameAndTypeInfo = getConstant(nameAndTypeIndex, NameAndTypeInfoConstant.class);
		}
		return nameAndTypeInfo;
	}
	
}
