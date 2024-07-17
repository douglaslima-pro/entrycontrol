package edu.douglaslima.entrycontrol.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	public HttpHeaders defaultHeader() {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		return header;
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
		ResponseError responseBody = new ResponseError(HttpStatus.BAD_REQUEST.value(), "error-argumentos-invalidos", e.getMessage());
		String path = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.toUriString();
		responseBody.setPath(path);
		return handleExceptionInternal(e, responseBody, defaultHeader(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException e, WebRequest request) {
		ResponseError responseBody = new ResponseError(HttpStatus.NOT_FOUND.value(), "error-objeto-nao-encontrado", e.getMessage());
		String path = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.toUriString();
		responseBody.setPath(path);
		return handleExceptionInternal(e, responseBody, defaultHeader(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException e, WebRequest request) {
		ResponseError responseBody = new ResponseError(HttpStatus.BAD_REQUEST.value(), "error-credenciais-incorretas", e.getMessage());
		String path = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.toUriString();
		responseBody.setPath(path);
		return handleExceptionInternal(e, responseBody, defaultHeader(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException e, WebRequest request) {
		ResponseError responseBody = new ResponseError(HttpStatus.NOT_FOUND.value(), "error-objeto-nao-encontrado", e.getMessage());
		String path = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.toUriString();
		responseBody.setPath(path);
		String detail = "Verifique se o nome de usuário está correto.";
		responseBody.setDetail(detail);
		return handleExceptionInternal(e, responseBody, defaultHeader(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException e, WebRequest request) {
		ResponseError responseBody = new ResponseError(HttpStatus.BAD_REQUEST.value(), "error-token-expirado", e.getMessage());
		String path = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.toUriString();
		responseBody.setPath(path);
		return handleExceptionInternal(e, responseBody, defaultHeader(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(TokenInvalidoException.class)
	public ResponseEntity<Object> handleTokenInvalidoException(TokenInvalidoException e, WebRequest request) {
		ResponseError responseBody = new ResponseError(HttpStatus.BAD_REQUEST.value(), "error-token-invalido", e.getMessage());
		String path = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.toUriString();
		responseBody.setPath(path);
		return handleExceptionInternal(e, responseBody, defaultHeader(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(TokenNuloException.class)
	public ResponseEntity<Object> handleTokenNuloException(TokenNuloException e, WebRequest request) {
		ResponseError responseBody = new ResponseError(HttpStatus.BAD_REQUEST.value(), "error-token-nulo", e.getMessage());
		String path = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.toUriString();
		responseBody.setPath(path);
		return handleExceptionInternal(e, responseBody, defaultHeader(), HttpStatus.BAD_REQUEST, request);
	}
	
}
