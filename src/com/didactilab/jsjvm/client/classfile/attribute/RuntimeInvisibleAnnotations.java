package com.didactilab.jsjvm.client.classfile.attribute;

import com.didactilab.jsjvm.client.util.Factory;

public class RuntimeInvisibleAnnotations extends AbstractRuntimeAnnotations {
	
	public static final String NAME = "RuntimeInvisibleAnnotations";
	
	public static final Factory<Attribute> FACTORY = new Factory<Attribute>() {
		@Override
		public Attribute create() {
			return new RuntimeInvisibleAnnotations();
		}
	};
	
	public RuntimeInvisibleAnnotations() {
		super(NAME);
	}
	
}
