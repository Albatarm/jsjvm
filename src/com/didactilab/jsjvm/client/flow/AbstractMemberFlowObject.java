package com.didactilab.jsjvm.client.flow;

public abstract class AbstractMemberFlowObject extends AbstractFlowObject {

	protected final Namer namer;

	public AbstractMemberFlowObject(Namer namer) {
		this.namer = namer;
	}

}
