package com.faris.skype.utils;

import com.faris.skype.utils.Utils.StringUtils;
import com.google.gson.Gson;

/**
 * @author Double0negative
 */
public class YivePinger {

	public static final String HOST = "http://mineskin.ca/v2/query/info/?ip={0}&port={1}";
	public static final Gson gson = new Gson();

	public static MinecraftPing ping(String host) throws Exception {
		return ping(host, 25565);
	}

	public static MinecraftPing ping(String host, int port) throws Exception {
		String json = WebClient.request(StringUtils.replaceVars(HOST, host, port));
		return gson.fromJson(json, MinecraftPing.class);
	}

	public static class MinecraftPing {
		public boolean status;
		public String error;
		public String motd;
		public String version;
		public Players players;
		public long ping;

		public class Players {
			public int online, max;
		}

	}

}