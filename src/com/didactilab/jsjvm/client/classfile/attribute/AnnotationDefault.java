package com.didactilab.jsjvm.client.classfile.attribute;

import java.io.IOException;

import com.didactilab.jsjvm.client.Factory;
import com.didactilab.jsjvm.client.classfile.ClassFormatException;
import com.didactilab.jsjvm.client.classfile.attribute.AbstractRuntimeAnnotations.ElementValue;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;

public class AnnotationDefault extends Attribute {

	public static final String NAME = "AnnotationDefault";
	
	public static final Factory<Attribute> FACTORY = new Factory<Attribute>() {
		@Override
		public Attribute create() {
			return new AnnotationDefault();
		}
	};
	
	private ElementValue defaultValue;
	
	public AnnotationDefault() {
		super(NAME);
	}
	
	@Override
	public void read(ConstantPool constants, Reader reader) throws IOException,
			ClassFormatException {
		super.read(constants, reader);
		defaultValue = ElementValue.read(reader, constants);
	}

	@Override
	public void print(Printer printer) {
		printer.println(defaultValue);
	}
	
	public ElementValue getDefaultValue() {
		return defaultValue;
	}

}
