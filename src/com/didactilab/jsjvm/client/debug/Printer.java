package com.didactilab.jsjvm.client.debug;

public interface Printer {
	void println(Object o);
	void println(Object... objects);
	void print(Object o);
	void print(Object... objects);
}
