package com.faris.skype;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.faris.skype.commands.CommandDefine;
import com.faris.skype.commands.CommandMinecraftPing;
import com.faris.skype.commands.CommandPing;
import com.faris.skype.commands.CommandSay;
import com.faris.skype.commands.CommandToggle;
import com.faris.skype.utils.Utils;
import com.skype.Chat;
import com.skype.ChatMessage;
import com.skype.ChatMessageListener;
import com.skype.Skype;
import com.skype.SkypeException;
import com.skype.User;

public class Main extends JFrame implements ChatMessageListener {
	private static Main mainInstance = null;

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 720, HEIGHT = WIDTH / 16 * 9;

	private File dataFolder = null;
	private BotSettings botSettings = null;

	public JButton toggleBot = null;
	public boolean botEnabled = true;

	public Main() {
		mainInstance = this;
		this.registerCommands();

		this.setTitle("MineUniverse Skype Bot");
		this.setSize(new Dimension(WIDTH, HEIGHT));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.toggleBot = new JButton("Toggle: Off") {
			private static final long serialVersionUID = 1L;

			{
				this.setBounds(30, 30, this.getWidth() - 30, this.getHeight() - 30);
				this.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						if (botEnabled) {
							botEnabled = false;
							toggleBot.setText("Toggle: On");
						} else {
							botEnabled = true;
							toggleBot.setText("Toggle: Off");
						}
					}
				});
			}
		};

		this.add(this.toggleBot);
	}

	private void registerCommands() {
		SkypeCommand.registerCommand(CommandDefine.class);
		SkypeCommand.registerCommand(CommandMinecraftPing.class);
		SkypeCommand.registerCommand(CommandPing.class);
		SkypeCommand.registerCommand(CommandSay.class);
		SkypeCommand.registerCommand(CommandToggle.class);
	}

	protected void initialiseBot() {
		try {
			try {
				this.dataFolder = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath(), "data");
			} catch (Exception ex) {
				this.dataFolder = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile(), "data");
			}
			this.dataFolder.mkdirs();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("An error occurred when trying to create the data folder.");
		}
		try {
			this.botSettings = new BotSettings() {
				{
					this.getPermissions().addPermission("KingFaris10", Arrays.asList("skype.command.stop"));
					this.getPermissions().save();
					this.loadConfiguration();
				}
			};
		} catch (Exception ex) {
			this.botSettings = new BotSettings();
			ex.printStackTrace();
			System.out.println("Failed to load permissions.");
		}
	}

	@Override
	public void chatMessageReceived(ChatMessage event) throws SkypeException {
		if (this.botEnabled && event.getContent().startsWith(".mu:")) {
			List<String> args = new ArrayList<String>();
			String strMessage = event.getContent().replaceFirst(".mu:", "");
			String command = "";
			if (strMessage.contains(" ")) {
				String[] argsSplit = strMessage.split(" ");
				command = argsSplit[0];
				if (argsSplit.length > 1) {
					for (int argIndex = 1; argIndex < argsSplit.length; argIndex++) {
						args.add(argsSplit[argIndex]);
					}
				}
			} else {
				command = strMessage.trim();
			}
			try {
				if (command != null && !this.onCommand(event.getChat(), event.getSender(), command, args)) event.getChat().send(Utils.getDate() + "MineUniverse: " + event.getSenderDisplayName() + ", you typed an invalid command.");
			} catch (Exception ex) {
				try {
					event.getChat().send(Utils.getDate() + "MineUniverse: " + ex.getClass().getSimpleName() + " error occurred.");
				} catch (Exception ex2) {
					System.out.println(ex.getClass().getSimpleName() + " error occurred.");
				}
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void chatMessageSent(ChatMessage event) throws SkypeException {
		if (this.botEnabled && event.getContent().startsWith(".mu:")) {
			List<String> args = new ArrayList<String>();
			String strMessage = event.getContent().replaceFirst(".mu:", "");
			String command = "";
			if (strMessage.contains(" ")) {
				String[] argsSplit = strMessage.split(" ");
				command = argsSplit[0];
				if (argsSplit.length > 1) {
					for (int argIndex = 1; argIndex < argsSplit.length; argIndex++) {
						args.add(argsSplit[argIndex]);
					}
				}
			} else {
				command = strMessage.trim();
			}
			try {
				if (command != null && !this.onCommand(event.getChat(), event.getSender(), command, args)) event.getChat().send(Utils.getDate() + "MineUniverse: " + event.getSenderDisplayName() + ", you typed an invalid command.");
			} catch (Exception ex) {
				try {
					event.getChat().send(Utils.getDate() + "MineUniverse: " + ex.getClass().getSimpleName() + " error occurred.");
				} catch (Exception ex2) {
					System.out.println(ex.getClass().getSimpleName() + " error occurred.");
				}
				ex.printStackTrace();
			}
		}
	}

	public boolean onCommand(Chat chat, User sender, String command, List<String> args) throws SkypeException {
		for (SkypeCommand skypeCmd : SkypeCommand.getCommands()) {
			if (skypeCmd.onCommand(chat, sender, command, args)) return true;
		}
		return false;
	}

	public File getDataFolder() {
		return this.dataFolder;
	}

	public BotSettings getSettings() {
		return this.botSettings;
	}

	public static void main(String[] args) {
		try {
			if (mainInstance != null) Skype.removeChatMessageListener(mainInstance);
			mainInstance = new Main();
			mainInstance.initialiseBot();
			Skype.setDaemon(false);
			Skype.addChatMessageListener(mainInstance);
			System.out.println(Utils.getDate() + "Successfully registered the chat message listener.");
		} catch (Exception ex) {
			if (ex.getClass().getSimpleName().equalsIgnoreCase("NotAttachedException")) System.out.println(Utils.getDate() + "Could not find the Skype process or Skype has not approved this application.");
			else ex.printStackTrace();
			System.out.println(Utils.getDate() + "Shutting down...");
			System.exit(0);
		}
	}

	public static Main getInstance() {
		return mainInstance;
	}
}
