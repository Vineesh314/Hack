package com.hack.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import twitter4j.JSONException;

import com.hack.java.TwitterOperations;
import com.hack.utils.Responses;

@Component
@Path("/twitter")
public class TwitterRest {

	@Autowired
	private TwitterOperations twitterOperations;

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

		Responses response = new Responses();
		response.setMessage("Success");
		response.setStatus("200");

		try {
			System.out.println(twitterOperations
					.getJSONwithScreenName(screenName));
			response.setResults(twitterOperations
					.getJSONwithScreenName(screenName));
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

		Responses response = new Responses();
		response.setMessage("Success");
		response.setStatus("200");

		try {
			System.out.println(twitterOperations.getJSONwithId(userId));
			response.setResults(twitterOperations.getJSONwithId(userId));
		} catch (JSONException e) {
			System.out.println(e.getLocalizedMessage());
		}

		return response;

	}
}
