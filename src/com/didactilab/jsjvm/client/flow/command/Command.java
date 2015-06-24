package com.didactilab.jsjvm.client.flow.command;

public interface Command {
	Command getPrevious();
	void setPrevious(Command previous);
}
