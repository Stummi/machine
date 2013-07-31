package org.stummi.device.assembler;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

public class SetInstructionAssembler implements InstructionAssembler {

	private byte instruction;

	public SetInstructionAssembler(int i) {
		this.instruction = (byte) i;
	}

	@Override
	public Set<FutureValue> assemble(String[] line, ByteBuffer buffer)
			throws AssemblyException {
		if(line.length != 2) { 
			throw new AssemblyException("SET-commands need exact one param");
		}	
		buffer.put(instruction);
		Set<FutureValue> futures = new HashSet<>();
		futures.add(Assembler.parseValue(buffer.position(), line[1], true));
		buffer.putShort((short) 0);
		return futures;
	}


}
