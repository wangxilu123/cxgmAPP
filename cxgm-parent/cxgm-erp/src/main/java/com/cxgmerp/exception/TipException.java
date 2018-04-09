package com.cxgmerp.exception;

public class TipException extends RuntimeException {

	private static final long serialVersionUID = 2393342895988580955L;

	public TipException() {
    }

    public TipException(String message) {
        super(message);
    }

    public TipException(String message, Throwable cause) {
        super(message, cause);
    }

    public TipException(Throwable cause) {
        super(cause);
    }

}
