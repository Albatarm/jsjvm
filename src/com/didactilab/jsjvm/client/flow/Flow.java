package com.didactilab.jsjvm.client.flow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;

import com.didactilab.jsjvm.client.classfile.ClassNotFoundException;
import com.didactilab.jsjvm.client.classfile.JavaClass;
import com.didactilab.jsjvm.client.classfile.JavaField;
import com.didactilab.jsjvm.client.classfile.JavaMethod;
import com.didactilab.jsjvm.client.classfile.JavaMethod.Parameter;
import com.didactilab.jsjvm.client.classfile.OpCode;
import com.didactilab.jsjvm.client.classfile.OpCodeData;
import com.didactilab.jsjvm.client.classfile.attribute.AbstractLocalVariableTable.Variable;
import com.didactilab.jsjvm.client.classfile.attribute.ArrayType;
import com.didactilab.jsjvm.client.classfile.attribute.Code;
import com.didactilab.jsjvm.client.classfile.attribute.LocalVariableTable;
import com.didactilab.jsjvm.client.classfile.constant.ClassConstant;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.classfile.constant.FieldRefConstant;
import com.didactilab.jsjvm.client.classfile.constant.MethodHandleConstant;
import com.didactilab.jsjvm.client.classfile.constant.MethodRefConstant;
import com.didactilab.jsjvm.client.classfile.constant.MethodTypeConstant;
import com.didactilab.jsjvm.client.classfile.constant.StringConstant;
import com.didactilab.jsjvm.client.classfile.descriptor.DescriptorParser;
import com.didactilab.jsjvm.client.classfile.descriptor.Type;
import com.didactilab.jsjvm.client.debug.SystemPrinter;
import com.didactilab.jsjvm.client.loader.JRESystemJavaClassLoader;
import com.didactilab.jsjvm.client.loader.JavaClassLoader;

public class Flow {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		JRESystemJavaClassLoader classLoader = new JRESystemJavaClassLoader(new File("build/runtime"));
		JavaClass javaClass = classLoader.loadClass("java/lang/String");
		JavaMethod method = javaClass.findMethod("valueOf", "(Ljava/lang/Object;)Ljava/lang/String;");
		//JavaMethod method = javaClass.findMethod("getBytes", "(Ljava/nio/charset/Charset;)[B");
		//JavaMethod method = javaClass.findMethod("copyValueOf", "([CII)Ljava/lang/String;");
		//JavaMethod method = javaClass.findMethod("toCharArray", "()[C");
		//JavaMethod method = javaClass.findMethod("getChars", "([CI)V");
		//JavaMethod method = javaClass.findMethod("split", "(Ljava/lang/String;)[Ljava/lang/String;");
		method.print(new SystemPrinter());
		
