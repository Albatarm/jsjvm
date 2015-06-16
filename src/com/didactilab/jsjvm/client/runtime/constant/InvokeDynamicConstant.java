package com.didactilab.jsjvm.client.runtime.constant;

import java.io.IOException;

import com.didactilab.jsjvm.client.reader.Reader;

public class InvokeDynamicConstant extends AbstractConstant {

	public final int bootstrapMethodAttrIndex;
	public final NameAndTypeConstant nameAndType;
	
	public InvokeDynamicConstant(ConstantPool owner, Reader reader) throws IOException {
		super(owner);
		bootstrapMethodAttrIndex = reader.readUInt16();
		int nameAndTypeIndex = reader.readUInt16();
		nameAndType = getConstant(nameAndTypeIndex, NameAndTypeConstant.class);
	}

}
