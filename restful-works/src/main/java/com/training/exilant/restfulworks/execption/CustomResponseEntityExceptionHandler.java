package com.training.exilant.restfulworks.execption;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	//we want to have method which can handle all types of exception
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllException(Exception ex,WebRequest req){
		
		ErrorDetails errordetails= new ErrorDetails(new Date(),
				ex.getMessage(),req.getDescription(true));
		
		return new ResponseEntity<Object>(errordetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(Exception ex,WebRequest req){
		
		ErrorDetails errordetails= new ErrorDetails(new Date(),
				ex.getMessage(),"sorry user not found in db");
		
		return new ResponseEntity<Object>(errordetails,HttpStatus.NOT_FOUND);
	}

}
