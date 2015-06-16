package com.didactilab.jsjvm.client.debug;

public class SystemPrinter implements Printer {

	@Override
	public void println(Object o) {
		System.out.println(o);
	}

	@Override
	public void println(Object... objects) {
		print(objects);
		println("");
	}

	@Override
	public void print(Object o) {
		System.out.print(o);
	}

	@Override
	public void print(Object... objects) {
		for (Object obj : objects) {
			print(obj);
		}
	}

}
