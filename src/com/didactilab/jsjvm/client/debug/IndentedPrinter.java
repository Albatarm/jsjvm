package com.didactilab.jsjvm.client.debug;

public class IndentedPrinter implements Printer {

	private final String indent;
	private final Printer printer;
	
	public IndentedPrinter(Printer printer, String indent) {
		this.printer = printer;
		this.indent = indent;
	}
	
	@Override
	public void println(Object o) {
		printer.println(indent, o);
	}

	@Override
	public void println(Object... objects) {
		printer.print(indent);
		printer.println(objects);
	}

	@Override
	public void print(Object o) {
		printer.print(indent, o);
	}

	@Override
	public void print(Object... objects) {
		printer.print(indent);
		printer.print(objects);
	}

}
