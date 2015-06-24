package com.didactilab.jsjvm.client.classfile.attribute;

import com.didactilab.jsjvm.client.util.Factory;

public class LocalVariableTypeTable extends AbstractLocalVariableTable {
	
	public static final String NAME = "LocalVariableTypeTable";
	
	public static final Factory<Attribute> FACTORY = new Factory<Attribute>() {
		@Override
		public Attribute create() {
			return new LocalVariableTypeTable();
		}
	};
	
	public LocalVariableTypeTable() {
		super(NAME);
	}

}
