package com.faris.skype.commands;

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
				chat.send(getUsage(sender.getDisplayName(), "say <message>"));
			}
			return true;
		}
		return false;
	}

}
