package com.didactilab.jsjvm.client.loader;

import java.io.File;
import java.io.IOException;

import com.didactilab.jsjvm.client.reader.FileReader;
import com.didactilab.jsjvm.client.reader.Reader;

public class JRESystemJavaClassLoader extends SystemJavaClassLoader {

	private final File dir;
	
	public JRESystemJavaClassLoader(File dir) {
		this.dir = dir;
	}
	
	@Override
	protected Reader openReaderForClass(String name) throws IOException {
		File file = new File(dir.toString() + "/" + name + ".class");
		if (!file.exists()) {
			throw new IOException("File not found : " + file);
		}
		return new FileReader(file);
	}

}
