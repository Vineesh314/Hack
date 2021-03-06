package com.hack.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import twitter4j.JSONException;
import twitter4j.JSONObject;

import com.sun.xml.internal.messaging.saaj.util.Base64;

public class TwitterOperations {

	/*
	 * private final static String getTokenURL =
	 * "https://api.twitter.com/oauth2/token"; private static String
	 * bearerToken; static final String ACCESS_TOKEN =
	 * "3013300573-DxiN7arVqrio38ICoHTJJ9sKujEA2b8AHLNx1yG"; static final String
	 * ACCESS_SECRET = "gZwbQfXeHHocYaenASQqgQIJcaORpd5Wu5zGZ0HK7iU51"; static
	 * final String CONSUMER_KEY = "9iXXUZRYTbY47tAnUAM0r9tth"; static final
	 * String CONSUMER_SECRET =
	 * "xZKJ9L0CyZfDCfa1PibRmxjsugGvqYi2UMwLm4RJowXl7m1sC2";
	 */

	private static String bearerToken;

	private String twitterBaseUrl;
	private String twitterAccessToken;
	private String twitterAccessSecret;
	private static String twitterConsumerKey;
	private static String twitterConsumerSecret;
	private static String twitterTokenUrl;

	/**
	 * @param twitterBaseUrl
	 *            the twitterBaseUrl to set
	 */
	public void setTwitterBaseUrl(String twitterBaseUrl) {
		this.twitterBaseUrl = twitterBaseUrl;
	}

	/**
	 * @param twitterAccessToken
	 *            the twitterAccessToken to set
	 */
	public void setTwitterAccessToken(String twitterAccessToken) {
		this.twitterAccessToken = twitterAccessToken;
	}

	/**
	 * @param twitterAccessSecret
	 *            the twitterAccessSecret to set
	 */
	public void setTwitterAccessSecret(String twitterAccessSecret) {
		this.twitterAccessSecret = twitterAccessSecret;
	}

	/**
	 * @param twitterConsumerKey
	 *            the twitterConsumerKey to set
	 */
	public void setTwitterConsumerKey(String twitterConsumerKey) {
		this.twitterConsumerKey = twitterConsumerKey;
	}

	/**
	 * @param twitterConsumerSecret
	 *            the twitterConsumerSecret to set
	 */
	public void setTwitterConsumerSecret(String twitterConsumerSecret) {
		this.twitterConsumerSecret = twitterConsumerSecret;
	}

	/**
	 * @param twitterTokenUrl
	 *            the twitterTokenUrl to set
	 */
	public void setTwitterTokenUrl(String twitterTokenUrl) {
		this.twitterTokenUrl = twitterTokenUrl;
	}

	public static void main(String[] args) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					bearerToken = requestBearerToken(twitterTokenUrl);
					searchTweets("https://api.twitter.com/1.1/search/tweets.json?count=100&q=greenfield+stadium&result_type=recent");

				} catch (IOException e) {
					System.out.println("IOException e");
					e.printStackTrace();
				}
			}
		}).start();

	}

	private static String encodeKeys(String consumerKey, String consumerSecret) {
		try {
			String encodedConsumerKey = URLEncoder.encode(consumerKey, "UTF-8");
			String encodedConsumerSecret = URLEncoder.encode(consumerSecret,
					"UTF-8");

			String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;
			byte[] encodedBytes = Base64.encode(fullKey.getBytes());// encodeBase64(fullKey.getBytes());

			return new String(encodedBytes);
		} catch (UnsupportedEncodingException e) {
			return new String();
		}
	}

	private static String requestBearerToken(String endPointUrl)
			throws IOException {
		HttpsURLConnection connection = null;

		String encodedCredentials = encodeKeys(twitterConsumerKey,
				twitterConsumerSecret);

		try {
			URL url = new URL(endPointUrl);
			connection = (HttpsURLConnection) url.openConnection();
			// System.out.println(connection);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Host", "api.twitter.com");
			connection.setRequestProperty("User-Agent", "MwTestTwitterAPI");
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedCredentials);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			connection.setRequestProperty("Content-Length", "29");
			connection.setUseCaches(false);

			writeRequest(connection, "grant_type=client_credentials");
			// System.out.println(connection.getResponseCode());
			// System.out.println(connection.getResponseMessage());

			String result = readResponse(connection);

			JSONObject jsonResult = new JSONObject(result);

			if (jsonResult.get("token_type") != null
					&& jsonResult.get("token_type").equals("bearer")) {
				return jsonResult.getString("access_token");
			}
			return new String();
		} catch (MalformedURLException | JSONException e) {
			throw new IOException("Invalid endpoint URL specified.", e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	private static String searchTweets(String endPointUrl) throws IOException {
		HttpsURLConnection connection = null;

		try {
			URL url = new URL(endPointUrl);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Host", "api.twitter.com");
			connection.setRequestProperty("User-Agent", "MwTestTwitterAPI");
			connection.setRequestProperty("Authorization", "Bearer "
					+ bearerToken);

			connection.setUseCaches(false);

			String result = readResponse(connection);
			System.out.println(result);

			if (result != null || result.equals("")) {
				return result;
			}
			return new String();
		} catch (MalformedURLException e) {
			throw new IOException("Invalid endpoint URL specified.", e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	// Writes a request to a connection
	private static boolean writeRequest(HttpURLConnection connection,
			String textBody) {
		try {
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(
					connection.getOutputStream()));
			wr.write(textBody);
			wr.flush();
			wr.close();

			return true;
		} catch (IOException e) {
			return false;
		}
	}

	// Reads a response for a given connection and returns it as a string.
	private static String readResponse(HttpURLConnection connection) {
		try {
			StringBuilder str = new StringBuilder();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line = "";
			while ((line = br.readLine()) != null) {
				str.append(line + System.getProperty("line.separator"));
			}
			return str.toString();
		} catch (IOException e) {
			return new String();
		}
	}

	public Object getJSONwithScreenName(String screenName) throws IOException,
			JSONException {

		System.out.println("=============here====================="
				+ twitterTokenUrl);
		bearerToken = requestBearerToken(twitterTokenUrl);
		System.out
				.println("https://api.twitter.com/1.1/search/tweets.json?count=100&q="
						+ screenName + "&result_type=recent");
		Object jsonStr = searchTweets("https://api.twitter.com/1.1/search/tweets.json?count=100&q="
				+ URLEncoder.encode(screenName) + "&result_type=recent");
		System.out.println(jsonStr);

		return jsonStr;

	}

	public Object getJSONwithId(String userId) throws IOException,
			JSONException {

		bearerToken = requestBearerToken(twitterTokenUrl);
		String jsonStr = searchTweets("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name="
				+ userId + "&count=100");

		jsonStr = ("{\"statuses\":") + jsonStr + "}";

		return jsonStr;

	}
}