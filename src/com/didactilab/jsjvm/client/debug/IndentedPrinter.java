package com.didactilab.jsjvm.client.debug;

public class IndentedPrinter implements Printer {

	private final String indent;
	private final Printer printer;
	
	private boolean startOfLine = true;
	
	public IndentedPrinter(Printer printer, String indent) {
		this.printer = printer;
		this.indent = indent;
	}
	
	@Override
	public void println(Object o) {
		if (startOfLine) {
			printer.print(indent);
		}
		printer.println(o);
		startOfLine = true;
	}

	@Override
	public void println(Object... objects) {
		if (startOfLine) {
			printer.print(indent);
		}
		printer.println(objects);
		startOfLine = true;
	}

	@Override
	public void print(Object o) {
		if (startOfLine) {
			printer.print(indent);
			startOfLine = false;
		}
		printer.print(o);
	}

	@Override
	public void print(Object... objects) {
		if (startOfLine) {
			printer.print(indent);
			startOfLine = false;
		}
		printer.print(objects);
	}

}
