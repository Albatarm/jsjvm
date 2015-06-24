package com.didactilab.jsjvm.client.flow.command;

public abstract class AbstractHasNextCommand extends AbstractCommand implements
		HasNextCommand {
	
	private Command next;

	@Override
	public Command next() {
		return next;
	}
	
	public void setNext(Command cmd) {
		next = cmd;
		next.setPrevious(this);
	}

}
