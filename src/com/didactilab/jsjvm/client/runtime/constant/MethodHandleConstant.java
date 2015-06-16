package com.didactilab.jsjvm.client.runtime.constant;

import java.io.IOException;

import com.didactilab.jsjvm.client.reader.Reader;

public class MethodHandleConstant extends AbstractConstant {
	
	// CONSTANT_Fieldref_info
	public static final int REF_getField = 1;
	public static final int REF_getStatic = 2;
	public static final int REF_putField = 3;
	public static final int REF_putStatic = 4;
	// CONSTANT_Methodref_info
	public static final int REF_invokeVirtual = 5;
	// CONSTANT_Methodref_info | CONSTANT_InterfaceMethodref_info
	public static final int REF_invokeStatic = 6;
	public static final int REF_invokeSpecial = 7;
	// CONSTANT_Methodref_info
	public static final int REF_newInvokeSpecial = 8;
	

	public final int kind;
	private final int index;
	
	public MethodHandleConstant(ConstantPool owner, Reader reader) throws IOException {
		super(owner);
		kind = reader.readUInt8();
		index = reader.readUInt16();
	}
	
	public AbstractRefConstant getReference() {
		return getConstant(index, AbstractRefConstant.class);
	}
	
}
