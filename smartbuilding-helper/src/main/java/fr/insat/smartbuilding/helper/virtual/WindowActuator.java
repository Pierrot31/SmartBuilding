package fr.insat.smartbuilding.helper.virtual;

import java.util.UUID;

public class WindowActuator extends VirtualActuator {
	
	private UUID id;
	private String location;
	private Boolean status;
	
	public WindowActuator(String location, Boolean status){
		super(location,status);
	}
	
}
