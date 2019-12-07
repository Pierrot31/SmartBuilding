package fr.insat.smartbuilding.smartbuilding.model;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class VirtualRoom {

	private String name;
	
	private Map<String,SensorIot> sensoriots = new HashMap<String,SensorIot>();;
	private Map<String,ActuatorIot> actuatoriots = new HashMap<String,ActuatorIot>();
	
	public VirtualRoom(String name){
		this.name = name;
	}
	
	public void addActuator(URI gateway,String actuatorname,Boolean status) {
		
		ActuatorIot newActuator = new ActuatorIot(gateway,this.name+"_"+actuatorname,status);
		
		this.actuatoriots.put(actuatorname, newActuator);
	}
	
	public Boolean readActuatorStatus(String actuatorname) {
		return actuatoriots.get(actuatorname).getStatus();
	}
	
	public void setActuatorStatus(String actuatorname, Boolean status) {
		actuatoriots.get(actuatorname).setStatus(status);
	}
	
	
}
