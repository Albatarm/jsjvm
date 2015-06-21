package com.didactilab.jsjvm.client.flow;

import java.util.Deque;
import java.util.LinkedList;


public class FlowBlock implements FlowObject {

	public final Deque<FlowObject> stack = new LinkedList<FlowObject>();
	
	private boolean finalBlock = false;
	
	public FlowBlock() {
	}
	
	public boolean isFinal() {
		return finalBlock;
	}
	
	public void setFinal(boolean value) {
		finalBlock = value;
	}
	
	@Override
	public String toSource() {
		SourceBuilder sb = new SourceBuilder();
		sb.append("{\n");
		for (FlowObject obj : stack) {
			sb.append(obj).append(";\n");
		}
		sb.append("}\n");
		return sb.toSource();
	}

}
