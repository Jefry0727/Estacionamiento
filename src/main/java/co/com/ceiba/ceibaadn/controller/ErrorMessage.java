package co.com.ceiba.ceibaadn.controller;

public class ErrorMessage {

	private String error;

	private String description;

	public ErrorMessage(Exception exception) {

		this(exception.getClass().getSimpleName(), exception.getMessage());

	}

	public ErrorMessage(String error, String description) {
		super();
		this.error = error;
		this.description = description;
	}

	public String getError() {
		return error;
	}

	public String getDescription() {
		return description;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ErrorMessage [Error_: " + error + ", Descripción: " + description + "]";

	}

}
