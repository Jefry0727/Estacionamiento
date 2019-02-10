package co.com.ceiba.ceibaadn.dto;

public class RestResponsePaymentDTO {
	
	private String message;
	
	private PaymentDTO paymentDTO;
	
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
