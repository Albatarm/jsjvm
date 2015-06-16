package com.didactilab.jsjvm.client.runtime.string;

import java.util.HashMap;

public class StringHashTable {

	private final HashMap<String, String> table = new HashMap<>();
	
	public String get(String s) {
		String hashed = table.get(s);
		if (hashed == null) {
			table.put(s, s);
			hashed = s;
		}
		return hashed;
	}
	
	
}
