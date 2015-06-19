package com.didactilab.jsjvm.client.classfile.attribute;

import com.didactilab.jsjvm.client.Factory;

public class RuntimeVisibleParameterAnnotations extends AbstractRuntimeParameterAnnotations {
	
	public static final String NAME = "RuntimeVisibleParameterAnnotations";
	
	public static final Factory<Attribute> FACTORY = new Factory<Attribute>() {
		@Override
		public Attribute create() {
			return new RuntimeVisibleParameterAnnotations();
		}
	};
	
	public RuntimeVisibleParameterAnnotations() {
		super(NAME);
	}
	
}
