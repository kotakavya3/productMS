package com.infy.order.utility;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.infy.order.exception.OrderMsException;

@RestControllerAdvice
public class ExceptionControllerAdvice {
	
	@Autowired
	Environment environment;

	@ExceptionHandler(OrderMsException.class)
	public ResponseEntity<ErrorInfo> orderMsExceptionHandler(OrderMsException exception) {
		ErrorInfo error = new ErrorInfo();
		error.setErrorMessage(environment.getProperty(exception.getMessage()));
		error.setTimestamp(LocalDateTime.now());
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ErrorInfo>(error, HttpStatus.NOT_FOUND);
	}
	
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ErrorInfo> exceptionHandler(Exception exception) {
//		ErrorInfo error = new ErrorInfo();
//		error.setErrorMessage(environment.getProperty("General.EXCEPTION_MESSAGE"));
//		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//		error.setTimestamp(LocalDateTime.now());
//		return new ResponseEntity<ErrorInfo>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
	
}
