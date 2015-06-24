package com.didactilab.jsjvm.client.classfile.attribute;

import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.util.Factory;

public class Synthetic extends Attribute {

	public static final String NAME = "Synthetic";
	
	public static final Factory<Attribute> FACTORY = new Factory<Attribute>() {
		@Override
		public Attribute create() {
			return new Synthetic();
		}
	};
	
	public Synthetic() {
		super(NAME);
	}

	@Override
	public void print(Printer printer) {
	}

}
