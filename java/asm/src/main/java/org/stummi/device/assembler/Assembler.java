package org.stummi.device.assembler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Assembler {
	private OutputStream out;
	private BufferedReader in;
	private ByteBuffer buffer;
	private byte[] bytes;

	private Map<String, Integer> constants;

	private List<FutureValue> futureValues;
	private int currentLine;

	public Assembler(Reader in, OutputStream out) {
		this.in = new BufferedReader(in);
		this.out = out;
		this.bytes = new byte[0x1000];
		this.buffer = ByteBuffer.wrap(bytes);
		this.constants = new HashMap<>();
		this.futureValues = new LinkedList<>();
	}

	public void assemble() throws AssemblyException, IOException {
		assembleInstructions();
		assembleFutures();
		out.write(bytes);;
	}

	private void assembleInstructions() throws IOException, AssemblyException {
		log.info("assemble instructions");
		String line;
		while ((line = in.readLine()) != null) {
			currentLine++;
			try {
				assembleLine(line);
			} catch (AssemblyException ae) {
				ae.setAsmLine(currentLine);
				throw ae;
			}
		}

	}

	private void assembleFutures() throws AssemblyException {
		log.info("assemble futures");
		for (FutureValue fv : futureValues) {
			fv.assemble(this);
		}
	}

	private void assembleLine(String line) throws AssemblyException {
		String[] spl = splitLine(line);
		if (spl.length == 0) {
			return;
		}
		assembleLine(spl);
	}

	private void assembleLine(String[] splitLine) throws AssemblyException {
		String cmd = splitLine[0];
		if (cmd.startsWith(":")) {
			handleLabel(splitLine);
		} else if (cmd.startsWith("$")) {
			handleConstant(splitLine);
		} else {
			handleInstruction(splitLine);
		}
	}

	private void handleInstruction(String[] splitLine) throws AssemblyException {
		String cmd = splitLine[0].toUpperCase();
		Instruction i;
		try {
			i = Instruction.valueOf(cmd);
		} catch (IllegalArgumentException iae) {
			throw new AssemblyException("unknown instruction: " + cmd);
		}

		Set<FutureValue> futures = i.getInstructionAssembler().assemble(
				splitLine, buffer);
		if (futures != null) {
			addFutures(futures);
		}
	}

	private void addFutures(Set<FutureValue> futures) {
		for (FutureValue fv : futures) {
			fv.setLineNumber(currentLine);
			futureValues.add(fv);
		}
	}

	private void handleConstant(String[] splitLine) throws AssemblyException {
		if (splitLine.length != 2) {
			throw new AssemblyException(
					"constant defintions take exact one argument");
		}

		String name = splitLine[0].substring(1);
		Integer value = parseNumber(splitLine[1]);
		addConstant(name, value);
	}

	private void addConstant(String name, Integer value)
			throws AssemblyException {
		if (constants.containsKey(name)) {
			throw new AssemblyException("dublicate constant: " + name);
		}
		this.constants.put(name, value);
	}

	public static FutureValue parseValue(int byteAddress, String string,
			boolean word) throws AssemblyException {
		if (string.startsWith("$")) {
			String name = string.substring(1);
			return new FutureConstantValue(byteAddress, name, false, word);
		} else if (string.startsWith("%")) {
			String name = string.substring(1);
			return new FutureConstantValue(byteAddress, name, true, word);
		}

		return new FutureStaticValue(byteAddress, parseNumber(string), word);
	}

	public static Integer parseNumber(String string) throws AssemblyException {
		try {
			int value;
			boolean negative = false;
			if (string.startsWith("+")) {
				string = string.substring(1);
			} else if (string.startsWith("-")) {
				string = string.substring(1);
				negative = true;
			}
			if (string.equals("0")) {
				value = 0;
			} else if (string.toLowerCase().startsWith("0x")) {
				value = Integer.parseInt(string.substring(2), 16);
			} else if (string.toLowerCase().startsWith("0")) {
				value = Integer.parseInt(string.substring(1), 8);
			} else {
				value = Integer.parseInt(string);
			}

			return negative ? -value : value;
		} catch (NumberFormatException nfe) {
			throw new AssemblyException("illegal number: " + string);
		}
	}

	private void handleLabel(String[] splitLine) throws AssemblyException {
		if (splitLine.length > 1) {
			throw new AssemblyException("labels can't have arguments");
		}

		String name = splitLine[0].substring(1);
		int value = buffer.position();

		addConstant(name, value);
	}

	private String[] splitLine(String line) {
		int commentPos = line.indexOf(';');
		if (commentPos >= 0) {
			line = line.substring(0, commentPos);
		}
		line = line.trim();
		line = line.replace("\t", " ");
		while (line.contains("  ")) {
			line = line.replace("  ", " ");
		}
		return line.split(" ");
	}

	public int getConstantValue(String constantName) throws AssemblyException {
		if (!constants.containsKey(constantName)) {
			throw new AssemblyException("unknown constant: " + constantName);
		}

		return constants.get(constantName);
	}

	public ByteBuffer getByteBuffer() {
		return buffer;
	}
}
