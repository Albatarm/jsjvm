package com.didactilab.jsjvm.client.loader;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Logger;

import com.didactilab.jsjvm.client.classfile.ClassFormatException;
import com.didactilab.jsjvm.client.classfile.ClassNotFoundException;
import com.didactilab.jsjvm.client.classfile.JavaClass;
import com.didactilab.jsjvm.client.reader.Reader;

public abstract class SystemJavaClassLoader extends AbstractJavaClassLoader {
	
	private final Logger LOGGER = Logger.getLogger("SystemJavaClassLoader");
	
	private final HashMap<String, JavaClass> classes = new HashMap<String, JavaClass>();

	@Override
	public JavaClass loadClass(String name) throws ClassNotFoundException {
		JavaClass c = findLoadedClass(name);
		if (c == null) {
			try {
				Reader reader = openReaderForClass(name);
				try {
					c = defineClass(name, reader);
					classes.put(c.getName(), c);
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
	public void resolveClass(JavaClass c) throws ClassNotFoundException {
		if (c.getClassLoader() != this) {
			throw new IllegalArgumentException("This class loader is not the one the the java class : " + c.getName());
		}
		c.resolve();
	}

	@Override
	public JavaClass defineClass(String name, Reader reader) throws ClassFormatException, IOException {
		LOGGER.info("Define class " + name);
		JavaClass newJavaClass = new JavaClass(this);
		newJavaClass.read(reader);
		return newJavaClass;
	}
	
	protected abstract Reader openReaderForClass(String name) throws IOException;
	
	@Override
	public Collection<JavaClass> getClasses() {
		return Collections.unmodifiableCollection(classes.values());
	}
	
}
