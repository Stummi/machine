package org.stummi.device.assembler;

import java.nio.ByteBuffer;

import lombok.Data;

@Data
public abstract class AbstractFutureValue implements FutureValue {
	private int lineNumber;

	public static void putValue(ByteBuffer bb, int address, int value,
			boolean word) {
		if (word) {
			bb.putShort(address, (short) value);
		} else {
			bb.put(address, (byte) value);
		}
	}
}
