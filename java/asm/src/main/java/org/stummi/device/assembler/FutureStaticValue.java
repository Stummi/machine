package org.stummi.device.assembler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class FutureStaticValue extends AbstractFutureValue {

	private int byteAddress;
	private int value;
	private boolean word;

	@Override
	public void assemble(Assembler assembler) {
		putValue(assembler.getByteBuffer(), byteAddress, value, word);
	}

}
