package com.didactilab.jsjvm.client.flow.command;

abstract class AbstractCommand implements Command {

	private Command previous;
	
	public AbstractCommand() {
	}
	
	public void setPrevious(Command cmd) {
		previous = cmd;
	}
	
	@Override
	public Command getPrevious() {
		return previous;
	}

}
