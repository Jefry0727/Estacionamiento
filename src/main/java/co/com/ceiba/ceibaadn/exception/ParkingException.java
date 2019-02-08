package co.com.ceiba.ceibaadn.exception;

public class ParkingException extends Exception{


	private static final long serialVersionUID = 1L;
	
	private String message;


	public ParkingException(String message) {
		super(message);

	}

	@Override
	public String getMessage() {
		return message;
	}


	
	
	
	

}
