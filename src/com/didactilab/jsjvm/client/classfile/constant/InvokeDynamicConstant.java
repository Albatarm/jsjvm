package com.didactilab.jsjvm.client.classfile.constant;

import java.io.IOException;

import com.didactilab.jsjvm.client.reader.Reader;

public class InvokeDynamicConstant extends AbstractConstant {

	public final int bootstrapMethodAttrIndex;
	private final int nameAndTypeIndex;
	private NameAndTypeConstant nameAndType;
	
	public InvokeDynamicConstant(ConstantPool owner, Reader reader) throws IOException {
		super(owner);
		bootstrapMethodAttrIndex = reader.readUInt16();
		nameAndTypeIndex = reader.readUInt16();
	}
	
	public NameAndTypeConstant getNameAndType() {
		if (nameAndType == null) {
			nameAndType = getConstant(nameAndTypeIndex, NameAndTypeConstant.class);
		}
		return nameAndType;
	}

}
