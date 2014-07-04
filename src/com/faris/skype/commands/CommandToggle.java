package com.faris.skype.commands;

import java.util.List;

import com.faris.skype.Main;
import com.faris.skype.SkypeCommand;
import com.faris.skype.utils.Utils;
import com.skype.Chat;
import com.skype.SkypeException;
import com.skype.User;

public class CommandToggle extends SkypeCommand {

	@Override
	public boolean onCommand(Chat chat, User sender, String command, List<String> args) throws SkypeException {
		int toggleType = command.equalsIgnoreCase("start") ? 1 : (command.equalsIgnoreCase("stop") ? 2 : 0);
		if (toggleType != 0) {
			if (args.size() == 0) {
				if (this.getPermissions().hasPermission(sender, "skype.command.toggle")) {
					if (toggleType == 1) {
						if (Main.getInstance().botEnabled) {
							chat.send(Utils.getDate() + "MineUniverse is already enabled.");
							return true;
						} else {
							chat.send(Utils.getDate() + "Turning on...");
							Main.getInstance().botEnabled = true;
						}
					} else {
						if (!Main.getInstance().botEnabled) {
							chat.send(Utils.getDate() + "MineUniverse is already disabled.");
							return true;
						} else {
							chat.send(Utils.getDate() + "Turning off...");
							Main.getInstance().botEnabled = false;
						}
					}
					Main.getInstance().toggleBot.setText("Toggle: " + (Main.getInstance().botEnabled ? "Off" : "On"));
				} else {
					chat.send(this.getNoAccess());
				}
			} else {
				chat.send(getUsage(sender.getDisplayName(), toggleType == 1 ? "start" : "stop"));
			}
			return true;
		}
		return false;
	}

}
