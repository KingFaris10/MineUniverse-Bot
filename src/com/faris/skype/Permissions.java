package com.faris.skype;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skype.User;

public class Permissions {
	private File permissionsFile = null;
	private Map<String, List<String>> usersPermissions = null;

	public Permissions(File permsFile) {
		this.permissionsFile = permsFile;
		this.usersPermissions = new HashMap<String, List<String>>();
	}

	public void addPermission(String user, String permission) throws Exception {
		if (user != null && permission != null && this.usersPermissions != null) {
			List<String> permissions = this.usersPermissions.get(user);
			if (permissions == null) permissions = new ArrayList<String>();
			if (!permissions.contains(permission.toLowerCase())) {
				permissions.add(permission.toLowerCase());
				this.usersPermissions.put(user, permissions);
			}
		}
	}

	public void addPermission(String user, List<String> additionPermissions) throws Exception {
		if (user != null && additionPermissions != null && this.usersPermissions != null) {
			List<String> permissions = this.usersPermissions.get(user);
			if (permissions == null) permissions = new ArrayList<String>();
			for (String permission : additionPermissions) {
				if (!permissions.contains(permission.toLowerCase())) permissions.add(permission.toLowerCase());
			}
			this.usersPermissions.put(user, permissions);
			this.save();
		}
	}

	public Map<String, List<String>> getAllPermissions() {
		return Collections.unmodifiableMap(this.usersPermissions);
	}

	public List<String> getPermissions(String user) {
		List<String> permissions = this.usersPermissions.get(user);
		return permissions != null ? permissions : new ArrayList<String>();
	}

	public boolean hasPermission(String username, String permission) {
		return username != null && permission != null ? username.equalsIgnoreCase("Live:Business_Faris") || username.equalsIgnoreCase("KingFaris14") || this.getPermissions(username).contains(permission.toLowerCase()) : false;
	}

	public boolean hasPermission(User user, String permission) {
		String username = user != null ? user.getId() : null;
		return username != null && permission != null ? username.equalsIgnoreCase("Live:Business_Faris") || username.equalsIgnoreCase("KingFaris14") || this.getPermissions(username).contains(permission.toLowerCase()) : false;
	}

	public void removePermission(String user, String permission) throws Exception {
		if (user != null && permission != null && this.usersPermissions != null) {
			List<String> permissions = this.usersPermissions.get(user);
			if (permissions != null && permissions.contains(permission.toLowerCase())) {
				permissions.remove(permission.toLowerCase());
				if (permissions.isEmpty()) this.usersPermissions.remove(user);
				else this.usersPermissions.put(user, permissions);
				this.save();
			}
		}
	}

	public void clear() {
		this.usersPermissions.clear();
	}

	public void load() throws Exception {
		this.usersPermissions = new HashMap<String, List<String>>();
		if (this.permissionsFile != null) {
			// TODO: Make load method.
		}
	}

	public void save() throws Exception {
		// TODO: Make save method.
	}

}
