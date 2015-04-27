package com.hack.utils;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JSONToJava {

	public static void main(String[] args) {
		JSONToJava j = new JSONToJava();
		System.out.println(j.getJava());
	}

	public Object getJava() {
		Employee employee = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			employee = mapper.readValue(new File("E://employee.json"),
					Employee.class);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(employee);
		return employee;
	}
}
