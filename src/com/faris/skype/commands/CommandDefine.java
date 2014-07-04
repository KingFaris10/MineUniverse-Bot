package com.faris.skype.commands;

import java.util.Arrays;
import java.util.List;

import com.faris.skype.SkypeCommand;
import com.faris.skype.utils.Utils.StringUtils;
import com.faris.skype.utils.web.DuckDuckGo;
import com.faris.skype.utils.web.DuckDuckGo.DuckDuckResult;
import com.skype.Chat;
import com.skype.SkypeException;
import com.skype.User;

public class CommandDefine extends SkypeCommand {

	@Override
	public boolean onCommand(Chat chat, User sender, String command, List<String> args) throws SkypeException {
		if (command.equalsIgnoreCase("define")) {
			if (args.size() == 1) {
				try {
					String[] argsToArray = args.toArray(new String[args.size()]);
					DuckDuckResult result = DuckDuckGo.search(StringUtils.implode(argsToArray));
					String def = result.Definition;
					if (def != null && !def.equals("")) {
						chat.send("Definition of " + args.get(0) + ": " + def);
						chat.send("Definition URL: " + result.DefinitionURL);
					} else {
						chat.send("No definition for: " + StringUtils.implode(argsToArray));
					}
				} catch (Exception ex) {
					chat.send(ex.getClass().getSimpleName() + " error occurred.");
					chat.send(ex.getMessage());
				}
			} else {
				chat.send(this.getUsage(sender, 0));
			}
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "Get the definition of a word.";
	}

	@Override
	public List<String> getUsages() {
		return Arrays.asList("define <word>");
	}

}
