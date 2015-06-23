package com.didactilab.jsjvm.client.flow;

public class FlowIf extends AbstractFlowObject {

	private final FlowObject cond;
	private final FlowBlock trueBlock;
	private final FlowBlock falseBlock;

	public FlowIf(FlowObject cond, FlowBlock trueBlock) {
		this(cond, trueBlock, null);
	}
	
	public FlowIf(FlowObject cond, FlowBlock trueBlock, FlowBlock falseBlock) {
		this.cond = cond;
		this.trueBlock = trueBlock;
		this.falseBlock = falseBlock;
	}
	
	@Override
	public String toSource() {
		return "if (" + cond.toSource() + ") " + trueBlock.toSource() + (falseBlock != null ? " else " + falseBlock.toSource() : "");
	}
	
	@Override
	public String toString() {
		return "FlowIf|if (" + cond.toSource() + ") {...} else {...}";
	}

}
