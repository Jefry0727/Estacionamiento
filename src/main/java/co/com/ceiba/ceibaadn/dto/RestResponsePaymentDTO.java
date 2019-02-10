package co.com.ceiba.ceibaadn.dto;

import java.io.Serializable;

public class RestResponsePaymentDTO implements Serializable{
	
	private String message;
	
	private PaymentDTO paymentDTO;
	
	
	
	public RestResponsePaymentDTO() {
		
		super();

	}

	public RestResponsePaymentDTO(String message, PaymentDTO paymentDTO) {
		super();
		this.message = message;
		this.paymentDTO = paymentDTO;
	}

	public String getMessage() {
		return message;
	}

	public PaymentDTO getPaymentDTO() {
		return paymentDTO;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setPaymentDTO(PaymentDTO paymentDTO) {
		this.paymentDTO = paymentDTO;
	}
	
	
}
