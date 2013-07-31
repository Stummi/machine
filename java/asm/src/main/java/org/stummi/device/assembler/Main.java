package org.stummi.device.assembler;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

public class Main {
	public static void main(String[] args) throws IOException {
		FileReader fr = new FileReader("example.sasm");
		OutputStream out = new FileOutputStream("example.bin");
		try {
			new Assembler(fr, out).assemble();
		} catch (AssemblyException e) {
			System.err.println("Assembly Error at line " + e.getAsmLine() +  " : " + e.getLocalizedMessage());
		}
	}
}
