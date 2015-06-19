package com.didactilab.jsjvm.client.classfile;

public enum JavaVersion {
	J2SE_8	(0x34, "J2SE 8"),
	J2SE_7	(0x33, "J2SE 7"),
	J2SE_6_0(0x32, "J2SE 6.0"),
	J2SE_5_0(0x31, "J2SE 5.0"),
	JDK_1_4	(0x30, "JDK 1.4"),
	JDK_1_3	(0x2F, "JDK 1.3"),
	JDK_1_2	(0x2E, "JDK 1.2"),
	JDK_1_1	(0x2D, "JDK 1.1"),
	UNKNOWN	(0x00, "UNKNOWN");
	
	private final int majorVersion;
	private final String name;
	
	private JavaVersion(int majorVersion, String name) {
		this.majorVersion = majorVersion;
		this.name = name;
	}
	
	public int getMajorVersion() {
		return majorVersion;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public static JavaVersion valueOf(int version) {
		for (JavaVersion v : values()) {
			if (v.majorVersion == version) {
				return v;
			}
		}
		return UNKNOWN;
	}
	
}
