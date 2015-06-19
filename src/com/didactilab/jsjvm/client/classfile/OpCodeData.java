package com.didactilab.jsjvm.client.classfile;

public enum OpCodeData {
	
	// Constants
	NOP(OpCode.NOP, "nop"),
	ACONST_NULL(OpCode.ACONST_NULL, "aconst_null"),
	ICONST_M1(OpCode.ICONST_M1, "iconst_m1"),
	ICONST_0(OpCode.ICONST_0, "iconst_0"),
	ICONST_1(OpCode.ICONST_1, "iconst_1"),
	ICONST_2(OpCode.ICONST_2, "iconst_2"),
	ICONST_3(OpCode.ICONST_3, "iconst_3"),
	ICONST_4(OpCode.ICONST_4, "iconst_4"),
	ICONST_5(OpCode.ICONST_5, "iconst_5"),
	LCONST_0(OpCode.LCONST_0, "lconst_0"),
	LCONST_1(OpCode.LCONST_1, "lconst_1"),
	FCONST_0(OpCode.FCONST_0, "fconst_0"),
	FCONST_1(OpCode.FCONST_1, "fconst_1"),
	FCONST_2(OpCode.FCONST_2, "fconst_2"),
	DCONST_0(OpCode.DCONST_0, "dconst_0"),
	DCONST_1(OpCode.DCONST_1, "dconst_1"),
	BIPUSH(OpCode.BIPUSH, "bipush", Param.BYTE_VALUE),
	SIPUSH(OpCode.SIPUSH, "sipush", Param.SHORT_VALUE),
	LDC(OpCode.LDC, "ldc", Param.CONSTANT_POOL_INDEX_BYTE),
	LDC_W(OpCode.LDC_W, "ldc_w", Param.CONSTANT_POOL_INDEX),
	LDC2_W(OpCode.LDC2_W, "ldc2_w", Param.CONSTANT_POOL_INDEX),
	
	// Loads
	ILOAD(OpCode.ILOAD, "iload", Param.LOCAL_VARIABLE_INDEX),
	LLOAD(OpCode.LLOAD, "lload", Param.LOCAL_VARIABLE_INDEX),
	FLOAD(OpCode.FLOAD, "fload", Param.LOCAL_VARIABLE_INDEX),
	DLOAD(OpCode.DLOAD, "dload", Param.LOCAL_VARIABLE_INDEX),
	ALOAD(OpCode.ALOAD, "aload", Param.LOCAL_VARIABLE_INDEX),
	ILOAD_0(OpCode.ILOAD_0, "iload_0"),
	ILOAD_1(OpCode.ILOAD_1, "iload_1"),
	ILOAD_2(OpCode.ILOAD_2, "iload_2"),
	ILOAD_3(OpCode.ILOAD_3, "iload_3"),
	LLOAD_0(OpCode.LLOAD_0, "lload_0"),
	LLOAD_1(OpCode.LLOAD_1, "lload_1"),
	LLOAD_2(OpCode.LLOAD_2, "lload_2"),
	LLOAD_3(OpCode.LLOAD_3, "lload_3"),
	FLOAD_0(OpCode.FLOAD_0, "fload_0"),
	FLOAD_1(OpCode.FLOAD_1, "fload_1"),
	FLOAD_2(OpCode.FLOAD_2, "fload_2"),
	FLOAD_3(OpCode.FLOAD_3, "fload_3"),
	DLOAD_0(OpCode.DLOAD_0, "dload_0"),
	DLOAD_1(OpCode.DLOAD_1, "dload_1"),
	DLOAD_2(OpCode.DLOAD_2, "dload_2"),
	DLOAD_3(OpCode.DLOAD_3, "dload_3"),
	ALOAD_0(OpCode.ALOAD_0, "aload_0"),
	ALOAD_1(OpCode.ALOAD_1, "aload_1"),
	ALOAD_2(OpCode.ALOAD_2, "aload_2"),
	ALOAD_3(OpCode.ALOAD_3, "aload_3"),
	IALOAD(OpCode.IALOAD, "iaload"),
	LALOAD(OpCode.LALOAD, "laload"),
	FALOAD(OpCode.FALOAD, "faload"),
	DALOAD(OpCode.DALOAD, "daload"),
	AALOAD(OpCode.AALOAD, "aaload"),
	BALOAD(OpCode.BALOAD, "baload"),
	CALOAD(OpCode.CALOAD, "caload"),
	SALOAD(OpCode.SALOAD, "saload"),
	
