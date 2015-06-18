package com.didactilab.jsjvm.client.classfile;

import java.io.File;
import java.io.IOException;

import com.didactilab.jsjvm.client.classfile.attribute.Attributes;
import com.didactilab.jsjvm.client.classfile.constant.ClassConstant;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.debug.SystemPrinter;
import com.didactilab.jsjvm.client.reader.FileReader;
import com.didactilab.jsjvm.client.reader.Reader;

public class JavaClass {

	private Reader reader;
	private ConstantPool constantPool = new ConstantPool();
	
	private int flags;
	private ClassConstant thisClass;
	private ClassConstant superClass;
	
	private ClassConstant[] interfaces;
	private JavaField[] fields;
	private JavaMethod[] methods;
	
	private final Attributes attributes;
	
	public JavaClass(Reader reader) {
		this.reader = reader;
		attributes = new Attributes(constantPool);
	}
	
	public void read() throws IOException {
		try {
			readHeader();
		} catch (InvalidClassFileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readHeader() throws InvalidClassFileFormatException, IOException {
		long magic = reader.readUInt32();
		int minorVersion = reader.readUInt16();
		int majorVersion = reader.readUInt16();
		
		if (magic != 0xCAFEBABE) {
			throw new InvalidClassFileFormatException("Not a CAFEBABE java class file");
		}
		System.out.println("major : " + majorVersion);
		System.out.println("minor : " + minorVersion);
		
		constantPool.read(reader);
		
		flags = reader.readUInt16();
		int thisIndex = reader.readUInt16();
		int superIndex = reader.readUInt16();
		
		thisClass = constantPool.get(thisIndex, ClassConstant.class);
		superClass = constantPool.get(superIndex, ClassConstant.class);
		
		System.out.println("this : " + thisClass);
		System.out.println("super : " + superClass);
		
		int interfaceLength = reader.readUInt16();
		interfaces = new ClassConstant[interfaceLength];
		for (int i = 0; i < interfaceLength; i++) {
			interfaces[i] = constantPool.get(reader.readUInt16(), ClassConstant.class);
			System.out.println("interface " + interfaces[i]);
		}
		
		int fieldCount = reader.readUInt16();
		fields = new JavaField[fieldCount];
		for (int i = 0; i < fieldCount; i++) {
			JavaField field = new JavaField(this);
			field.read(reader);
			fields[i] = field;
			field.print(new SystemPrinter());
		}
		
		int methodCount = reader.readUInt16();
		methods = new JavaMethod[methodCount];
		for (int i = 0; i < methodCount; i++) {
			JavaMethod method = new JavaMethod(this);
			method.read(reader);
			methods[i] = method;
			method.print(new SystemPrinter());
		}
		
		attributes.read(reader);
		attributes.print(new SystemPrinter());
		
	}
	
	public ConstantPool getConstantPool() {
		return constantPool;
	}
	
	public static void main(String[] args) throws IOException {
		File file = new File(".");
		file = file.getAbsoluteFile();
		System.out.println(file.toString());
		String filename = "test/String.class";
		FileReader reader = new FileReader(filename);
		
		JavaClass classFileReader = new JavaClass(reader);
		classFileReader.read();
		
		//
		System.out.println();
		System.out.println(UnknownAttribute.UNKNOWN_ATTRIBUTES);
		
	}
	
}
