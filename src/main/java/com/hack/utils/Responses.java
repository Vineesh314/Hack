package com.hack.utils;


public class Responses {

	String status;
	String message;
	Object results;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the results
	 */
	public Object getResults() {
		return results;
	}

	/**
	 * @param object
	 *            the results to set
	 */
	public void setResults(Object object) {
		this.results = object;
	}
}
