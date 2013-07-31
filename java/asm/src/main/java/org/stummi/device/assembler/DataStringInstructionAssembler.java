package org.stummi.device.assembler;

import java.nio.ByteBuffer;
import java.util.Set;

public class DataStringInstructionAssembler implements InstructionAssembler {

	@Override
	public Set<FutureValue> assemble(String[] line, ByteBuffer buffer)
			throws AssemblyException {
		if (line.length < 2) {
			throw new AssemblyException("DATAS needs at least one argument");
		}

		StringBuilder sb = new StringBuilder();
		for (int idx = 1; idx < line.length; idx++) {
			if (idx > 1) {
				sb.append(" ");
			}
			sb.append(line[idx]);
		}

		buffer.put(sb.toString().getBytes());
		return null;
	}

}
