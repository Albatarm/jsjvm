package com.didactilab.jsjvm.client.classfile.attribute;

import com.didactilab.jsjvm.client.util.Factory;

public class LocalVariableTable extends AbstractLocalVariableTable {

	public static final String NAME = "LocalVariableTable";
	
	public static final Factory<Attribute> FACTORY = new Factory<Attribute>() {
		@Override
		public Attribute create() {
			return new LocalVariableTable();
		}
	};
	
	public LocalVariableTable() {
		super(NAME);
	}

}
