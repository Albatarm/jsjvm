package com.didactilab.jsjvm.client.classfile;

import java.io.File;
import java.io.IOException;

import com.didactilab.jsjvm.client.classfile.attribute.Attributes;
import com.didactilab.jsjvm.client.classfile.constant.ClassConstant;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.debug.IndentedPrinter;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.debug.SystemPrinter;
import com.didactilab.jsjvm.client.loader.JRESystemJavaClassLoader;
import com.didactilab.jsjvm.client.loader.JavaClassLoader;
import com.didactilab.jsjvm.client.reader.Reader;

public class JavaClass {

	private static final int MAGIC = 0xCAFEBABE;
	
	private final JavaClassLoader classLoader;
	
	private ConstantPool constantPool = new ConstantPool();
	
	private int minorVersion;
	private int majorVersion;
	
	private int flags;
	private ClassConstant thisClass;
	private ClassConstant superClass;
	
	private ClassConstant[] interfaces;
	private JavaField[] fields;
	private JavaMethod[] methods;
	
	private final Attributes attributes;
	
	public JavaClass(JavaClassLoader classLoader) {
		this.classLoader = classLoader;
		attributes = new Attributes(constantPool);
	}
	
	public void read(Reader reader) throws IOException {
		try {
			readHeader(reader);
		} catch (ClassFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readHeader(Reader reader) throws ClassFormatException, IOException {
		long magic = reader.readUInt32();
		minorVersion = reader.readUInt16();
		majorVersion = reader.readUInt16();
		
		if (magic != MAGIC) {
			throw new ClassFormatException("Not a CAFEBABE java class file");
		}
		
		constantPool.read(reader);
		
		flags = reader.readUInt16();
		int thisIndex = reader.readUInt16();
		int superIndex = reader.readUInt16();
		
		thisClass = constantPool.get(thisIndex, ClassConstant.class);
		superClass = constantPool.get(superIndex, ClassConstant.class);
		
		int interfaceLength = reader.readUInt16();
		interfaces = new ClassConstant[interfaceLength];
		for (int i = 0; i < interfaceLength; i++) {
			interfaces[i] = constantPool.get(reader.readUInt16(), ClassConstant.class);
		}
		
		int fieldCount = reader.readUInt16();
		fields = new JavaField[fieldCount];
		for (int i = 0; i < fieldCount; i++) {
			JavaField field = new JavaField(this);
			field.read(reader);
			fields[i] = field;
		}
		
		int methodCount = reader.readUInt16();
		methods = new JavaMethod[methodCount];
		for (int i = 0; i < methodCount; i++) {
			JavaMethod method = new JavaMethod(this);
			method.read(reader);
			methods[i] = method;
		}
		
		attributes.read(reader);
	}
	
	public void print(Printer printer) {
		printer.println("version ", JavaVersion.valueOf(majorVersion), " minor ", minorVersion);
		printer.print("class ", thisClass.getName(), " extends ", superClass.getName());
		if (interfaces.length != 0) {
			printer.print(" implements");
			for (ClassConstant inf : interfaces) {
				printer.print(" " + inf.getName());
			}
		} 
		printer.println();
		Printer p = new IndentedPrinter(printer, "  ");
		for (JavaField field : fields) {
			field.print(p);
		}
		for (JavaMethod method : methods) {
			method.print(p);
		}
		attributes.print(p);
		
	}
	
	public ConstantPool getConstantPool() {
		return constantPool;
	}
	
	public JavaMethod findMethod(String name, String descriptor) {
		for (JavaMethod method : methods) {
			if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
				return method;
			}
		}
		return null;
	}
	
	public JavaField getField(String name) {
		for (JavaField field : fields) {
			if (field.getName().equals(name)) {
				return field;
			}
		}
		return null;
	}
	
	public JavaClassLoader getClassLoader() {
		return classLoader;
	}
	
	public String getName() {
		return thisClass.getName();
	}
	
	public String getJavaName() {
		return thisClass.getName().replace('/', '.');
	}
	
	public static void main(String[] args) throws IOException {
		JRESystemJavaClassLoader classLoader = new JRESystemJavaClassLoader(new File("build/runtime"));
		try {
			JavaClass clazz = classLoader.loadClass("java/lang/String");
			clazz.print(new SystemPrinter());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("\nUnknown attributes : " + UnknownAttribute.UNKNOWN_ATTRIBUTES);
		
	}
	
}
