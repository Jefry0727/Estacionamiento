package co.com.ceiba.ceibaadn.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import co.com.ceiba.ceibaadn.exception.ParkingException;



@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler{
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@org.springframework.web.bind.annotation.ExceptionHandler({ParkingException.class})
	@ResponseBody
	public ErrorMessage parkingException(Exception exception) {
		
		ErrorMessage erroMessage = new ErrorMessage(exception);
		LogManager.getLogger(this.getClass()).info("ERROR: BAD_REQUEST, " + erroMessage);
		
		return erroMessage;
		
		
	}

}
