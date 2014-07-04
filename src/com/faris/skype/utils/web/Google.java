package com.faris.skype.utils.web;

import com.faris.skype.utils.WebClient;
import com.google.gson.Gson;

/**
 * @author Double0negative
 */
public class Google {

	public static GoogleResult search(String type, String search, int start) throws Exception {
		String url = "http://ajax.googleapis.com/ajax/services/search/" + type + "?v=2.0&safe=off&q=" + search + "&start=" + start;
		String json = WebClient.request(url);

		return new Gson().fromJson(json, GoogleResult.class);

	}

	public class GoogleResult {
		public ResponseData responseData;
	}

	public class ResponseData {
		public Results[] results;
	}

	public class Results {
		public String cacheUrl;
		public String content;
		public String title;
		public String titleNoFormatting;
		public String unescapedUrl;
		public String url;
		public String visibleUrl;
	}

}