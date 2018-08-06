package com.trainings.messagesource;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class HelloWorld {
	private String greeting;
	private String message;
	
	//you can have message resource entry made in .xml file and you can do auto wire or do below
	@Autowired
	private MessageSource messageSource;
	
	

	public String getGreeting() {
		return greeting;
	}

	@Override
	public String toString() {
		return "HelloWorld [greeting=" + greeting + ", message=" + message + "]";
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void sayHelloWorld() {
		System.out.println(messageSource.getMessage("helloworld.greet",new Object[] {greeting,message},null));
	}
	public void sayHelloWorld_fr() {
		System.out.println(messageSource.getMessage("helloworld.greet",new Object[] {greeting,message},new Locale("FR")));
		
	}

}
