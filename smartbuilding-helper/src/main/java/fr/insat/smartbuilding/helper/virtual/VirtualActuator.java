package fr.insat.smartbuilding.helper.virtual;

import java.util.UUID;

public abstract class VirtualActuator {
	
	private UUID id;
	private String location;
	private Boolean status;
	
	public VirtualActuator(String location, Boolean status){
		this.id = UUID.randomUUID();
		this.location = location;
		this.status = status;
	}
	
	public UUID getId(){
		return this.id;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public Boolean getStatus() {
		return this.status;
	}
	
}
