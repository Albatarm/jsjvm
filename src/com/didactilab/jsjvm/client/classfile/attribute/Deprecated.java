package com.didactilab.jsjvm.client.classfile.attribute;

import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.util.Factory;

public class Deprecated extends Attribute {

	public static final String NAME = "Deprecated";
	
	public static final Factory<Attribute> FACTORY = new Factory<Attribute>() {
		@Override
		public Attribute create() {
			return new Deprecated();
		}
	};
	
	public Deprecated() {
		super(NAME);
	}

	@Override
	public void print(Printer printer) {
	}

}
