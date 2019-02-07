package co.com.ceiba.ceibaadn.dto;

import java.io.Serializable;

public class VehicleDTO implements Serializable{


	private static final long serialVersionUID = 1L;

	private int idDTO;

	private String licenseDTO;

	private String cylinderDTO;

	private int typeVehicleDTO;

	
	
	public VehicleDTO() {
		
		super();

	}

	public VehicleDTO(int idDTO, String licenseDTO, String cylinderDTO, int typeVehicleDTO) {
		super();
		this.idDTO = idDTO;
		this.licenseDTO = licenseDTO;
		this.cylinderDTO = cylinderDTO;
		this.typeVehicleDTO = typeVehicleDTO;
	}

	public int getIdDTO() {
		return idDTO;
	}

	public String getLicenseDTO() {
		return licenseDTO;
	}

	public String getCylinderDTO() {
		return cylinderDTO;
	}

	public int getTypeVehicleDTO() {
		return typeVehicleDTO;
	}

	public void setIdDTO(int idDTO) {
		this.idDTO = idDTO;
	}

	public void setLicenseDTO(String licenseDTO) {
		this.licenseDTO = licenseDTO;
	}

	public void setCylinderDTO(String cylinderDTO) {
		this.cylinderDTO = cylinderDTO;
	}

	public void setTypeVehicleDTO(int typeVehicleDTO) {
		this.typeVehicleDTO = typeVehicleDTO;
	}


	

}
