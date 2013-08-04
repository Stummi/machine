package org.stummi.device.assembler;

import java.nio.ByteBuffer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Sometimes the Assembly of an Instruction needs values which are determined
 * later (e.g. Labels)
 */
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class FutureConstantValue extends AbstractFutureValue {
	int bytePosition;
	String name;
	boolean relative;
	boolean word;

	public void assemble(Assembler asm)
			throws AssemblyException {
		int value = asm.getConstantValue(name);
		value = relative ? bytePosition - value : value;
		ByteBuffer bb = asm.getByteBuffer();
		putValue(bb, bytePosition, value, word);
	}
}
