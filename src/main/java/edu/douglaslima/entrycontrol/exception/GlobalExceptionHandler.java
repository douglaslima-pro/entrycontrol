package edu.douglaslima.entrycontrol.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Object> handleNullPointerException(NullPointerException e, WebRequest request) {
		ResponseError responseBody = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error-null-pointer-exception", e.getMessage());
		String path = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.toUriString();
		responseBody.setPath(path);
		responseBody.setDetail("Entrar em contato com o time de desenvolvimento da API e informar o código do erro.");
		return handleExceptionInternal(e, responseBody, defaultHeader(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException e, WebRequest request) {
		ResponseError responseBody = new ResponseError(HttpStatus.UNAUTHORIZED.value(), "error-credenciais-incorretas", "Nome de usuário ou senha incorretos!");
		String path = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.toUriString();
		responseBody.setPath(path);
		responseBody.setDetail("Solicitar a redefinição da senha caso tenha esquecido.");
		return handleExceptionInternal(e, responseBody, defaultHeader(), HttpStatus.UNAUTHORIZED, request);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException e, WebRequest request) {
		ResponseError responseBody = new ResponseError(HttpStatus.NOT_FOUND.value(), "error-usuario-nao-encontrado", e.getMessage());
		String path = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.toUriString();
		responseBody.setPath(path);
		String detail = "Verifique se o nome de usuário está correto.";
		responseBody.setDetail(detail);
		return handleExceptionInternal(e, responseBody, defaultHeader(), HttpStatus.NOT_FOUND, request);
	}
	
}
