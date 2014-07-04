package com.faris.skype;

import java.io.File;

import com.faris.skype.utils.FileConfiguration;

public class BotSettings {
	private Permissions permissions = null;

	private File botFile = null, permissionsFile = null;
	private FileConfiguration botConfig = null;

	public BotSettings() {
		File dataFolder = Main.getInstance().getDataFolder();
		try {
			this.botFile = new File(dataFolder, "config.yml");
			if (!this.botFile.exists()) this.botFile.createNewFile();
			this.botConfig = FileConfiguration.loadConfiguration(this.botFile);

			this.permissionsFile = new File(dataFolder, "permissions.yml");
			if (!this.permissionsFile.exists()) this.permissionsFile.createNewFile();
			this.permissions = new Permissions(this.permissionsFile);
			this.permissions.load();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getClass().getSimpleName() + " error: Failed to load the configuration.");
		}
	}

	public BotSettings loadConfiguration() throws Exception {
		this.permissions.load();
		return this;
	}

	public FileConfiguration getBotsConfig() {
		return this.botConfig;
	}

	public Permissions getPermissions() {
		return this.permissions;
	}

}
