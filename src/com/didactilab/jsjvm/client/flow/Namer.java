package com.didactilab.jsjvm.client.flow;

import com.didactilab.jsjvm.client.classfile.JavaClass;
import com.didactilab.jsjvm.client.classfile.JavaField;
import com.didactilab.jsjvm.client.classfile.JavaMethod;

public interface Namer {
	String getNameOf(JavaClass clazz);
	String getNameOf(JavaMethod method);
	String getNameOf(JavaField field);
}
