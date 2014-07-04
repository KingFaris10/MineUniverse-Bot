package com.faris.skype.commands;

import java.util.Arrays;
import java.util.List;

import com.faris.skype.SkypeCommand;
import com.skype.Chat;
import com.skype.SkypeException;
import com.skype.User;

public class CommandSay extends SkypeCommand {

	@Override
	public boolean onCommand(Chat chat, User sender, String command, List<String> args) throws SkypeException {
		if (command.equalsIgnoreCase("say")) {
			if (!args.isEmpty()) {
				chat.send(this.getArgs(args));
			} else {
				chat.send(this.getUsage(sender, 0));
			}
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "Make the bot say a message!";
	}

	@Override
	public List<String> getUsages() {
		return Arrays.asList("say <message>");
	}

}
