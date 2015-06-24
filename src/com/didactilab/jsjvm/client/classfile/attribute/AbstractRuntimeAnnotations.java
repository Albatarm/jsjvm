package com.didactilab.jsjvm.client.classfile.attribute;

import java.io.IOException;

import com.didactilab.jsjvm.client.classfile.ClassFormatException;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;
import com.didactilab.jsjvm.client.util.Joiner;

public abstract class AbstractRuntimeAnnotations extends Attribute {
	
	public static class ElementValue {
		
		public static ElementValue read(Reader reader, ConstantPool constants) throws IOException, ClassFormatException {
			char tag = (char) reader.readUInt8();
			switch (tag) {
				case 'B':
				case 'C':
				case 'D':
				case 'F':
				case 'I':
				case 'J':
				case 'S':
				case 'Z':
				case 's':
					return new ConstElementValue(reader, constants);
				case 'e':
					return new EnumConstElementValue(reader, constants);
				case 'c':
					return new ClassElementValue(reader, constants);
				case '@':
					return new AnnotationElementValue(reader, constants);
				case '[':
					return new ArrayElementValue(reader, constants);
				default:
					throw new ClassFormatException("Invalid tag '" + tag + "' of element value");
			}
		}
		
	}
	
	public static class ConstElementValue extends ElementValue {
		public final Object value;
		
		ConstElementValue(Reader reader, ConstantPool constants) throws IOException, ClassFormatException {
			value = constants.get(reader.readUInt16(), Object.class);
		}
		
		@Override
		public String toString() {
			return String.valueOf(value);
		}
	}
	
	public static class EnumConstElementValue extends ElementValue {
		public final String typeName;
		public final String constName;
		
		EnumConstElementValue(Reader reader, ConstantPool constants) throws IOException, ClassFormatException {
			typeName = constants.getString(reader.readUInt16());
			constName = constants.getString(reader.readUInt16());
		}
		
		@Override
		public String toString() {
			return typeName + "." + constName;
		}
	}
	
	public static class ClassElementValue extends ElementValue {
		public final String descriptor;
		
		ClassElementValue(Reader reader, ConstantPool constants) throws IOException, ClassFormatException {
			descriptor = constants.getString(reader.readUInt16());
		}
		
		@Override
		public String toString() {
			return "class " + descriptor;
		}
	}
	
	public static class AnnotationElementValue extends ElementValue {
		public final Annotation annotation;
		
		AnnotationElementValue(Reader reader, ConstantPool constants) throws IOException, ClassFormatException {
			annotation = new Annotation(reader, constants);
		}
		
		@Override
		public String toString() {
			return annotation.toString();
		}
	}
	
	public static class ArrayElementValue extends ElementValue {
		public final ElementValue[] values;
		
		ArrayElementValue(Reader reader, ConstantPool constants) throws IOException, ClassFormatException {
			int len = reader.readUInt16();
			values = new ElementValue[len];
			for (int i = 0; i < len; i++) {
				values[i] = ElementValue.read(reader, constants);
			}
		}		
	}
	
	public static class Element {
		public final String name;
		public final ElementValue value;
		
		public Element(Reader reader, ConstantPool constants) throws IOException, ClassFormatException {
			name = constants.getString(reader.readUInt16());
			value = ElementValue.read(reader, constants);
		}
		
		@Override
		public String toString() {
			return name + ": " + value;
		}
	}
	
	public static class Annotation {
		public final String type;
		public final Element[] elements;
		
		public Annotation(Reader reader, ConstantPool constants) throws IOException, ClassFormatException {
			int index = reader.readUInt16();
			type = constants.getString(index);
			int length = reader.readUInt16();
			elements = new Element[length];
			for (int i = 0; i < length; i++) {
				elements[i] = new Element(reader, constants);
			}
		}
		
		@Override
		public String toString() {
			return type + " (" + Joiner.join(elements) + ")";
		}
	}
	
	private Annotation[] annotations;
	
	public AbstractRuntimeAnnotations(String name) {
		super(name);
	}
	
	@Override
	public void read(ConstantPool constants, Reader reader) throws IOException,
			ClassFormatException {
		super.read(constants, reader);
		int len = reader.readUInt16();
		annotations = new Annotation[len];
		for (int i = 0; i < len; i++) {
			annotations[i] = new Annotation(reader, constants);
		}
	}

	@Override
	public void print(Printer printer) {
		for (Annotation annotation : annotations) {
			printer.println(annotation);
		}
	}

}
