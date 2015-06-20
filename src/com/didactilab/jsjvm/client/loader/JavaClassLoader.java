package com.didactilab.jsjvm.client.loader;

import java.io.IOException;

import com.didactilab.jsjvm.client.classfile.ClassFormatException;
import com.didactilab.jsjvm.client.classfile.ClassNotFoundException;
import com.didactilab.jsjvm.client.classfile.JavaClass;
import com.didactilab.jsjvm.client.reader.Reader;

public interface JavaClassLoader {

	JavaClass defineClass(String name, Reader reader) throws ClassFormatException, IOException;
	void resolveClass(JavaClass c);
	
	JavaClass loadClass(String name) throws ClassNotFoundException;
	JavaClass findLoadedClass(String name);
	
	
}
