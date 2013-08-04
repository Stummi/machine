package org.stummi.device.assembler;

public class AssemblyException extends Exception {
	private static final long serialVersionUID = -4054606342464444178L;

	private int asmLine;

	public AssemblyException(String message, int asmLine) {
		super(message);
		this.asmLine = asmLine;
	}
	
	public AssemblyException(String message) {
		this(message, 0);
	}

	public int getAsmLine() {
		return asmLine;
	}
	
	public void setAsmLine(int asmLine) {
		this.asmLine = asmLine;
	}
}
