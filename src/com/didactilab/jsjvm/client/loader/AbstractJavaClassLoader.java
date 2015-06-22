package com.didactilab.jsjvm.client.loader;

import com.didactilab.jsjvm.client.classfile.ClassNotFoundException;
import com.didactilab.jsjvm.client.classfile.JavaClass;


public abstract class AbstractJavaClassLoader implements JavaClassLoader {

	@Override
	public JavaClass loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		JavaClass cls = loadClass(name);
		if (resolve) {
			resolveClass(cls);
		}
		return cls;
	}

}
