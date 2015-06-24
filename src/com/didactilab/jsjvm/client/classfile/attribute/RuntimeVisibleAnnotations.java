package com.didactilab.jsjvm.client.classfile.attribute;

import com.didactilab.jsjvm.client.util.Factory;

public class RuntimeVisibleAnnotations extends AbstractRuntimeAnnotations {
	
	public static final String NAME = "RuntimeVisibleAnnotations";
	
	public static final Factory<Attribute> FACTORY = new Factory<Attribute>() {
		@Override
		public Attribute create() {
			return new RuntimeVisibleAnnotations();
		}
	};
	
	public RuntimeVisibleAnnotations() {
		super(NAME);
	}
	
}
