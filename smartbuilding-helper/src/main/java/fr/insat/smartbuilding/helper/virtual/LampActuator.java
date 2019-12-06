package fr.insat.smartbuilding.helper.virtual;

import java.util.UUID;

public class LampActuator extends VirtualActuator {

	private UUID id;
	private String location;
	private Boolean status;
	
	public LampActuator(String location, Boolean status){
		super(location,status);
	}
}
