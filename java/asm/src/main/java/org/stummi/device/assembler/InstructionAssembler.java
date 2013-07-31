package org.stummi.device.assembler;

import java.nio.ByteBuffer;
import java.util.Set;

public interface InstructionAssembler {
	Set<FutureValue> assemble(String[] line, ByteBuffer buffer)
			throws AssemblyException;
}
