package com.didactilab.jsjvm.client.classfile.attribute;

import com.didactilab.jsjvm.client.Factory;

public class RuntimeInvisibleParameterAnnotations extends AbstractRuntimeParameterAnnotations {
	
	public static final String NAME = "RuntimeInvisibleParameterAnnotations";
	
	public static final Factory<Attribute> FACTORY = new Factory<Attribute>() {
		@Override
		public Attribute create() {
			return new RuntimeInvisibleParameterAnnotations();
		}
	};
	
	public RuntimeInvisibleParameterAnnotations() {
		super(NAME);
	}
	
}
