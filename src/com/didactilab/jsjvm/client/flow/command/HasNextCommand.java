package com.didactilab.jsjvm.client.flow.command;

public interface HasNextCommand extends Command {
	Command next();
}
