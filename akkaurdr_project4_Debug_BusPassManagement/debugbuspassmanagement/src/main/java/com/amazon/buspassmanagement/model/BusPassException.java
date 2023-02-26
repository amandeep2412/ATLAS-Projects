package com.amazon.buspassmanagement.model;

@SuppressWarnings("serial")
public class BusPassException extends Exception {

	public BusPassException() {
		System.err.println("Duplicate Buspass");
	}
}
