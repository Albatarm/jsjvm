package com.didactilab.jsjvm.client.runtime.constant;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.didactilab.jsjvm.client.reader.Reader;
import com.didactilab.jsjvm.client.runtime.InvalidClassFileFormatException;
import com.didactilab.jsjvm.client.runtime.string.StringHashTable;

public class ConstantPool {
	
	private static final Logger LOGGER = Logger.getLogger("ConstantPool");
	
	private StringHashTable stringTable = new StringHashTable();
	
	private Object[] list;
	
	public void read(Reader reader) throws InvalidClassFileFormatException, IOException {
		int count = reader.readUInt16();
		
		LOGGER.info("Constant pool with " + count + " constants");
		
		list = new Object[count - 1];
		for (int i=1; i<count; i++) {
			int type = reader.readUInt8();
			int delta = 0;
			
			LOGGER.info("read constant " + i + " => " + type);
			
			Object constant;
			switch (type) {
				case Constant.CONSTANT_CLASS: 
					constant = new ClassConstant(this, reader); 
					break;
				case Constant.CONSTANT_FIELDREF: 
					constant = new FieldRefConstant(this, reader); 
					break;
				case Constant.CONSTANT_METHODREF: 
					constant = new MethodRefConstant(this, reader); 
					break;
				case Constant.CONSTANT_INTERFACEMETHODREF: 
					constant = new InterfaceMethodRefConstant(this, reader); 
					break;
				case Constant.CONSTANT_STRING: 
					constant = new StringConstant(this, reader); 
					break;
				case Constant.CONSTANT_INTEGER: 
					constant = readInteger(reader); 
					break;
				case Constant.CONSTANT_FLOAT: 
					constant = readFloat(reader); 
					break;
				case Constant.CONSTANT_LONG: 
					constant = readLong(reader); 
					delta = 1;
					System.err.println("i++");
					break;
				case Constant.CONSTANT_DOUBLE: 
					constant = readDouble(reader); 
					delta = 1;
					System.err.println("i++");
					break;
				case Constant.CONSTANT_NAMEANDTYPE: 
					constant = new NameAndTypeConstant(this, reader); 
					break;
				case Constant.CONSTANT_UTF8: 
					constant = readUtf8(reader); 
					break;
				case Constant.CONSTANT_METHODHANDLE: 
					constant = new MethodHandleConstant(this, reader); 
					break;
				case Constant.CONSTANT_METHODTYPE: 
					constant = new MethodTypeConstant(this, reader); 
					break;
				case Constant.CONSTANT_INVOKEDYNAMIC: 
					constant = new InvokeDynamicConstant(this, reader); 
					break;
				default: throw new InvalidClassFileFormatException("constant not recognized : " + type);
			}
			list[i-1] = constant;
			i += delta;
		}
		
		if (LOGGER.isLoggable(Level.INFO)) {
			for (int i=0, c=list.length; i<c; i++) {
				if (list[i] != null) {
					System.out.println("constant " + (i+1) + " : " + list[i]);
				}
			}
		}
	}
	
	public <T> T get(int index, Class<T> constantClass) {
		Object constant = list[index - 1];
		if (constantClass.isAssignableFrom(constant.getClass())) {
		//if (constant.getClass().isAssignableFrom(constantClass)) {
			return constantClass.cast(constant);
		} else {
			throw new IllegalArgumentException("wanted class " + constantClass.getSimpleName() + ", constant class " + 
					constant.getClass().getSimpleName() + ", at index " + (index - 1));
		}
	}
	
	public String getString(int index) {
		return get(index, String.class);
	}
	
	private Object readInteger(Reader reader) throws IOException {
		return reader.readInt32();
	}
	
	private Object readFloat(Reader reader) throws IOException {
		return reader.readFloat();
	}
	
	private Object readLong(Reader reader) throws IOException {
		reader.readInt32();
		reader.readInt32();
		return 0;
	}
	
	private Object readDouble(Reader reader) throws IOException {
		return reader.readDouble();
	}
	
	private Object readUtf8(Reader reader) throws IOException {
		int length = reader.readUInt16();
		byte[] bytes = reader.readBytes(length);
		String str = new String(bytes, "UTF-8");
		return stringTable.get(str);
	}
	
}
