package com.didactilab.jsjvm.client.flow.command;

public final class CommandTest {
	
	private Command start;
	private Command end;

	public CommandTest() {
		initTree();
	}
	
	private void initTree() {
		start = new EvalCommand("init()");
		IfCommand ifCmd = new IfCommand();
		ifCmd.setPrevious(start);
		end = new ReturnCommand("return x");
		ifCmd.setTrueCommand(end);
		AssignCommand cmd = new AssignCommand("x = -x");
		cmd.setPrevious(ifCmd);
		cmd.setNext(end);
	}
	
	public void run() {
		
	}
	
	public static void main(String[] args) {
		CommandTest test = new CommandTest();
		test.run();
	}

}
