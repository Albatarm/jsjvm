package com.didactilab.jsjvm.client.flow;

import java.util.Deque;
import java.util.LinkedList;

public class Instructions {
	
	private Deque<FlowObject> stack = new LinkedList<FlowObject>();

	public Instructions() {
	}
	
	public void push(FlowObject obj) {
		stack.push(obj);
	}
	
	@Deprecated
	public FlowObject pop() {
		return stack.pop();
	}
	
	public FlowObject peek() {
		return stack.peek();
	}

}
