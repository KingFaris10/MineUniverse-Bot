package com.faris.skype.utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class Utils {
	// Date
	private static GregorianCalendar calendar = null;
	private static final String strDateFormat = "[%s/%s/%s %s:%s:%s] ";

	public static String getDate() {
		if (calendar == null) calendar = (GregorianCalendar) Calendar.getInstance(Locale.UK);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		int minuteOfHour = calendar.get(Calendar.MINUTE);
		int secondOfMinute = calendar.get(Calendar.SECOND);
		return String.format(strDateFormat, dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth, month < 10 ? "0" + month : month, calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY), minuteOfHour < 10 ? "0" + minuteOfHour : minuteOfHour, secondOfMinute < 10 ? "0" + secondOfMinute : secondOfMinute);
	}

	public static boolean isInteger(String aString) {
		try {
			Integer.parseInt(aString);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static class StringUtils {
		public static String implode(String... strs) {
			StringBuilder sb = new StringBuilder();
			for (String str : strs) {
				sb.append(str).append(" ");
			}
			return sb.toString().trim();
		}

		public static String implode(int start, String... strs) {
			List<String> list = Arrays.asList(strs);
			for (int a = 0; a < start; a++) {
				list.remove(0);
			}
			return implode(list.toArray(new String[0]));
		}

		public static String replaceVars(String str, Object... args) {
			int a = 0;
			for (Object ob : args) {
				str = str.replace("{" + a + "}", ob.toString());
				a++;
			}
			return str;

		}
	}

	public static String createGist(String contents) {
		/**OAuthService oauthService = new OAuthService();

		// Replace with actual login and password
		oauthService.getClient().setCredentials("--", "--");

		// Create authorisation with 'gist' scope only
		Authorization auth = new Authorization();
		auth.setScopes(Arrays.asList("gist"));
		auth = oauthService.createAuthorization(auth);

		// Create Gist service configured with OAuth2 token
		GistService gistService = new GistService();
		gistService.getClient().setOAuth2Token(auth.getToken());

		// Create Gist
		Gist gist = new Gist();
		gist.setPublic(false);
		gist.setDescription("Created by MineUniverse Bot via Java API");
		GistFile file = new GistFile();
		file.setContent("Gist!");
		file.setFilename("gist.txt");
		gist.setFiles(Collections.singletonMap(file.getFilename(), file));
		gist = gistService.createGist(gist);
		return gist.getHtmlUrl();**/
		return "";
	}
}
