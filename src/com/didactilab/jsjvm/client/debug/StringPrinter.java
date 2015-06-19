package com.didactilab.jsjvm.client.debug;

public class StringPrinter implements Printer {

	private final StringBuilder sb = new StringBuilder();
	
	@Override
	public void println(Object o) {
		sb.append(o).append('\n');
	}

	@Override
	public void println(Object... objects) {
		print(objects);
		sb.append('\n');
	}

	@Override
	public void print(Object o) {
		sb.append(o);
	}

	@Override
	public void print(Object... objects) {
		for (Object obj : objects) {
			sb.append(obj);
		}
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}

}
