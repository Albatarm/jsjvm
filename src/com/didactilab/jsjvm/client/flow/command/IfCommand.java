package com.didactilab.jsjvm.client.flow.command;

public class IfCommand extends AbstractCommand {

	private Command ifTrue, ifFalse;
	
	public IfCommand() {
	}
	
	public void setTrueCommand(Command cmd) {
		ifTrue = cmd;
	}
	
	public void setFalseCommand(Command cmd) {
		ifFalse = cmd;
	}
	
	public Command nextIfTrue() {
		return ifTrue;
	}
	
	public Command nextIfFalse() {
		return ifFalse;
	}

}
