package com.didactilab.jsjvm.client.flow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;

import com.didactilab.jsjvm.client.classfile.ArrayType;
import com.didactilab.jsjvm.client.classfile.ClassNotFoundException;
import com.didactilab.jsjvm.client.classfile.JavaClass;
import com.didactilab.jsjvm.client.classfile.JavaField;
import com.didactilab.jsjvm.client.classfile.JavaMethod;
import com.didactilab.jsjvm.client.classfile.JavaMethod.Parameter;
import com.didactilab.jsjvm.client.classfile.OpCode;
import com.didactilab.jsjvm.client.classfile.OpCodeData;
import com.didactilab.jsjvm.client.classfile.PrimitiveType;
import com.didactilab.jsjvm.client.classfile.Type;
import com.didactilab.jsjvm.client.classfile.attribute.AbstractLocalVariableTable.Variable;
import com.didactilab.jsjvm.client.classfile.attribute.Code;
import com.didactilab.jsjvm.client.classfile.attribute.CodeArrayType;
import com.didactilab.jsjvm.client.classfile.attribute.LocalVariableTable;
import com.didactilab.jsjvm.client.classfile.constant.ClassConstant;
import com.didactilab.jsjvm.client.classfile.constant.ConstantPool;
import com.didactilab.jsjvm.client.classfile.constant.FieldRefConstant;
import com.didactilab.jsjvm.client.classfile.constant.InterfaceMethodRefConstant;
import com.didactilab.jsjvm.client.classfile.constant.MethodHandleConstant;
import com.didactilab.jsjvm.client.classfile.constant.MethodRefConstant;
import com.didactilab.jsjvm.client.classfile.constant.MethodTypeConstant;
import com.didactilab.jsjvm.client.classfile.constant.StringConstant;
import com.didactilab.jsjvm.client.classfile.descriptor.ArrayDescType;
import com.didactilab.jsjvm.client.classfile.descriptor.DescType;
import com.didactilab.jsjvm.client.classfile.descriptor.DescriptorParser;
import com.didactilab.jsjvm.client.classfile.descriptor.ObjectDescType;
import com.didactilab.jsjvm.client.debug.SystemPrinter;
import com.didactilab.jsjvm.client.loader.JRESystemJavaClassLoader;
import com.didactilab.jsjvm.client.loader.JavaClassLoader;
import com.didactilab.jsjvm.client.util.Indentation;
import com.didactilab.jsjvm.client.util.Iterables;

public class Flow {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		JRESystemJavaClassLoader classLoader = new JRESystemJavaClassLoader(new File("build/runtime"));
		JavaClass javaClass = classLoader.loadClass("java/lang/Integer", true);
		JavaMethod method = javaClass.findMethod("numberOfTrailingZeros", "(I)I");
		//JavaMethod method = javaClass.findMethod("toString", "(II)Ljava/lang/String;");
		//JavaMethod method = javaClass.findMethod("valueOf", "(Ljava/lang/Object;)Ljava/lang/String;");
		//JavaMethod method = javaClass.findMethod("getBytes", "(Ljava/nio/charset/Charset;)[B");
		//JavaMethod method = javaClass.findMethod("copyValueOf", "([CII)Ljava/lang/String;");
		//JavaMethod method = javaClass.findMethod("toCharArray", "()[C");
		//JavaMethod method = javaClass.findMethod("getChars", "([CI)V");
		//JavaMethod method = javaClass.findMethod("split", "(Ljava/lang/String;)[Ljava/lang/String;");
		method.print(new SystemPrinter());
		
