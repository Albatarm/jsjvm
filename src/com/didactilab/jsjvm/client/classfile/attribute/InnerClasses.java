package com.didactilab.jsjvm.client.classfile.attribute;

import java.io.IOException;

import com.didactilab.jsjvm.client.Factory;
import com.didactilab.jsjvm.client.classfile.AccessFlags;
import com.didactilab.jsjvm.client.classfile.ClassFormatException;
import com.didactilab.jsjvm.client.classfile.attribute.InnerClasses.InnerClass;
import com.didactilab.jsjvm.client.classfile.constant.ClassConstant;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.reader.Reader;

public class InnerClasses extends AbstractListAttribute<InnerClass> {

	public static class InnerClass {
		
		public final ClassConstant innerClass;
		public final ClassConstant outerClass;
		public final String name;
		public final int accessFlags;
		
		public InnerClass(ClassConstant innerClass, ClassConstant outerClass, String name, int accessFlags) {
			this.innerClass = innerClass;
			this.outerClass = outerClass;
			this.name = name;
			this.accessFlags = accessFlags;
		}
		
		public boolean isTopLevelOrLocalOrAnonymous() {
			return outerClass == null;
		}
		
		public boolean isAnonymous() {
			return name == null;
		}
		
		@Override
		public String toString() {
			return innerClass + (outerClass != null ? " parent=" + outerClass : "") + (name != null ? " name=" + name : "") +
					" flags=" + Integer.toHexString(accessFlags);
		}
		
	}
	
	public static final String NAME = "InnerClasses";
	
	public static final Factory<Attribute> FACTORY = new Factory<Attribute>() {
		@Override
		public Attribute create() {
			return new InnerClasses();
		}
	};
	
	private InnerClass[] classes;
	
	public InnerClasses() {
		super(NAME);
	}
	
	@Override
	public void read(ConstantPool constants, Reader reader) throws IOException,
			ClassFormatException {
		super.read(constants, reader);
		int length = reader.readUInt16();
		classes = new InnerClass[length];
		for (int i = 0; i < length; i++) {
			int innerClassIndex = reader.readUInt16();
			int outerClassIndex = reader.readUInt16();
			int nameIndex = reader.readInt16();
			int accessFlags = reader.readUInt16();
			if ((accessFlags & AccessFlags.ACC_INNERCLASS) != accessFlags) {
				throw new ClassFormatException("Invalid access flags for an inner class");
			}
			classes[i] = new InnerClass(constants.get(innerClassIndex, ClassConstant.class),
					outerClassIndex != 0 ? constants.get(outerClassIndex, ClassConstant.class) : null,
					nameIndex != 0 ? constants.getString(nameIndex) : null,
					accessFlags);
		}
	}

	@Override
	protected InnerClass[] getList() {
		return classes;
	}

}
