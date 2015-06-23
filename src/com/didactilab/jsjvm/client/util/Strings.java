package com.didactilab.jsjvm.client.util;

public final class Strings {

	public static String rightPad(String s, int num, char padChar) {
		if (s.length() >= num) {
			return s;
		}
		return s + stringOfChar(padChar, num - s.length());
	}
	
	public static String stringOfChar(char ch, int count) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<count; i++) {
			sb.append(ch);
		}
		return sb.toString();
	}
	
	private Strings() {
	}

}
