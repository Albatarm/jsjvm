package com.didactilab.jsjvm.client.flow;

import java.io.File;
import java.io.IOException;

import com.didactilab.jsjvm.client.classfile.JavaClass;
import com.didactilab.jsjvm.client.classfile.JavaMethod;
import com.didactilab.jsjvm.client.classfile.JavaMethod.Parameter;
import com.didactilab.jsjvm.client.debug.SystemPrinter;
import com.didactilab.jsjvm.client.reader.FileReader;

public class Flow {

	public static void main(String[] args) throws IOException {
		File file = new File("build/runtime/java/lang/String.class");
		FileReader reader = new FileReader(file.toString());
		JavaClass javaClass = new JavaClass(reader);
		javaClass.read();
		JavaMethod method = javaClass.findMethod("split", "(Ljava/lang/String;)[Ljava/lang/String;");
		method.print(new SystemPrinter());
		
		Flow flow = new Flow(method);
		flow.run();
	}
	
	private final JavaMethod method;

	public Flow(JavaMethod method) { 
		this.method = method;
	}
	
	public void run() {
		for (Parameter param : method.getParameters()) {
			System.out.println(param);
		}
		System.out.println("return " + method.getReturnType());
	}
	
}
