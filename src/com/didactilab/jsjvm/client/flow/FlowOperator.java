package com.didactilab.jsjvm.client.flow;

public enum FlowOperator implements FlowObject {
	EQ("==", "eq"),
	NE("!=", "ne"),
	LT("<", "lt"),
	GT(">", "gt"),
	LE("<=", "le"),
	GE(">=", "ge");
	
	private final String source;
	private final String name;

	private FlowOperator(String source, String name) {
		this.source = source;
		this.name = name;
		
	}

	@Override
	public String toSource() {
		return source;
	}
	
	@Override
	public String toString() {
		return "FlowOperator|" + name;
	}
	
	public FlowOperator inverse() {
		switch (this) {
		case EQ: return NE;
		case GE: return LT;
		case GT: return LE;
		case LE: return GT;
		case LT: return GE;
		case NE: return EQ;
		default: throw new IllegalStateException();
		}
	}

}
