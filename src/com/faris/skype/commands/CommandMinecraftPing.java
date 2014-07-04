package com.faris.skype.commands;

import java.util.Arrays;
import java.util.List;

import com.faris.skype.SkypeCommand;
import com.faris.skype.utils.Utils;
import com.faris.skype.utils.YivePinger;
import com.faris.skype.utils.YivePinger.MinecraftPing;
import com.skype.Chat;
import com.skype.SkypeException;
import com.skype.User;

public class CommandMinecraftPing extends SkypeCommand {
	private static final String IPV4_REGEX = "\\A(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z";

	@Override
	public boolean onCommand(Chat chat, User sender, String command, List<String> args) throws SkypeException {
		if (command.equalsIgnoreCase("mcping")) {
			if (args.size() == 1 || args.size() == 2) {
				String strIP = args.get(0);
				String strPort = args.size() > 1 ? args.get(1) : "25565";
				if (isIP(strIP)) {
					if (Utils.isInteger(strPort)) {
						try {
							MinecraftPing ping = args.size() > 1 ? YivePinger.ping(strIP, Integer.parseInt(strPort)) : YivePinger.ping(strIP);
							if (ping.status) {
								chat.send("(" + strIP + ") " + ping.motd.replace("  ", " ").trim() + " - " + ping.version + " - " + ping.players.online + "/" + ping.players.max + " players. Ping: " + ping.ping + "ms");
							} else {
								chat.send("Failed to ping the server (" + strIP + ").");
							}
						} catch (Exception ex) {
							chat.send("Failed to ping the server (" + strIP + ") due to an error: " + ex.getClass().getSimpleName());
						}
					} else {
						chat.send(sender.getDisplayName() + ", please enter a valid port.");
					}
				} else {
					chat.send(sender.getDisplayName() + ", please enter a valid IP address.");
				}
			} else {
				chat.send(this.getUsage(sender, 0));
			}
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "Ping a Minecraft server.";
	}

	@Override
	public List<String> getUsages() {
		return Arrays.asList("mcping <serverIP> [<port>]");
	}

	private static boolean isIP(String ipAddress) {
		return ipAddress != null && ipAddress.matches(IPV4_REGEX);
	}

}
