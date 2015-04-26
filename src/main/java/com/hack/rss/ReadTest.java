/**
 * 
 */
package com.hack.rss;

/**
 * @author Vineesh
 * 
 */

public class ReadTest {
	public static void main(String[] args) {
		RSSFeedParser parser = new RSSFeedParser(
				"http://cloud.feedly.com/v3/search/feeds?query=kochi");
		Feed feed = parser.readFeed();
		System.out.println(feed);
		for (FeedMessage message : feed.getMessages()) {
			System.out.println(message);

		}

	}
}
