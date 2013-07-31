package org.stummi.device.assembler;

public interface FutureValue {
	void assemble(Assembler assembler) throws AssemblyException;

	void setLineNumber(int lineNumber);

	int getLineNumber();
}