	// Stores
	ISTORE(OpCode.ISTORE, "istore", Param.LOCAL_VARIABLE_INDEX),
	LSTORE(OpCode.LSTORE, "lstore", Param.LOCAL_VARIABLE_INDEX),
	FSTORE(OpCode.FSTORE, "fstore", Param.LOCAL_VARIABLE_INDEX),
	DSTORE(OpCode.DSTORE, "dstore", Param.LOCAL_VARIABLE_INDEX),
	ASTORE(OpCode.ASTORE, "store", Param.LOCAL_VARIABLE_INDEX),
	ISTORE_0(OpCode.ISTORE_0, "istore_0"),
	ISTORE_1(OpCode.ISTORE_1, "istore_1"),
	ISTORE_2(OpCode.ISTORE_2, "istore_2"),
	ISTORE_3(OpCode.ISTORE_3, "istore_3"),
	LSTORE_0(OpCode.LSTORE_0, "lstore_0"),
	LSTORE_1(OpCode.LSTORE_1, "lstore_1"),
	LSTORE_2(OpCode.LSTORE_2, "lstore_2"),
	LSTORE_3(OpCode.LSTORE_3, "lstore_3"),
	FSTORE_0(OpCode.FSTORE_0, "fstore_0"),
	FSTORE_1(OpCode.FSTORE_1, "fstore_1"),
	FSTORE_2(OpCode.FSTORE_2, "fstore_2"),
	FSTORE_3(OpCode.FSTORE_3, "fstore_3"),
	DSTORE_0(OpCode.DSTORE_0, "dstore_0"),
	DSTORE_1(OpCode.DSTORE_1, "dstore_1"),
	DSTORE_2(OpCode.DSTORE_2, "dstore_2"),
	DSTORE_3(OpCode.DSTORE_3, "dstore_3"),
	ASTORE_0(OpCode.ASTORE_0, "astore_0"),
	ASTORE_1(OpCode.ASTORE_1, "astore_1"),
	ASTORE_2(OpCode.ASTORE_2, "astore_2"),
	ASTORE_3(OpCode.ASTORE_3, "astore_3"),
	IASTORE(OpCode.IASTORE, "iastore"),
	LASTORE(OpCode.LASTORE, "lastore"),
	FASTORE(OpCode.FASTORE, "fastore"),
	DASTORE(OpCode.DASTORE, "dastore"),
	AASTORE(OpCode.AASTORE, "aastore"),
	BASTORE(OpCode.BASTORE, "bastore"),
	CASTORE(OpCode.CASTORE, "castore"),
	SASTORE(OpCode.SASTORE, "sastore"),
	
	// Stack
	POP(OpCode.POP, "pop"),
	POP2(OpCode.POP2, "pop2"),
	DUP(OpCode.DUP, "dup"),
	DUP_X1(OpCode.DUP_X1, "dup_x1"),
	DUP_X2(OpCode.DUP_X2, "dup_x2"),
	DUP2(OpCode.DUP2, "dup2"),
	DUP2_X1(OpCode.DUP2_X1, "dup2_x1"),
	DUP2_X2(OpCode.DUP2_X2, "dup2_x2"),
	SWAP(OpCode.SWAP, "swap"),
	
	// Math
	IADD(OpCode.IADD, "iadd"),
	LADD(OpCode.LADD, "ladd"),
	FADD(OpCode.FADD, "fadd"),
	DADD(OpCode.DADD, "dadd"),
	ISUB(OpCode.ISUB, "isub"),
	LSUB(OpCode.LSUB, "lsub"),
	FSUB(OpCode.FSUB, "fsub"),
	DSUB(OpCode.DSUB, "dsub"),
	IMUL(OpCode.IMUL, "imul"),
	LMUL(OpCode.LMUL, "lmul"),
	FMUL(OpCode.FMUL, "fmul"),
	DMUL(OpCode.DMUL, "dmul"),
	IDIV(OpCode.IDIV, "idiv"),
	LDIV(OpCode.LDIV, "ldiv"),
	FDIV(OpCode.FDIV, "fdiv"),
	DDIV(OpCode.DDIV, "ddiv"),
	IREM(OpCode.IREM, "irem"),
	LREM(OpCode.LREM, "lrem"),
	FREM(OpCode.FREM, "frem"),
	DREM(OpCode.DREM, "drem"),
	INEG(OpCode.INEG, "ineg"),
	LNEG(OpCode.LNEG, "lneg"),
	FNEG(OpCode.FNEG, "fneg"),
	DNEG(OpCode.DNEG, "dneg"),
	ISHL(OpCode.ISHL, "ishl"),
	LSHL(OpCode.LSHL, "lshl"),
	ISHR(OpCode.ISHR, "ishr"),
	LSHR(OpCode.LSHR, "lshr"),
	IUSHR(OpCode.IUSHR, "iushr"),
	LUSHR(OpCode.LUSHR, "lushr"),
	IAND(OpCode.IAND, "iand"),
	LAND(OpCode.LAND, "land"),
	IOR(OpCode.IOR, "ior"),
	LOR(OpCode.LOR, "lor"),
	IXOR(OpCode.IXOR, "ixor"),
	LXOR(OpCode.LXOR, "lxor"),
	IINC(OpCode.IINC, "iinc", Param.LOCAL_VARIABLE_INDEX, Param.BYTE_VALUE),
	  
