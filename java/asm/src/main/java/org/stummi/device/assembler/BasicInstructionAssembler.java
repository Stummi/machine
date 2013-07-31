package org.stummi.device.assembler;

import java.nio.ByteBuffer;
import java.util.Set;

public class BasicInstructionAssembler implements InstructionAssembler {

	private byte value;

	public BasicInstructionAssembler(byte value) {
		this.value = value;
	}

	@Override
	public Set<FutureValue> assemble(String[] line, ByteBuffer buffer)
			throws AssemblyException {
		if(line.length!=1) {
			throw new AssemblyException(String.format("%s doesn't allow any params", line[0]));
		}
		buffer.put(value);
		return null;
	}

}
