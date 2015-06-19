package com.didactilab.jsjvm.client.classfile;

import java.io.File;
import java.io.IOException;

import com.didactilab.jsjvm.client.classfile.attribute.Attributes;
import com.didactilab.jsjvm.client.classfile.constant.ClassConstant;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.debug.IndentedPrinter;
import com.didactilab.jsjvm.client.debug.Printer;
import com.didactilab.jsjvm.client.debug.SystemPrinter;
import com.didactilab.jsjvm.client.reader.FileReader;
import com.didactilab.jsjvm.client.reader.Reader;

public class JavaClass {

	private static final int MAGIC = 0xCAFEBABE;
	
	private Reader reader;
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
		minorVersion = reader.readUInt16();
		majorVersion = reader.readUInt16();
		
		if (magic != MAGIC) {
			throw new InvalidClassFileFormatException("Not a CAFEBABE java class file");
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
	
	public static void main(String[] args) throws IOException {
		File dir = new File("build/runtime/java/lang");
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.toString().endsWith(".class")) {
				FileReader reader = new FileReader(file.toString());
				JavaClass classFileReader = new JavaClass(reader);
				classFileReader.read();
				classFileReader.print(new SystemPrinter());
			}
		}
		
		/*String filename = "build/runtime/java/lang/String.class";
		FileReader reader = new FileReader(filename);
		
		JavaClass classFileReader = new JavaClass(reader);
		classFileReader.read();*/
		
		//
		System.out.println();
		System.out.println(UnknownAttribute.UNKNOWN_ATTRIBUTES);
		
	}
	
}