	// Conversions
	I2L(OpCode.I2L, "i2l"),
	I2F(OpCode.I2F, "i2f"),
	I2D(OpCode.I2D, "i2d"),
	L2I(OpCode.L2I, "l2i"),
	L2F(OpCode.L2F, "l2f"),
	L2D(OpCode.L2D, "l2d"),
	F2I(OpCode.F2I, "f2i"),
	F2L(OpCode.F2L, "f2l"),
	F2D(OpCode.F2D, "f2d"),
	D2I(OpCode.D2I, "d2i"),
	D2L(OpCode.D2L, "d2l"),
	D2F(OpCode.D2F, "d2f"),
	I2B(OpCode.I2B, "i2b"),
	I2C(OpCode.I2C, "i2c"),
	I2S(OpCode.I2S, "i2s"),
	
	// Comparaisons
	LCMP(OpCode.LCMP, "lcmp"),
	FCMPL(OpCode.FCMPL, "fcmpl"),
	FCMPG(OpCode.FCMPG, "fcmpg"),
	DCMPL(OpCode.DCMPL, "dcmpl"),
	DCMPG(OpCode.DCMPG, "dcmpg"),
	IFEG(OpCode.IFEG, "ifeg", Param.BRANCH_OFFSET),
	IFNE(OpCode.IFNE, "ifne", Param.BRANCH_OFFSET),
	IFLT(OpCode.IFLT, "iflt", Param.BRANCH_OFFSET),
	IFGE(OpCode.IFGE, "ifge", Param.BRANCH_OFFSET),
	IFGT(OpCode.IFGT, "ifgt", Param.BRANCH_OFFSET),
	IFLE(OpCode.IFLE, "ifle", Param.BRANCH_OFFSET),
	IF_ICMPEQ(OpCode.IF_ICMPEQ, "if_icmpeq", Param.BRANCH_OFFSET),
	IF_ICMPNE(OpCode.IF_ICMPNE, "if_icmpne", Param.BRANCH_OFFSET),
	IF_ICMPLT(OpCode.IF_ICMPLT, "if_icmplt", Param.BRANCH_OFFSET),
	IF_ICMPGE(OpCode.IF_ICMPGE, "if_icmpge", Param.BRANCH_OFFSET),
	IF_ICMPGT(OpCode.IF_ICMPGT, "if_icmpgt", Param.BRANCH_OFFSET),
	IF_ICMPLE(OpCode.IF_ICMPLE, "if_icmple", Param.BRANCH_OFFSET),
	IF_ACMPEG(OpCode.IF_ACMPEG, "if_acmpeg", Param.BRANCH_OFFSET),
	IF_ACMPNE(OpCode.IF_ACMPNE, "if_acmpne", Param.BRANCH_OFFSET),
	
	// Control
	GOTO(OpCode.GOTO, "goto", Param.BRANCH_OFFSET),
	JSR(OpCode.JSR, "jsr", Param.BRANCH_OFFSET),
	RET(OpCode.RET, "ret", Param.LOCAL_VARIABLE_INDEX_AS_RETURN_ADDRESS),
	TABLESWITCH(OpCode.TABLESWITCH, "tableswitch", Param.TABLESWITCH),
	LOOKUPSWITCH(OpCode.LOOKUPSWITCH, "lookupswitch", Param.LOOKUPSWITCH),
	IRETURN(OpCode.IRETURN, "ireturn"),
	LRETURN(OpCode.LRETURN, "lreturn"),
	FRETURN(OpCode.FRETURN, "freturn"),
	DRETURN(OpCode.DRETURN, "dreturn"),
	ARETURN(OpCode.ARETURN, "areturn"),
	RETURN(OpCode.RETURN, "return"),
	