		Flow flow = new Flow(method);
		flow.run();
	}
	
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
		locals = new FlowVar[codeAttr.getMaxLocals()];
		System.out.println("init vars :");
		for (Parameter param : method.getParameters()) {
			locals[param.localIndex] = new FlowParameter(param);
			System.out.println("  var " + param.localIndex + " => " + locals[param.localIndex] + " from " + param);
		}
		if (!method.isStatic()) {
			locals[0] = new FlowThis(method.getJavaClass());
		}
	}
	
	private FlowVar getLocalSetter(int index, int addr, FlowObject value, Type instructionType) {
		if (locals[index] == null) {
			Type type;
			String name;
			if (varTable != null) {
				Variable var = varTable.getVariableAt(addr, index, 3);
				if (var == null) {
					throw new IllegalStateException("Local " + index + " at " + addr + " not found in var table");
				} else {
					DescriptorParser parser = new DescriptorParser(var.descriptor);
					type = toType(parser.parseField());
					name = var.name;
				}
			} else {
				type = value instanceof FlowValue ? ((FlowValue) value).getType() : getObjectType();
				if (type == null && instructionType != null) {
					type = instructionType;
				}
				name = "local" + index;
			}
			FlowLocal local = new FlowLocal(name, type);
			locals[index] = local;
			return new FlowDeclareVar(local);
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
		while (!leave && parser.hasMore()) {
			int op = parser.nextOp();
			System.out.println("  #stack " + Iterables.reversed(stack));
			System.out.println("  read " + OpCodeData.valueOf(op));
			switch (op) {
				case OpCode.ALOAD_0:
				case OpCode.ALOAD_1:
				case OpCode.ALOAD_2:
				case OpCode.ALOAD_3:
					stack.push(getLocal(op - OpCode.ALOAD_0));
					break;
				case OpCode.ILOAD:
				case OpCode.ILOAD_0:
				case OpCode.ILOAD_1:
				case OpCode.ILOAD_2:
				case OpCode.ILOAD_3:
					stack.push(getLocal(op == OpCode.ILOAD ? parser.nextLocalVariableIndex() : op - OpCode.ILOAD_0));
					break;
				case OpCode.LLOAD_0:
				case OpCode.LLOAD_1:
				case OpCode.LLOAD_2:
				case OpCode.LLOAD_3:
					stack.push(getLocal(op - OpCode.LLOAD_0));
					break;
				case OpCode.ASTORE_0:
				case OpCode.ASTORE_1:
				case OpCode.ASTORE_2:
				case OpCode.ASTORE_3:
					{
						FlowObject obj = stack.pop();
						FlowVar var = getLocalSetter(op - OpCode.ASTORE_0, parser.getLastOpPosition(), obj, getObjectType());
						stack.push(new FlowAssign(var, obj));
					}
					break;
				case OpCode.ISTORE:
				case OpCode.ISTORE_0:
				case OpCode.ISTORE_1:
				case OpCode.ISTORE_2:
				case OpCode.ISTORE_3:
					{
						FlowObject obj = stack.pop();
						FlowVar var = getLocalSetter(op == OpCode.ISTORE ? parser.nextLocalVariableIndex() : op - OpCode.ISTORE_0, 
								parser.getLastOpPosition(), obj, PrimitiveType.INT);
						stack.push(new FlowAssign(var, obj));
					}
					break;
				case OpCode.LSTORE_0:
				case OpCode.LSTORE_1:
				case OpCode.LSTORE_2:
				case OpCode.LSTORE_3:
					{
						FlowObject obj = stack.pop();
						FlowVar var = getLocalSetter(op - OpCode.LSTORE_0, parser.getLastOpPosition(), obj, PrimitiveType.LONG);
						stack.push(new FlowAssign(var, obj));
					}
					break;
				case OpCode.ACONST_NULL:
					stack.push(new FlowNull());
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
				case OpCode.LCONST_0:
				case OpCode.LCONST_1:
					stack.push(new FlowLongConst(op - OpCode.LCONST_0));
					break;
				case OpCode.CALOAD:
					{
						FlowObject ref = stack.pop();
						FlowObject index = stack.pop();
						stack.push(new FlowArrayGet(ref, index, PrimitiveType.CHAR));
					}
					break;
				case OpCode.CASTORE:
					{
						FlowObject ref = stack.pop();
						FlowObject index = stack.pop();
						FlowObject value = stack.pop();
						stack.push(new FlowAssign(new FlowArrayGet(ref, index, PrimitiveType.CHAR), value));
					}
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
							stack.push(new FlowStringConst(((StringConstant) constObj).value(), getStringType()));
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
				case OpCode.BIPUSH:
					stack.push(new FlowIntConst(parser.nextByteValue()));
					break;
				case OpCode.POP:
					stack.pop();
					break;
				case OpCode.LADD:
					stack.push(new FlowOperation(stack.pop(), "+", stack.pop()));
					break;
				case OpCode.ISUB: 
					{
						FlowObject left = stack.pop();
						FlowObject right = stack.pop();
						stack.push(new FlowOperation(left, "-", right));
					}
					break;
				case OpCode.IDIV:
					{
						FlowObject left = stack.pop();
						FlowObject right = stack.pop();
						stack.push(new FlowOperation(left, "/", right));
					}
					break;
				case OpCode.IREM:
					{
						FlowObject left = stack.pop();
						FlowObject right = stack.pop();
						stack.push(new FlowOperation(left, "%", right));
					}
					break;
				case OpCode.IINC:
					{
						int index = parser.nextLocalVariableIndex();
						int iconst = parser.nextByteValue();
						FlowVar var = getLocal(index);
						stack.push(new FlowInc(var, iconst));
					}
					break;
				case OpCode.INEG:
					stack.push(new FlowNegation(stack.pop()));
					break;
				case OpCode.ISHL:
					{
						FlowObject left = stack.pop();
						FlowObject right = stack.pop();
						stack.push(new FlowOperation(left, "<<", right));
					}
					break;
				case OpCode.IUSHR:
				{
					FlowObject left = stack.pop();
					FlowObject right = stack.pop();
					stack.push(new FlowOperation(left, ">>>", right));
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
				case OpCode.INVOKEINTERFACE:
				{
					JavaMethod anotherMethod = findInterfaceMethod(parser.nextConstantPoolIndex());
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
				case OpCode.IFNULL:
				case OpCode.IF_ACMPNE:
				case OpCode.IF_ACMPEQ:
				case OpCode.IFEQ:
				case OpCode.IFNE:
				case OpCode.IFLT:
				case OpCode.IFGE:
				case OpCode.IFGT:
				case OpCode.IFLE:
				case OpCode.IF_ICMPEQ:
				case OpCode.IF_ICMPNE:
				case OpCode.IF_ICMPLT:
				case OpCode.IF_ICMPGE:
				case OpCode.IF_ICMPGT:
				case OpCode.IF_ICMPLE:
					{
						int offset = parser.nextBranchOffset();
						int next = parser.getPosition();
						System.out.println("  --> true");
						FlowBlock trueBlock = decompile(parser.getLastOpPosition() + offset);
						System.out.println("  --> false");
						FlowBlock falseBlock = decompile(next);
						
						FlowOperator operator = toOperator(op);
						FlowObject left = stack.pop();
						FlowObject right;
						if (op == OpCode.IFNONNULL || op == OpCode.IFNULL) {
							right = new FlowNull();
						} else if (op >= OpCode.IFEQ && op <= OpCode.IFLE) {
							if (left instanceof FlowLongCond) {
								FlowLongCond c = (FlowLongCond) left;
								left = c.left;
								right = c.right;
							} else {
								right = new FlowIntConst(0);
							}
						} else {
							right = stack.pop();
						}
						
						if (right instanceof FlowConst) {
							FlowObject nextRight = left;
							left = right;
							right = nextRight;
							operator = operator.inverse();
							FlowBlock nextFalseBlock = trueBlock;
							trueBlock = falseBlock;
							falseBlock = nextFalseBlock;
						}
						
						// inverse
						FlowObject nextRight = left;
						left = right;
						right = nextRight;
						operator = operator.inverse();
						FlowBlock nextFalseBlock = trueBlock;
						trueBlock = falseBlock;
						falseBlock = nextFalseBlock;
						
						//stack.push(new FlowIf(new FlowCond(left, operator, right), trueBlock, falseBlock));
						FlowCond cond = new FlowCond(left, operator, right);
						if (trueBlock.isLastInstructionFinal()) {
							stack.push(new FlowIf(cond, trueBlock));
							falseBlock.copyTo(stack);
							//stack.push(falseBlock);
						} else {
							stack.push(new FlowIf(cond, trueBlock, falseBlock));
						}
						leave = true;
					}
					break;
				case OpCode.LCMP:
					stack.push(new FlowLongCond(stack.pop(), stack.pop()));
					break;
				case OpCode.GOTO:
					parser.setPosition(parser.getLastOpPosition() + parser.nextBranchOffset());
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
				case OpCode.GETSTATIC:
					{
						JavaField field = findField(parser.nextConstantPoolIndex());
						stack.push(new FlowField(field));
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
						CodeArrayType atype = parser.nextArrayType();
						stack.push(new FlowNewArray(atype, stack.pop()));
					}
					break;
				case OpCode.ARRAYLENGTH:
					stack.push(new FlowArrayLength(stack.pop()));
					break;
				case OpCode.DUP:
					stack.push(stack.peek());
					break;
				case OpCode.NOP:
					break;
				default:
					throw new IllegalArgumentException("Not complete " + OpCodeData.valueOf(op));
			}
		}
		
		System.out.println("  #stack " + Iterables.reversed(stack));
		System.out.println("end block");
		
		return current;
	}
	
	public void run() throws ClassNotFoundException {
		FlowBlock methodBlock = decompile(0);
		
		System.out.println("\nSource ");
		System.out.println(Indentation.indent(methodBlock.toSource()));
	}
	
	private JavaMethod findMethod(int constantPoolIndex) throws ClassNotFoundException {
		MethodRefConstant methodRef = constants.get(constantPoolIndex, MethodRefConstant.class);
		JavaClass jClass = classLoader.loadClass(methodRef.getClassName().getName());
		return jClass.findMethod(methodRef.getNameAndType().getName(), 
				methodRef.getNameAndType().getDescriptor());
	}
	
	private JavaMethod findInterfaceMethod(int constantPoolIndex) throws ClassNotFoundException {
		InterfaceMethodRefConstant methodRef = constants.get(constantPoolIndex, InterfaceMethodRefConstant.class);
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
	
	private Type getStringType() {
		return classLoader.findLoadedClass("java/lang/String");
	}
	
	private Type getObjectType() {
		return classLoader.findLoadedClass("java/lang/Object");
	}
	
	@Deprecated
	private Type toType(DescType descType) {
		try {
			if (descType instanceof PrimitiveType) {
				return (PrimitiveType) descType;
			} else if (descType instanceof ArrayDescType) {
				ArrayDescType adt = (ArrayDescType) descType;
				DescType dtype = adt.type;
				Type componentType;
				if (dtype instanceof PrimitiveType) {
					componentType = (PrimitiveType) dtype;
				} else if (dtype instanceof ObjectDescType) {
					componentType = classLoader.loadClass(((ObjectDescType) dtype).name);
				} else {
					throw new IllegalStateException("descriptor parser fails with component type : " + dtype);
				}
				return new ArrayType(adt.dimension, componentType, adt.getDescriptor());
			} else if (descType instanceof ObjectDescType) {
				return classLoader.loadClass(((ObjectDescType) descType).name);
			} else if (descType == DescType.VOID) {
				return null;
			} else {
				throw new IllegalStateException("descriptor parser fails : " + descType);
			}
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Cannot be possible");
		}
	}
	
	private FlowOperator toOperator(int op) {
		switch (op) {
		case OpCode.IFNONNULL: 
		case OpCode.IFNE: 
		case OpCode.IF_ICMPNE:
		case OpCode.IF_ACMPNE:	return FlowOperator.NE;
		
		case OpCode.IFNULL:	
		case OpCode.IF_ACMPEQ: 
		case OpCode.IFEQ: 
		case OpCode.IF_ICMPEQ: return FlowOperator.EQ;
			
		case OpCode.IF_ICMPLT:
		case OpCode.IFLT: return FlowOperator.LT;
		
		case OpCode.IF_ICMPGE:
		case OpCode.IFGE: return FlowOperator.GE;
		
		case OpCode.IF_ICMPGT:
		case OpCode.IFGT: return FlowOperator.GT;
		
		case OpCode.IF_ICMPLE:
		case OpCode.IFLE: return FlowOperator.LE;
		
		default:
			throw new IllegalArgumentException();
		}
	}
	
}
