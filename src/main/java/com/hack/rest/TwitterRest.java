package com.hack.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import twitter4j.JSONException;

import com.hack.java.TwitterOperations;
import com.hack.utils.Responses;

@Component
@Path("/twitter")
public class TwitterRest {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object noAction() {
		return "No Action specified";
	}

	@GET
	@Path("sendAuthDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getAuthDetails(
			@QueryParam("authToken") final String authToken,
			@QueryParam("authTokenSecret") final String authTokenSecret,
			@QueryParam("screenName") final String screenName)
			throws IOException {
		TwitterOperations tweet = new TwitterOperations();

		Responses response = new Responses();
		response.setMessage("Success");
		response.setStatus("200");

		try {
			System.out.println(tweet.getJSONwithScreenName(screenName));
			response.setResults(tweet.getJSONwithScreenName(screenName));
		} catch (JSONException e) {
			System.out.println(e.getLocalizedMessage());
		}

		return response;

	}

	@GET
	@Path("getDetailsId")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getDetailsId(@QueryParam("authToken") final String authToken,
			@QueryParam("authTokenSecret") final String authTokenSecret,
			@QueryParam("userId") final String userId) throws IOException {
		TwitterOperations tweet = new TwitterOperations();

		Responses response = new Responses();
		response.setMessage("Success");
		response.setStatus("200");

		try {
			System.out.println(tweet.getJSONwithId(userId));
			response.setResults(tweet.getJSONwithId(userId));
		} catch (JSONException e) {
			System.out.println(e.getLocalizedMessage());
		}

		return response;

	}
}
