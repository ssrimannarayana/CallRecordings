package com.kgl.KglServices.exceptions;

public class InvalidPhoneNumber extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPhoneNumber(String errmsg) {
		super(errmsg);
	}
	
}
