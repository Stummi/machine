package org.stummi.device.assembler;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

public class DataByteInstructionAssembler implements InstructionAssembler {

	@Override
	public Set<FutureValue> assemble(String[] line, ByteBuffer buffer)
			throws AssemblyException {
		if (line.length != 2) {
			throw new AssemblyException("DATAB must have exact one argument");
		}
		Set<FutureValue> returnSet = new HashSet<>();
		returnSet.add(Assembler.parseValue(buffer.position(), line[1], false));
		buffer.put((byte) 0);
		return returnSet;
	}
}
