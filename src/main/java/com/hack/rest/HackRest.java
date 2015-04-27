package com.hack.rest;

import java.sql.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.hack.utils.Employee;

@Component
@Path("/hack")
public class HackRest {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object noAction() {
		return "No Action specified";
	}

	@GET
	@Path("getname")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getName() {
		@SuppressWarnings({ "deprecation" })
		Employee employee = new Employee(1, "Lokesh", "Gupta", new Date(1981,
				8, 18));

		return employee;

	}

	@POST
	@Path("setName")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Object setName(Employee employee) {

		return employee;

	}

}
