package com.didactilab.jsjvm.client.flow.command;

public abstract class AbstractCommand2 extends AbstractCommand {

	private final String text;
	
	public AbstractCommand2(String text) {
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