		Flow flow = new Flow(method);
		flow.run();
	}
	
	/*private static class BlockPosition {
		public final FlowBlock block;
		public final int addr;
		
		public BlockPosition(FlowBlock block, int addr) {
			this.block = block;
			this.addr = addr;
		}
	}*/
	
	private final JavaClassLoader classLoader;
	private final JavaMethod method;
	private final Code codeAttr;
	private final LocalVariableTable varTable;
	private final ConstantPool constants;
	
	private final CodeParser parser;
	
	private FlowVar[] locals;

	public Flow(JavaMethod method) { 
		this.method = method;
		codeAttr = method.getCodeAttribute();
		varTable = codeAttr.getAttributes().get(LocalVariableTable.NAME, LocalVariableTable.class);
		constants = method.getJavaClass().getConstantPool();
		classLoader = method.getJavaClass().getClassLoader();
		parser = new CodeParser(codeAttr.getCode());
		
		initLocals();
	}
	
	private void initLocals() {
		int paramCount = method.getParameters().length;
		locals = new FlowVar[codeAttr.getMaxLocals()];
		int startParameterIndex = method.isStatic() ? 0 : 1;
		for (int i=0; i<paramCount; i++) {
			Parameter param = method.getParameters()[i];
			locals[startParameterIndex + i] = new FlowParameter(param);
		}
		if (!method.isStatic()) {
			locals[0] = new FlowThis();
		}
	}
	
	private FlowVar getLocalSetter(int index, int addr, FlowValue value) {
		if (locals[index] == null) {
			Type type;
			String name;
			if (varTable != null) {
				Variable var = varTable.getVariableAt(addr, index);
				if (var == null) {
					throw new IllegalStateException("Local " + index + " at " + addr + " not found in var table");
				}
				DescriptorParser parser = new DescriptorParser(var.descriptor);
				type = parser.parseField();
				name = var.name;
			} else {
				type = value.getType();
				name = "local" + index;
			}
			FlowLocal local = new FlowLocal(name);
			locals[index] = local;
			return new FlowDeclareVar(local, type);
		}
		return locals[index];
	}
	
	private FlowVar getLocal(int index) {
		if (locals[index] == null) {
			throw new IllegalStateException("local " + index + " is not initialized");
		}
		return locals[index];
	}
	
	private FlowBlock decompile(int position) throws ClassNotFoundException {
		
		System.out.println("begin block");
		
		parser.setPosition(position);
		
		FlowBlock current = new FlowBlock();
		Deque<FlowObject> stack = current.stack;
		
		boolean leave = false;
		while (!leave) {
			int op = parser.nextOp();
			System.out.println("  stack " + reversed(stack));
			System.out.println("  read " + OpCodeData.valueOf(op));
			switch (op) {
				case OpCode.ALOAD_0:
				case OpCode.ALOAD_1:
				case OpCode.ALOAD_2:
				case OpCode.ALOAD_3:
					stack.push(getLocal(op - OpCode.ALOAD_0));
					break;
				case OpCode.ILOAD_0:
				case OpCode.ILOAD_1:
				case OpCode.ILOAD_2:
				case OpCode.ILOAD_3:
					stack.push(getLocal(op - OpCode.ILOAD_0));
					break;
				case OpCode.ASTORE_0:
				case OpCode.ASTORE_1:
				case OpCode.ASTORE_2:
				case OpCode.ASTORE_3:
					{
						FlowVar var = getLocalSetter(op - OpCode.ASTORE_0, parser.getLastOpPosition(), null);
						stack.push(new FlowAssign(var, stack.pop()));
					}
					break;
				case OpCode.ICONST_M1:
				case OpCode.ICONST_0:
				case OpCode.ICONST_1:
				case OpCode.ICONST_2:
				case OpCode.ICONST_3:
				case OpCode.ICONST_4:
				case OpCode.ICONST_5:
					stack.push(new FlowIntConst(op - OpCode.ICONST_M1 - 1));
					break;
				case OpCode.LDC:
					{
						int index = parser.nextConstantPoolIndexByte();
						Object constObj = constants.get(index, Object.class);
						if (constObj instanceof Integer) {
							stack.push(new FlowIntConst((Integer) constObj));
						} else if (constObj instanceof Float) {
							stack.push(new FlowFloatConst((Float) constObj));
						} else if (constObj instanceof StringConstant) {
							stack.push(new FlowStringConst(((StringConstant) constObj).value()));
						} else if (constObj instanceof ClassConstant) {
							throw new UnsupportedOperationException();
						} else if (constObj instanceof MethodTypeConstant) {
							throw new UnsupportedOperationException();
						} else if (constObj instanceof MethodHandleConstant) {
							throw new UnsupportedOperationException();
						} else {
							throw new IllegalStateException();
						}
					}
					break;
				case OpCode.INVOKEVIRTUAL:
					{
						JavaMethod anotherMethod = findMethod(parser.nextConstantPoolIndex());
						int numParameter = anotherMethod.getParameters().length;
						ArrayList<FlowObject> list = new ArrayList<>();
						for (int i=0; i<numParameter; i++) {
							list.add(0, stack.pop());
						}
						FlowObject instance = stack.pop();
						stack.push(new FlowCall(anotherMethod, instance, list));
					}
					break;
				case OpCode.INVOKESTATIC:
					{
						JavaMethod theMethod = findMethod(parser.nextConstantPoolIndex());
						int numParameter = theMethod.getParameters().length;
						ArrayList<FlowObject> list = new ArrayList<>();
						for (int i=0; i<numParameter; i++) {
							list.add(0, stack.pop());
						}
						stack.push(new FlowCall(theMethod, list));
					}
					break;
				case OpCode.INVOKESPECIAL:
					{
						JavaMethod theMethod = findMethod(parser.nextConstantPoolIndex());
						int numParameter = theMethod.getParameters().length;
						ArrayList<FlowObject> list = new ArrayList<>();
						for (int i=0; i<numParameter; i++) {
							list.add(0, stack.pop());
						}
						if (theMethod.isConstructor()) {
							FlowNew instance = (FlowNew) stack.pop();
							instance.setParameters(list);
						} else {
							throw new UnsupportedOperationException(theMethod.getName());
						}
					}
					break;
				case OpCode.IFNONNULL: 
					{
						int offset = parser.nextBranchOffset();
						int next = parser.getPosition();
						System.out.println("  --> true");
						FlowBlock trueBlock = decompile(parser.getLastOpPosition() + offset);
						System.out.println("  --> false");
						FlowBlock falseBlock = decompile(next);
						
						stack.push(new FlowIf(new FlowCond(stack.pop(), "!=", new FlowNull()), trueBlock, falseBlock));
						leave = true;
					}
					break;
				case OpCode.ARETURN:
				case OpCode.IRETURN:
					stack.push(new FlowReturn(stack.pop()));
					leave = true;
					break;
				case OpCode.RETURN:
					leave = true;
					break;
				case OpCode.ATHROW:
					stack.push(new FlowThrow(stack.pop()));
					leave = true;
					break;
				case OpCode.GETFIELD:
					{
						JavaField field = findField(parser.nextConstantPoolIndex());
						stack.push(new FlowField(stack.pop(), field));
					}
					break;
				case OpCode.NEW:
					{
						JavaClass clazz = findClass(parser.nextConstantPoolIndex());
						stack.push(new FlowNew(clazz));
					}
					break;
				case OpCode.NEWARRAY:
					{
						ArrayType atype = parser.nextArrayType();
						stack.push(new FlowNewArray(atype, stack.pop()));
					}
					break;
				case OpCode.ARRAYLENGTH:
					stack.push(new FlowArrayLength(stack.pop()));
					break;
				case OpCode.DUP:
					stack.push(stack.peek());
					break;
				default:
					throw new IllegalArgumentException("Not complete " + OpCodeData.valueOf(op));
			}
		}
		
		System.out.println("end block");
		
		return current;
	}
	
	public void run() throws ClassNotFoundException {
		/*for (Parameter param : method.getParameters()) {
			System.out.println(param);
		}
		System.out.println("return " + method.getReturnType());
		
		FlowBlock methodBlock = new FlowBlock();
		FlowBlock lastBlock = methodBlock;
		
		Deque<BlockPosition> blocks = new LinkedList<BlockPosition>();
		blocks.push(new BlockPosition(methodBlock, 0));
		
		CodeParser parser = new CodeParser(codeAttr.getCode());
		
		while (parser.hasMore()) {
			
			FlowBlock currentBlock = blocks.peek().block;
			Deque<FlowObject> stack = currentBlock.stack;
			if (lastBlock != currentBlock) {
				parser.setPosition(blocks.peek().addr);
				lastBlock = currentBlock;
			}
			
			int op = parser.nextOp();
			switch (op) {
				case OpCode.ALOAD_0:
				case OpCode.ALOAD_1:
				case OpCode.ALOAD_2:
				case OpCode.ALOAD_3:
					stack.push(getLocal(op - OpCode.ALOAD_0));
					break;
				case OpCode.ILOAD_0:
				case OpCode.ILOAD_1:
				case OpCode.ILOAD_2:
				case OpCode.ILOAD_3:
					stack.push(getLocal(op - OpCode.ILOAD_0));
					break;
				case OpCode.ASTORE_0:
				case OpCode.ASTORE_1:
				case OpCode.ASTORE_2:
				case OpCode.ASTORE_3:
					{
						FlowVar var = getLocalSetter(op - OpCode.ASTORE_0, parser.getLastOpPosition(), null);
						stack.push(new FlowAssign(var, stack.pop()));
					}
					break;
				case OpCode.ICONST_M1:
				case OpCode.ICONST_0:
				case OpCode.ICONST_1:
				case OpCode.ICONST_2:
				case OpCode.ICONST_3:
				case OpCode.ICONST_4:
				case OpCode.ICONST_5:
					stack.push(new FlowIntConst(op - OpCode.ICONST_M1 - 1));
					break;
				case OpCode.INVOKEVIRTUAL:
					{
						JavaMethod anotherMethod = findMethod(parser.nextConstantPoolIndex());
						int numParameter = anotherMethod.getParameters().length;
						ArrayList<FlowObject> list = new ArrayList<>();
						for (int i=0; i<numParameter; i++) {
							list.add(0, stack.pop());
						}
						FlowObject instance = stack.pop();
						stack.push(new FlowCall(anotherMethod, instance, list));
					}
					break;
				case OpCode.INVOKESTATIC:
					{
						JavaMethod theMethod = findMethod(parser.nextConstantPoolIndex());
						int numParameter = theMethod.getParameters().length;
						ArrayList<FlowObject> list = new ArrayList<>();
						for (int i=0; i<numParameter; i++) {
							list.add(0, stack.pop());
						}
						stack.push(new FlowCall(theMethod, list));
					}
					break;
				case OpCode.INVOKESPECIAL:
					{
						JavaMethod theMethod = findMethod(parser.nextConstantPoolIndex());
						int numParameter = theMethod.getParameters().length;
						ArrayList<FlowObject> list = new ArrayList<>();
						for (int i=0; i<numParameter; i++) {
							list.add(0, stack.pop());
						}
						if (theMethod.isConstructor()) {
							// pop the instance
							//stack.pop();
							FlowNew instance = (FlowNew) stack.pop();
							instance.setParameters(list);
						} else {
							throw new UnsupportedOperationException(theMethod.getName());
						}
					}
					break;
				case OpCode.IFNONNULL: 
					{
						int offset = parser.nextBranchOffset();
						FlowBlock trueBlock = new FlowBlock();
						FlowBlock falseBlock = new FlowBlock();
						stack.push(new FlowIf(new FlowCond(stack.pop(), "!=", new FlowNull()), trueBlock, falseBlock));
						
						blocks.push(new BlockPosition(falseBlock, parser.getPosition()));
						blocks.push(new BlockPosition(trueBlock, parser.getLastOpPosition() + offset));
					}
					break;
				case OpCode.ARETURN:
				case OpCode.IRETURN:
					stack.push(new FlowReturn(stack.pop()));
					blocks.pop();
					break;
				case OpCode.RETURN:
					blocks.pop();
					break;
				case OpCode.GETFIELD:
					{
						JavaField field = findField(parser.nextConstantPoolIndex());
						stack.push(new FlowField(stack.pop(), field));
					}
					break;
				case OpCode.NEW:
					{
						JavaClass clazz = findClass(parser.nextConstantPoolIndex());
						stack.push(new FlowNew(clazz));
					}
					break;
				case OpCode.NEWARRAY:
					{
						ArrayType atype = parser.nextArrayType();
						stack.push(new FlowNewArray(atype, stack.pop()));
					}
					break;
				case OpCode.ARRAYLENGTH:
					stack.push(new FlowArrayLength(stack.pop()));
					break;
				case OpCode.DUP:
					stack.push(stack.peek());
					break;
				default:
					throw new IllegalArgumentException("Not complete " + OpCodeData.valueOf(op));
			}
		}*/
		FlowBlock methodBlock = decompile(0);
		
		System.out.println("\nSource ");
		System.out.println(methodBlock.toSource());
	}
	
	private JavaMethod findMethod(int constantPoolIndex) throws ClassNotFoundException {
		MethodRefConstant methodRef = constants.get(constantPoolIndex, MethodRefConstant.class);
		JavaClass jClass = classLoader.loadClass(methodRef.getClassName().getName());
		return jClass.findMethod(methodRef.getNameAndType().getName(), 
				methodRef.getNameAndType().getDescriptor());
	}
	
	private JavaField findField(int constantPoolIndex) throws ClassNotFoundException {
		FieldRefConstant fieldRef = constants.get(constantPoolIndex, FieldRefConstant.class);
		JavaClass anotherClass = classLoader.loadClass(fieldRef.getClassName().getName());
		return anotherClass.getField(fieldRef.getNameAndType().getName());
	}
	
	private JavaClass findClass(int constantPoolIndex) throws ClassNotFoundException {
		ClassConstant classConstant = constants.get(constantPoolIndex, ClassConstant.class);
		return classLoader.loadClass(classConstant.getName());
	}
	
	private <T> Collection<T> reversed(Collection<T> c) {
		ArrayList<T> l = new ArrayList<>(c);
		Collections.reverse(l);
		return l;
	}
	
}
