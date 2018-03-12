package com.vv.exception;

public class ResourceNotFoundException extends Exception {
	private static final long serialVersionUID = -7013656108858135854L;

	public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
