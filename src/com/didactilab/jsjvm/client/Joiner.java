package com.didactilab.jsjvm.client;

import java.util.Arrays;
import java.util.Collection;

public final class Joiner {

	public static String join(Collection<?> c) {
		return join(", ", c);
	}
	
	public static String join(String sep, Collection<?> c) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Object object : c) {
			if (first) {
				first = false;
			} else {
				sb.append(sep);
			}
			sb.append(object);
		}
		return sb.toString();
	}
	
	@SafeVarargs
	public static <T> String join(T... a) {
		return join(Arrays.asList(a));
	}
	
	@SafeVarargs
	public static <T> String join(String sep, T... a) {
		return join(sep, Arrays.asList(a));
	}
	
	private Joiner() {
	}
	
}
