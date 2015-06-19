package com.didactilab.jsjvm.client.classfile.attribute;

import java.io.IOException;

import com.didactilab.jsjvm.client.Joiner;
import com.didactilab.jsjvm.client.classfile.ClassFormatException;
import com.didactilab.jsjvm.client.classfile.attribute.AbstractRuntimeAnnotations.Annotation;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.reader.Reader;

public class AbstractRuntimeParameterAnnotations extends Attribute {

	public static class Parameter {
		public final Annotation[] annotations;
		
		public Parameter(ConstantPool constants, Reader reader) throws IOException,
				ClassFormatException {
			int len = reader.readUInt16();
			annotations = new Annotation[len];
			for (int i = 0; i < len; i++) {
				annotations[i] = new Annotation(reader, constants);
			}
		}
		
		@Override
		public String toString() {
			return Joiner.join(annotations);
		}
	}
	
	private Parameter[] parameters;
	
	public AbstractRuntimeParameterAnnotations(String name) {
		super(name);
	}
	
	@Override
	public void read(ConstantPool constants, Reader reader) throws IOException,
			ClassFormatException {
		super.read(constants, reader);
		int len = reader.readUInt16();
		parameters = new Parameter[len];
		for (int i = 0; i < len; i++) {
			parameters[i] = new Parameter(constants, reader);
		}
	}

	@Override
	public void print(Printer printer) {
		for (Parameter parameter : parameters) {
			printer.println(parameter);
		}
	}

}
