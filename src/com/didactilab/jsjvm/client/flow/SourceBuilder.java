package com.didactilab.jsjvm.client.flow;

import java.util.Collection;

public class SourceBuilder {

	private final StringBuilder sb = new StringBuilder();
	
	public SourceBuilder append(String s) {
		sb.append(s);
		return this;
	}
	
	public SourceBuilder append(FlowObject obj) {
		sb.append(obj.toSource());
		return this;
	}
	
	public <T extends FlowObject> SourceBuilder appendJoin(String sep, Collection<T> c) {
		boolean first = true;
		for (T t : c) {
			if (first) {
				first = false; 
			} else {
				sb.append(sep);
			}
			sb.append(t.toSource());
		}
		return this;
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}
	
	public String toSource() {
		return sb.toString();
	}
	
}
