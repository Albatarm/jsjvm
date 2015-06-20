package com.didactilab.jsjvm.client.loader;

import java.io.IOException;
import java.util.HashMap;

import com.didactilab.jsjvm.client.classfile.ClassFormatException;
import com.didactilab.jsjvm.client.classfile.ClassNotFoundException;
import com.didactilab.jsjvm.client.classfile.JavaClass;
import com.didactilab.jsjvm.client.reader.Reader;

public abstract class SystemJavaClassLoader extends AbstractJavaClassLoader {
	
	private final HashMap<String, JavaClass> classes = new HashMap<String, JavaClass>();

	@Override
	public JavaClass loadClass(String name) throws ClassNotFoundException {
		JavaClass c = findLoadedClass(name);
		if (c == null) {
			try {
				Reader reader = openReaderForClass(name);
				try {
					c = defineClass(name, reader);
				} catch (ClassFormatException e) {
					throw new ClassNotFoundException(e);
				} finally {
					reader.close();
				}
			} catch (IOException e) {
				throw new ClassNotFoundException(e);
			}
		}
		return c ;
	}

	@Override
	public JavaClass findLoadedClass(String name) {
		return classes.get(name);
	}

	@Override
	public void resolveClass(JavaClass c) {
		classes.put(c.getName(), c);
	}

	@Override
	public JavaClass defineClass(String name, Reader reader) throws ClassFormatException, IOException {
		JavaClass newJavaClass = new JavaClass(this);
		newJavaClass.read(reader);
		return newJavaClass;
	}
	
	protected abstract Reader openReaderForClass(String name) throws IOException;
	
}
