package com.ebis.checkApp.exception.domain;

public class InsufficientCreditsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InsufficientCreditsException(String message) {
        super(message);
    }
}