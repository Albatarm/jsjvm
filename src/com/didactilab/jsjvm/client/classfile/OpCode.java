package com.didactilab.jsjvm.client.classfile;

public interface OpCode {
	
	// Constants
	int NOP = 0;
	int ACONST_NULL = 1;
	int ICONST_M1 = 2;
	int ICONST_0 = 3;
	int ICONST_1 = 4;
	int ICONST_2 = 5;
	int ICONST_3 = 6;
	int ICONST_4 = 7;
	int ICONST_5 = 8;
	int LCONST_0 = 9;
	int LCONST_1 = 10;
	int FCONST_0 = 11;
	int FCONST_1 = 12;
	int FCONST_2 = 13;
	int DCONST_0 = 14;
	int DCONST_1 = 15;
	int BIPUSH = 16;
	int SIPUSH = 17;
	int LDC = 18;
	int LDC_W = 19;
	int LDC2_W = 20;
	
	// Loads
	int ILOAD = 21;
	int LLOAD = 22;
	int FLOAD = 23;
	int DLOAD = 24;
	int ALOAD = 25;
	int ILOAD_0 = 26;
	int ILOAD_1 = 27;
	int ILOAD_2 = 28;
	int ILOAD_3 = 29;
	int LLOAD_0 = 30;
	int LLOAD_1 = 31;
	int LLOAD_2 = 32;
	int LLOAD_3 = 33;
	int FLOAD_0 = 34;
	int FLOAD_1 = 35;
	int FLOAD_2 = 36;
	int FLOAD_3 = 37;
	int DLOAD_0 = 38;
	int DLOAD_1 = 39;
	int DLOAD_2 = 40;
	int DLOAD_3 = 41;
	int ALOAD_0 = 42;
	int ALOAD_1 = 43;
	int ALOAD_2 = 44;
	int ALOAD_3 = 45;
	int IALOAD = 46;
	int LALOAD = 47;
	int FALOAD = 48;
	int DALOAD = 49;
	int AALOAD = 50;
	int BALOAD = 51;
	int CALOAD = 52;
	int SALOAD = 53;
	
	// Stores
	int ISTORE = 54;
	int LSTORE = 55;
	int FSTORE = 56;
	int DSTORE = 57;
	int ASTORE = 58;
	int ISTORE_0 = 59;
	int ISTORE_1 = 60;
	int ISTORE_2 = 61;
	int ISTORE_3 = 62;
	int LSTORE_0 = 63;
	int LSTORE_1 = 64;
	int LSTORE_2 = 65;
	int LSTORE_3 = 66;
	int FSTORE_0 = 67;
	int FSTORE_1 = 68;
	int FSTORE_2 = 69;
	int FSTORE_3 = 70;
	int DSTORE_0 = 71;
	int DSTORE_1 = 72;
	int DSTORE_2 = 73;
	int DSTORE_3 = 74;
	int ASTORE_0 = 75;
	int ASTORE_1 = 76;
	int ASTORE_2 = 77;
	int ASTORE_3 = 78;
	int IASTORE = 79;
	int LASTORE = 80;
	int FASTORE = 81;
	int DASTORE = 82;
	int AASTORE = 83;
	int BASTORE = 84;
	int CASTORE = 85;
	int SASTORE = 86;
	
}
