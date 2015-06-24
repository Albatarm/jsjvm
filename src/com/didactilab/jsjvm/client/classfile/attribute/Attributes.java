package com.didactilab.jsjvm.client.classfile.attribute;

import java.io.IOException;
import java.util.HashMap;

import com.didactilab.jsjvm.client.classfile.ClassFormatException;
import com.didactilab.jsjvm.client.classfile.UnknownAttribute;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.debug.IndentedPrinter;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;
import com.didactilab.jsjvm.client.util.Factory;

public class Attributes {
	
	private static HashMap<String, Factory<Attribute>> factories = new HashMap<>();
	
	static {
		factory(SourceFile.NAME, SourceFile.FACTORY);
		factory(Signature.NAME, Signature.FACTORY);
		factory(Exceptions.NAME, Exceptions.FACTORY);
		factory(Code.NAME, Code.FACTORY);
		factory(LineNumberTable.NAME, LineNumberTable.FACTORY);
		factory(LocalVariableTable.NAME, LocalVariableTable.FACTORY);
		factory(LocalVariableTypeTable.NAME, LocalVariableTypeTable.FACTORY);
		factory(ConstantValue.NAME, ConstantValue.FACTORY);
		factory(Deprecated.NAME, Deprecated.FACTORY);
		factory(InnerClasses.NAME, InnerClasses.FACTORY);
		factory(RuntimeVisibleAnnotations.NAME, RuntimeVisibleAnnotations.FACTORY);
		factory(RuntimeInvisibleAnnotations.NAME, RuntimeInvisibleAnnotations.FACTORY);
		factory(RuntimeVisibleParameterAnnotations.NAME, RuntimeVisibleParameterAnnotations.FACTORY);
		factory(RuntimeInvisibleParameterAnnotations.NAME, RuntimeInvisibleParameterAnnotations.FACTORY);
		factory(EnclosingMethod.NAME, EnclosingMethod.FACTORY);
		factory(Synthetic.NAME, Synthetic.FACTORY);
		factory(MethodParameters.NAME, MethodParameters.FACTORY);
		factory(AnnotationDefault.NAME, AnnotationDefault.FACTORY);
		factory(SourceDebugExtension.NAME, SourceDebugExtension.FACTORY);
	}
	
	private static void factory(String name, Factory<Attribute> factory) {
		factories.put(name, factory);
	}

	private final ConstantPool constants;
	private Attribute[] attributes;
	
	private HashMap<String, Attribute> attributeByName;

	public Attributes(ConstantPool constants) {
		this.constants = constants;
	}
	
	public void read(Reader reader) throws IOException, ClassFormatException {
		int count = reader.readUInt16();
		attributes = new Attribute[count];
		for (int i = 0; i < count; i++) {
			String name = constants.getString(reader.readUInt16());
			Factory<Attribute> factory = factories.get(name);
			Attribute attr = factory != null ? factory.create() : new UnknownAttribute(name);
			attr.read(constants, reader);
			attributes[i] = attr;
		}
	}
	
	public void print(Printer printer) {
		printer.println("attributes[", attributes.length, "]:");
		for (int i = 0; i < attributes.length; i++) {
			Attribute attr = attributes[i];
			printer.println("   - ", attr.getName(), " : ");
			attr.print(new IndentedPrinter(printer, "      "));
		}
	}
	
	public boolean isEmpty() {
		return attributes.length == 0;
	}
	
	public <T extends Attribute> T get(String name, Class<T> attributeClass) {
		if (attributeByName == null) {
			attributeByName = new HashMap<>();
			for (Attribute attr : attributes) {
				attributeByName.put(attr.getName(), attr);
			}
		}
		Attribute attr = attributeByName.get(name);
		if (attr != null) {
			if (attributeClass.isInstance(attr)) {
				return attributeClass.cast(attr);
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			return null;
		}
	}
	
}
