package fr.insat.smartbuilding.smartbuilding.controller;

import fr.insat.smartbuilding.smartbuilding.model.ActuatorIot;
import fr.insat.smartbuilding.smartbuilding.model.BrightnessRegulation;
import fr.insat.smartbuilding.smartbuilding.model.History;
import fr.insat.smartbuilding.smartbuilding.model.SensorIot;
import fr.insat.smartbuilding.smartbuilding.model.TemperatureRegulation;
import fr.insat.smartbuilding.smartbuilding.model.VirtualRoom;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class Controller {

	public static Map<String, VirtualRoom> rooms = new HashMap<String, VirtualRoom>();
    public static VirtualRoom outside = new VirtualRoom("Outside",URI.create("http://127.0.0.1:8080"));
    public static Float targetTemperature;
    public static Float targetBrightness;
    public Thread temperatureRegulation ;
    public Thread brightnessRegulation ;
    
    private History history = new History();

    @CrossOrigin
    @PostMapping(path = "/addroom", consumes = "application/json", produces = "application/json")
    public void addRoom(@RequestBody Map<String, Object> payload) {
    	
    	VirtualRoom newTempVirtualRoom = new VirtualRoom(payload.get("name").toString(),URI.create("http://127.0.0.1:8080"));
    	
    	newTempVirtualRoom.addSensor(URI.create("http://127.0.0.1:8080"),"Temperature");
    	newTempVirtualRoom.setSensorValue("Temperature", (float) 25, "Degree");
    	newTempVirtualRoom.addSensor(URI.create("http://127.0.0.1:8080"),"Brightness");
    	newTempVirtualRoom.setSensorValue("Brightness", (float) 25, "Lux");
    	
    	newTempVirtualRoom.addActuator(URI.create("http://127.0.0.1:8080"),"Hvac");
    	newTempVirtualRoom.setActuatorStatus("Hvac","ON");
    	newTempVirtualRoom.addActuator(URI.create("http://127.0.0.1:8080"),"Lamp");
    	newTempVirtualRoom.setActuatorStatus("Lamp","ON");
    	newTempVirtualRoom.addActuator(URI.create("http://127.0.0.1:8080"),"Door");
    	newTempVirtualRoom.setActuatorStatus("Door","ON");
    	newTempVirtualRoom.addActuator(URI.create("http://127.0.0.1:8080"),"Window");
    	newTempVirtualRoom.setActuatorStatus("Window","ON");
    	newTempVirtualRoom.addActuator(URI.create("http://127.0.0.1:8080"),"Shutter");
    	newTempVirtualRoom.setActuatorStatus("Shutter","ON");
    	
    	rooms.put(payload.get("name").toString(),newTempVirtualRoom);
    	System.out.println("Room added : "+newTempVirtualRoom.toString());
    	history.put("Room "+payload.get("name").toString()+" added to the Building");
    }
 
    /*We might need to figure out another way to do this on startup...*/
    @CrossOrigin
    @PutMapping("/outside/{temp}/{brightness}")
    public void instanciateOutside(@PathVariable("temp") Float outsidetemp, @PathVariable("brightness") Float outsidebrightness) {
    	outside.addSensor(URI.create("http://127.0.0.1:8080"),"Temperature");
    	outside.setSensorValue("Temperature", outsidetemp, "Degree");
    	outside.addSensor(URI.create("http://127.0.0.1:8080"),"Brightness");
    	outside.setSensorValue("Brightness", outsidebrightness, "Lux");
    	history.put("Outside environment has been configured with "+outsidetemp.toString()+"°C and "+outsidebrightness.toString()+"Lux");
    }
    
    @CrossOrigin
	@PutMapping("/targettemp/{temp}")
    public void setTargetTemp(@PathVariable("temp") Float targettemp) {
    	targetTemperature = targettemp;
    	System.out.println("Starting Temperature Regulation");
    	if (temperatureRegulation == null) {
    		this.temperatureRegulation = new Thread(new TemperatureRegulation());
        	System.out.println(temperatureRegulation.toString());
        	this.temperatureRegulation.start();
    	} 
    	history.put("Target temperature "+targettemp.toString()+"°C has been configured for the whole building");
    }
    
    @CrossOrigin
	@PutMapping("/targetbrightness/{brightness}")
    public void setTargetBrightness(@PathVariable("brightness") Float targetbrightness) {
    	targetBrightness = targetbrightness;
    	System.out.println("Starting Brightness Regulation");
    	if (brightnessRegulation == null) {
    		this.brightnessRegulation = new Thread(new BrightnessRegulation());
        	System.out.println(brightnessRegulation.toString());
        	this.brightnessRegulation.start();
    	} 
    	history.put("Target Brightness "+targetbrightness.toString()+"Lux has been configured for the whole building");
    }
    
    @CrossOrigin
	@GetMapping("/getrooms/")
	public Map<String, Object> getRooms() {
		
		Map<String, Object> response = new HashMap<String, Object>();
		int i=0;
		for (String room : rooms.keySet()) {
			response.put("room_"+i,room);
			i++;
		}
		history.put("List of all rooms has been retrieved");
		return response;
	}
    
    @CrossOrigin
	@GetMapping("/getroom/{roomname}/actuators")
	public Map<String, Object> getActuators(@PathVariable("roomname") String roomname) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, ActuatorIot> actuators = rooms.get(roomname).getActuatorList();
		int i = 0;
		for (String actuator : actuators.keySet()) {
			response.put("actuator_"+i,actuator);
			i++;
		}
		history.put("List of all actuators for room "+roomname+" has been requested");
		return response;
	}
    
    @CrossOrigin
	@GetMapping("/getroom/{roomname}/sensors")
	public Map<String, Object> getSensors(@PathVariable("roomname") String roomname) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String,SensorIot> sensors = rooms.get(roomname).getSensorList();
		int i = 0;
		for (String sensor : sensors.keySet()) {
			response.put("sensors_"+i,sensor);
			i++;
		}
		history.put("List of all sensors for room "+roomname+" has been requested");
		return response;
	}
    
    @CrossOrigin
	@GetMapping("/getroom/{roomname}/readsensor/{sensor}")
	public Map<String, Object> getSensorValue(@PathVariable("roomname") String roomname, @PathVariable("sensor") String sensor) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		float value = rooms.get(roomname).readSensorValue(sensor);
		response.put(sensor,value);
		history.put("Sensor "+sensor+" in "+roomname+" has been read");
		return response;
	}
	
    
    @CrossOrigin
	@GetMapping("/getroom/{roomname}/readactuator/{actuator}")
	public Map<String, Object> getActuatorValue(@PathVariable("roomname") String roomname, @PathVariable("actuator") String actuator) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		String value = rooms.get(roomname).readActuatorStatus(actuator);
		response.put(actuator,value);
		history.put("Actuator "+actuator+" in "+roomname+" has been read");
		return response;
	}
    
    @CrossOrigin
	@PutMapping("/getroom/{roomname}/setactuator/{actuator}/{value}")
	public void setActuatorValue(@PathVariable("roomname") String roomname, @PathVariable("actuator") String actuator, @PathVariable("value") String value) {
		System.out.println("Boolean : "+value);
		rooms.get(roomname).setActuatorStatus(actuator, value);
		history.put("Actuator "+actuator+" in "+roomname+" has been set to"+value.toString());
	}
    
    @CrossOrigin
    @PutMapping("/lockbuilding/")
    public void lockBuilding() {
    	for (VirtualRoom room : rooms.values()) {
    		room.setActuatorStatus("Door","OFF");
    		room.setActuatorStatus("Window","OFF");
    		room.setActuatorStatus("Hvac","OFF");
    	}
    	history.put("The building has been locked, Windows, Doors are closed and Hvac is off");
    	/*stop threads as well*/
    }
    
    @CrossOrigin
    @PutMapping("/unlockbuilding/")
    public void unlockBuilding() {
    	for (VirtualRoom room : rooms.values()) {
    		room.setActuatorStatus("Door","ON");
    	}
    	history.put("The building has been unlocked, Doors are open");
    }
    
    @CrossOrigin
    @GetMapping("/logs/")
	public History getLogs() {
		
		history.put("All logs have been retrieved");
		
		return history;
	}
}