	// References
	GETSTATIC(OpCode.GETSTATIC, "getstatic", Param.CONSTANT_POOL_INDEX),
	PUTSTATIC(OpCode.PUTSTATIC, "putstatic", Param.CONSTANT_POOL_INDEX),
	GETFIELD(OpCode.GETFIELD, "getfield", Param.CONSTANT_POOL_INDEX),
	PUTFIELD(OpCode.PUTFIELD, "putfield", Param.CONSTANT_POOL_INDEX),
	INVOKEVIRTUAL(OpCode.INVOKEVIRTUAL, "invokevirtual", Param.CONSTANT_POOL_INDEX),
	INVOKESPECIAL(OpCode.INVOKESPECIAL, "invokespecial", Param.CONSTANT_POOL_INDEX),
	INVOKESTATIC(OpCode.INVOKESTATIC, "invokestatic", Param.CONSTANT_POOL_INDEX),
	INVOKEINTERFACE(OpCode.INVOKEINTERFACE, "invokeinterface", Param.CONSTANT_POOL_INDEX, Param.COUNT, Param.NULL),
	INVOKEDYNAMIC(OpCode.INVOKEDYNAMIC, "invokedynamic", Param.CONSTANT_POOL_INDEX, Param.NULL, Param.NULL),
	NEW(OpCode.NEW, "new", Param.CONSTANT_POOL_INDEX),
	NEWARRAY(OpCode.NEWARRAY, "newarray", Param.ATYPE),
	ANEWARRAY(OpCode.ANEWARRAY, "anewarray", Param.CONSTANT_POOL_INDEX),
	ARRAYLENGTH(OpCode.ARRAYLENGTH, "arraylength"),
	ATHROW(OpCode.ATHROW, "athrow"),
	CHECKCAST(OpCode.CHECKCAST, "checkcast", Param.CONSTANT_POOL_INDEX),
	INSTANCEOF(OpCode.INSTANCEOF, "instanceof", Param.CONSTANT_POOL_INDEX),
	MONITORENTER(OpCode.MONITORENTER, "monitorenter"),
	MONITOREXIT(OpCode.MONITOREXIT, "monitorexit"),
	
	// Extended
	WIDE(OpCode.WIDE, "wide", Param.WIDE),
	MULTINEWARRAY(OpCode.MULTINEWARRAY, "multinewarray", Param.CONSTANT_POOL_INDEX, Param.DIMENSIONS),
	IFNULL(OpCode.IFNULL, "ifnull", Param.BRANCH_OFFSET),
	IFNONNULL(OpCode.IFNONNULL, "ifnonnull", Param.BRANCH_OFFSET),
	GOTO_W(OpCode.GOTO_W, "goto_w", Param.BRANCH_OFFSET_W),
	JSR_W(OpCode.JSR_W, "jsr_w", Param.BRANCH_OFFSET_W),
	
	// Reserved
	BREAKPOINT(OpCode.BREAKPOINT, "breakpoint"),
	IMPDEP1(OpCode.IMPDEP1, "impdep1"),
	IMPDEP2(OpCode.IMPDEP2, "impdep2");

	public enum Param {
		LOCAL_VARIABLE_INDEX(1), // unsigned byte
		LOCAL_VARIABLE_INDEX_AS_RETURN_ADDRESS(1),	// unsigned byte
		CONSTANT_POOL_INDEX(2), // unsigned short
		CONSTANT_POOL_INDEX_BYTE(1), // unsigned byte
		BYTE_VALUE(1), // signed byte
		SHORT_VALUE(2),	// signed short
		BRANCH_OFFSET(2), 	// signed short
		BRANCH_OFFSET_W(4), 	// signed int
		NULL(1), // 0
		COUNT(1),  // unsigned byte
		LOOKUPSWITCH(-1),	// variable
		TABLESWITCH(-1),	// variable
		DIMENSIONS(1), // unsigned byte
		ATYPE(1),		// enum
		WIDE(-1);		// variable
		
		public final int size;
		
		private Param(int size) {
			this.size = size;
		}
		
		public boolean isVariableSize() {
			return size == -1;
		}
	}
	
	private static OpCodeData[] OPCODES;
	
	public final int code;
	public final String name;
	public final Param[] params;
	
	private OpCodeData(int code, String name, Param... params) {
		this.code = code;
		this.name = name;
		this.params = params;
	}
	
	public int getParamSize() {
		int size = 0;
		for (Param param : params) {
			if (param.size == -1) {
				return -1;
			}
			size += param.size;
		}
		return size;
	}
	
	public static OpCodeData valueOf(int code) {
		if (OPCODES == null) {
			OPCODES = new OpCodeData[OpCode._MAX+1];
			for (OpCodeData opCode : values()) {
				OPCODES[opCode.code] = opCode;
			}
		}
		return OPCODES[code];
	}
	
}
