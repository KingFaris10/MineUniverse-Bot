package com.faris.skype.commands;

import java.net.Socket;
import java.util.List;

import com.faris.skype.SkypeCommand;
import com.faris.skype.utils.Utils;
import com.skype.Chat;
import com.skype.SkypeException;
import com.skype.User;

public class CommandPing extends SkypeCommand {
	private static final String IPV4_REGEX = "\\A(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z";

	@Override
	public boolean onCommand(Chat chat, User sender, String command, List<String> args) throws SkypeException {
		if (command.equalsIgnoreCase("ping")) {
			if (args.size() == 1 || args.size() == 2) {
				String strIP = args.get(0);
				String strPort = args.size() > 1 ? args.get(1) : "80";
				if (isIP(strIP)) {
					if (Utils.isInteger(strPort)) {
						int port = Integer.parseInt(strPort);
						if (isOnline(strIP, port)) chat.send(strIP + ":" + port + " is online.");
						else chat.send(strIP + ":" + port + " is offline.");
					} else {
						chat.send(sender.getDisplayName() + ", please enter a valid port.");
					}
				} else {
					chat.send(sender.getDisplayName() + ", please enter a valid IP address.");
				}
			} else {
				chat.send(getUsage(sender, "ping <ip> [<port>]"));
			}
			return true;
		}
		return false;
	}

	private static boolean isIP(String ipAddress) {
		return ipAddress != null && ipAddress.matches(IPV4_REGEX);
	}

	private static boolean isOnline(String ipAddress, int portNumber) {
		try (Socket s = new Socket(ipAddress, portNumber)) {
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
}
