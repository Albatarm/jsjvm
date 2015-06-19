package com.didactilab.jsjvm.client.flow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import com.didactilab.jsjvm.client.classfile.JavaClass;
import com.didactilab.jsjvm.client.classfile.JavaMethod;
import com.didactilab.jsjvm.client.classfile.JavaMethod.Parameter;
import com.didactilab.jsjvm.client.classfile.OpCode;
import com.didactilab.jsjvm.client.classfile.attribute.AbstractLocalVariableTable.Variable;
import com.didactilab.jsjvm.client.classfile.attribute.Code;
import com.didactilab.jsjvm.client.classfile.attribute.LocalVariableTable;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.classfile.constant.MethodRefConstant;
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
	private final Code codeAttr;
	private final LocalVariableTable varTable;
	private final ConstantPool constants;

	public Flow(JavaMethod method) { 
		this.method = method;
		codeAttr = method.getCodeAttribute();
		varTable = codeAttr.getAttributes().get(LocalVariableTable.NAME, LocalVariableTable.class);
		constants = method.getJavaClass().getConstantPool();
	}
	
	public void run() {
		for (Parameter param : method.getParameters()) {
			System.out.println(param);
		}
		System.out.println("return " + method.getReturnType());
		
		Deque<Object> stack = new ArrayDeque<Object>();
		
		byte[] code = codeAttr.getCode();
		int pos = 0, len = code.length;
		while (pos < code.length) {
			int opStart = pos;
			int op = code[pos++] & 0xFF;
			switch (op) {
				case OpCode.ALOAD_0:
				case OpCode.ALOAD_1:
				case OpCode.ALOAD_2:
				case OpCode.ALOAD_3:
					stack.push(getVariableAt(op - OpCode.ALOAD_0, opStart));
					break;
				case OpCode.ICONST_M1:
				case OpCode.ICONST_0:
				case OpCode.ICONST_1:
				case OpCode.ICONST_2:
				case OpCode.ICONST_3:
				case OpCode.ICONST_4:
				case OpCode.ICONST_5:
					stack.push(new FlowIntValue(op - OpCode.ICONST_M1 - 1));
					break;
				case OpCode.INVOKEVIRTUAL:
					{
						int byte1 = code[pos++] & 0xFF;
						int byte2 = code[pos++] & 0xFF;
						int index = ((byte1 << 8) | byte2) & 0xFFFF;
						MethodRefConstant methodRef = constants.get(index, MethodRefConstant.class);
						
					}
					break;
			}
		}
	}
	
	private FlowVar getVariableAt(int index, int addr) {
		boolean isStatic = method.isStatic();
		if (!isStatic && index == 0) {
			return new This();
		}
		if (varTable != null) {
			Variable var = varTable.getVariableAt(addr, index);
			if (var != null) {
				return new NamedFlowVar(var.name);
			} else {
				return new UnamedFlowVar();
			}
		} else {
			int paramCount = method.getParameters().length;
			if (isStatic && index < paramCount) {
				return new NamedFlowVar("param" + (index + 1));
			} else if (!isStatic && index < paramCount + 1) {
				return new NamedFlowVar("param" + index);
			} else {
				return new NamedFlowVar("local" + index);
			}
		}
	}
	
}
