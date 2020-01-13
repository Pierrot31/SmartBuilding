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
	
	
	public void addSensor(URI gateway,String sensorname,Float range) {
		
		SensorIot newSensor = new SensorIot(gateway,this.name+"_"+sensorname,range);
		
		this.sensoriots.put(sensorname, newSensor);
	}
	
	public Float readSensorValue(String sensorname) {
		return sensoriots.get(sensorname).getValue();
	}
	
	public void setSensorValue(String sensorname, Float value) {
		sensoriots.get(sensorname).setValue(value);
	}
	
	public void addSensorProgram(String sensorname, float[] program) {
		sensoriots.get(sensorname).addProgram(program);
	}
	
	public void setSensorProgram(String sensorname, Boolean status) {
		sensoriots.get(sensorname).setProgram(status);
	}
}
