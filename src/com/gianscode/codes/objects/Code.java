package com.gianscode.codes.objects;

import java.util.List;

public class Code {

	private String name;
	private List<String> commands;

	public Code(String name, List<String> commands) {
		this.name = name;
		this.commands = commands;
	}

	public String getName() {
		return name;
	}

	public List<String> getCommands() {
		return commands;
	}
}