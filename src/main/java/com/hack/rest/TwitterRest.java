package com.hack.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import twitter4j.JSONException;

import com.hack.java.twittersearch2;
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
		twittersearch2 ts = new twittersearch2();

		Responses response = new Responses();
		response.setMessage("Success");
		response.setStatus("200");

		try {
			System.out.println(ts.getJSONwithScreenName(screenName));
			response.setResults(ts.getJSONwithScreenName(screenName));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}

	@GET
	@Path("getDetailsId")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getDetailsId(@QueryParam("authToken") final String authToken,
			@QueryParam("authTokenSecret") final String authTokenSecret,
			@QueryParam("userId") final String userId) throws IOException {
		twittersearch2 ts = new twittersearch2();

		Responses response = new Responses();
		response.setMessage("Success");
		response.setStatus("200");

		try {
			System.out.println(ts.getJSONwithId(userId));
			response.setResults(ts.getJSONwithId(userId));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}
}
