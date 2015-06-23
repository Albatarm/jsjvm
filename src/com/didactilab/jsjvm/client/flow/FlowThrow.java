package com.didactilab.jsjvm.client.flow;

public class FlowThrow extends AbstractFlowObject implements FlowFinalInstruction {

	private final FlowObject exception;

	public FlowThrow(FlowObject exception) {
		this.exception = exception;
	}
	
	@Override
	public String toSource() {
		return "throw " + exception.toSource();
	}

}
