package com.didactilab.jsjvm.client.flow.command;

public class AbstractHasNextCommand2 extends AbstractHasNextCommand {

	private final String text;
	
	public AbstractHasNextCommand2(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "|" + text;
	}

}
