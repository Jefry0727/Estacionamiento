package co.com.ceiba.ceibaadn.dto;

import java.io.Serializable;

public class RestResponsePaymentDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String message;
	
	private PaymentDTO paymentDTO;
	
	
	
	public RestResponsePaymentDTO() {
		


	}

	public RestResponsePaymentDTO(String message, PaymentDTO paymentDTO) {

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
