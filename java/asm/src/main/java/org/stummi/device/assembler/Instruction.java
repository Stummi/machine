package org.stummi.device.assembler;

public enum Instruction {
	NUL(0x00),
	STOP(0xFF),
	AGETB(0x01),
	AGETW(0x02),
	APUTB(0x03),
	APUTW(0x04),
	ASET(new SetInstructionAssembler(0x05)),
	AINC(0x06),
	ADEC(0x07),
	UGETB(0x08),
	UGETW(0x09),
	UPUTB(0x0A),
	UPUTW(0x0B),
	USET(new SetInstructionAssembler(0x0C)),
	UINC(0x0D),
	UDEC(0x0E),
	DAJMP(0x0F),
	DRJMP(0x10),
	IAJMP(0x11),
	IRJMP(0x12),
	ICJMP(0x13),
 DATAB(
			new DataByteInstructionAssembler()), DATAW(
			new DataWordInstructionAssembler()), DATAS(
			new DataStringInstructionAssembler()),
	;
	
	private InstructionAssembler instructionAssembler;
	
	private Instruction(int value) {
		this(new BasicInstructionAssembler((byte)value));
	}
	
	public InstructionAssembler getInstructionAssembler() {
		return instructionAssembler;
	}
	
	private Instruction(InstructionAssembler instructionAssembler) {
		this.instructionAssembler = instructionAssembler;
	}
}