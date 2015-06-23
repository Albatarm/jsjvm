package com.didactilab.jsjvm.client.classfile;

import java.io.IOException;

import com.didactilab.jsjvm.client.classfile.attribute.Attributes;
import com.didactilab.jsjvm.client.classfile.descriptor.ArrayDescType;
import com.didactilab.jsjvm.client.classfile.descriptor.DescType;
import com.didactilab.jsjvm.client.classfile.descriptor.ObjectDescType;
import com.didactilab.jsjvm.client.classfile.descriptor.VoidDescType;
import com.didactilab.jsjvm.client.debug.IndentedPrinter;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;

public abstract class JavaMember {

	private final JavaClass javaClass;
	
	private int accessFlags;
	private String name;
	private String descriptor;
	
	private Attributes attributes;
	
	public JavaMember(JavaClass javaClass) {
		this.javaClass = javaClass;
		attributes = new Attributes(javaClass.getConstantPool());
	}
	
	public void read(Reader reader) throws IOException, ClassFormatException {
		accessFlags = reader.readUInt16();
		
		if ((accessFlags & getAllowedAccessFlagsMask()) != accessFlags) {
			throw new ClassFormatException("The access flags of a " + getMemberTypeName() + " is not valid : " + Integer.toHexString(accessFlags));
		}
		
		name = javaClass.getConstantPool().getString(reader.readUInt16());
		descriptor = javaClass.getConstantPool().getString(reader.readUInt16());
		attributes.read(reader);
		afterRead();
	}
	
	protected void afterRead() {
	}
	
	public void print(Printer printer) {
		printer.println(getMemberTypeName(), " ", toString());
		if (!attributes.isEmpty()) {
			attributes.print(new IndentedPrinter(printer, "   "));
		}
		
	}
	
	protected abstract int getAllowedAccessFlagsMask();
	protected abstract String getMemberTypeName();
	
	public Attributes getAttributes() {
		return attributes;
	}
	
	public String getName() {
		return name;
	}
	
	public JavaClass getJavaClass() {
		return javaClass;
	}
	
	public int getAccessFlags() {
		return accessFlags;
	}
	
	public String getDescriptor() {
		return descriptor;
	}
	
	protected Type toType(DescType descType) throws ClassNotFoundException {
		if (descType instanceof PrimitiveType) {
			return (PrimitiveType) descType;
		} else if (descType instanceof ArrayDescType) {
			ArrayDescType adt = (ArrayDescType) descType;
			DescType dtype = adt.type;
			Type componentType;
			if (dtype instanceof PrimitiveType) {
				componentType = (PrimitiveType) dtype;
			} else if (dtype instanceof ObjectDescType) {
				componentType = getJavaClass().getClassLoader().loadClass(((ObjectDescType) dtype).name);
			} else {
				throw new IllegalStateException("descriptor parser fails with component type : " + dtype);
			}
			return new ArrayType(adt.dimension, componentType, adt.getDescriptor());
		} else if (descType instanceof ObjectDescType) {
			return getJavaClass().getClassLoader().loadClass(((ObjectDescType) descType).name);
		} else if (descType instanceof VoidDescType) {
			return null;
		} else {
			throw new IllegalStateException("descriptor parser fails : " + descType);
		}
	}
	
}
