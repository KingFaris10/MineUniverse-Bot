package com.faris.skype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.skype.Chat;
import com.skype.SkypeException;
import com.skype.User;

public abstract class SkypeCommand {
	private static List<SkypeCommand> registeredCommands = new ArrayList<SkypeCommand>();

	public abstract boolean onCommand(Chat chat, User sender, String command, List<String> args) throws SkypeException;

	public abstract String getHelp();

	public abstract List<String> getUsages();

	protected String getArgs(List<String> args) {
		return this.getArgs(args, 0);
	}

	protected String getArgs(List<String> args, int startingIndex) {
		StringBuilder sbArgs = new StringBuilder();
		for (int i = startingIndex; i < args.size(); i++) {
			sbArgs.append(args.get(i)).append(" ");
		}
		return sbArgs.toString().trim();
	}

	protected String getNoAccess() {
		return "You do not have access to that command.";
	}

	protected Permissions getPermissions() {
		return Main.getInstance().getSettings().getPermissions();
	}

	protected String getUsage(String sender, int index) {
		return "Usage: " + this.getUsages().get(index);
	}

	protected String getUsage(User sender, int index) throws SkypeException {
		return "Usage: " + this.getUsages().get(index);
	}

	public static void clearCommands() {
		registeredCommands.clear();
	}

	public static List<SkypeCommand> getCommands() {
		return Collections.unmodifiableList(registeredCommands);
	}

	public static void registerCommand(Class<? extends SkypeCommand> skypeCommandClass) {
		try {
			SkypeCommand skypeCommand = (SkypeCommand) skypeCommandClass.getConstructors()[0].newInstance();
			if (skypeCommand != null && !registeredCommands.contains(skypeCommand)) registeredCommands.add(skypeCommand);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
