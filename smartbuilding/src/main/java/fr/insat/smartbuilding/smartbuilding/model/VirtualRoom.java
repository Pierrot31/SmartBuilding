package fr.insat.smartbuilding.smartbuilding.model;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class VirtualRoom {

	private String name;
	private URI gateway;
	
	private Map<String,SensorIot> sensoriots = new HashMap<String,SensorIot>();;
	private Map<String,ActuatorIot> actuatoriots = new HashMap<String,ActuatorIot>();
	
	public VirtualRoom(String name, URI gateway){
		
		this.name = name;
		this.gateway = gateway;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache());
		headers.add("content-type","application/xml;ty=2");
		headers.add("Accept","application/xml");
		headers.add("x-m2m-origin","admin:admin");
		
		String xmlPayload="<m2m:ae xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\""+name+"\">\n" +
		"<api>ROOM</api>\n" +
		"<lbl>Type/room Category/room location/"+name+"</lbl>\n" +
		"<rr>false</rr>\n" + 
		"</m2m:ae>";
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<>(xmlPayload,headers);
		restTemplate.postForEntity(this.gateway.toString()+"~/in-cse",request,String.class);
		
	}
	
	public void addActuator(URI gateway,String actuatorname) {
		
		ActuatorIot newActuator = new ActuatorIot(gateway,this.name,actuatorname);
		
		this.actuatoriots.put(actuatorname, newActuator);
	}
	
	public String readActuatorStatus(String actuatorname) {
		return actuatoriots.get(actuatorname).getStatus();
	}
	
	public void setActuatorStatus(String actuatorname, String status) {
		actuatoriots.get(actuatorname).setStatus(status);
	}
	
	public void addSensor(URI gateway,String sensorname) {
		
		SensorIot newSensor = new SensorIot(gateway,this.name,sensorname);
		
		this.sensoriots.put(sensorname, newSensor);
	}
	
	public Float readSensorValue(String sensorname) {
		return sensoriots.get(sensorname).getValue();
	}
	
	public void setSensorValue(String sensorname, Float value, String unit) {
		sensoriots.get(sensorname).setValue(value,unit);
	}
	
	/*
	public void addSensorProgram(String sensorname, float[] program) {
		sensoriots.get(sensorname).addProgram(program);
	}
	
	public void setSensorProgram(String sensorname, Boolean status) {
		sensoriots.get(sensorname).setProgram(status);
	}*/
	
	public String getRoomName() {
		return this.name;
	}
	
	public Map<String,SensorIot> getSensorList() {
		return this.sensoriots;
	}
	
	public Map<String,ActuatorIot> getActuatorList() {
		return this.actuatoriots;
	}
}
