package edu.douglaslima.entrycontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "O token é nulo ou vazio!")
public class TokenNuloException extends RuntimeException {

	public TokenNuloException() {
		
	}
	
	public TokenNuloException(String errorMessage) {
		super(errorMessage);
	}
	
	public TokenNuloException(String errorMessage, Object... params) {
		super(String.format(errorMessage, params));
	}

	public TokenNuloException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}

	public TokenNuloException(String errorMessage, Throwable cause, Object... params) {
		super(String.format(errorMessage, params), cause);
	}
	
}
