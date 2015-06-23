package com.didactilab.jsjvm.client.util;

import java.util.ArrayList;
import java.util.List;

public final class Indentation {

	public static String indent(String text) {
		StringBuilder sb = new StringBuilder();
		int indent = 0;
		ArrayList<String> indentTable = new ArrayList<String>();
		boolean needIndent = false;
		for (int i=0,len = text.length(); i<len; i++) {
			char ch = text.charAt(i);
			if (ch == ';') {
				sb.append(";\n");
				needIndent = true;
			} else if (ch == '{') {
				if (needIndent) {
					sb.append(getIndent(indentTable, indent));
				}
				indent++;
				needIndent = true;
				sb.append("{\n");
			} else if (ch == '}') {
				indent--;
				if (needIndent) {
					sb.append(getIndent(indentTable, indent));
				}
				sb.append("}\n");
				needIndent = true;
			} else {
				if (needIndent) {
					sb.append(getIndent(indentTable, indent));
					needIndent = false;
				}
				sb.append(ch);
			}
		}
		return sb.toString();
	}
	
	private static String getIndent(List<String> table, int indent) {
		if (indent < table.size()) {
			String str = table.get(indent);
			if (str == null) {
				str = generateIndent(indent);
				table.set(indent, str);
			}
			return str;
		} else {
			int need = indent - table.size() + 1;
			for (int i=0; i<need; i++) {
				table.add(null);
			}
			String str = generateIndent(indent);
			table.set(indent, str);
			return str;
		}
	}
	
	private static String generateIndent(int indent) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<indent; i++) {
			sb.append("  ");
		}
		return sb.toString();
	}
	
}
