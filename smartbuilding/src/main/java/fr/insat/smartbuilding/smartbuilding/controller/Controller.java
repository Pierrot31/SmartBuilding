package fr.insat.smartbuilding.smartbuilding.controller;

import fr.insat.smartbuilding.smartbuilding.model.ActuatorIot;
import fr.insat.smartbuilding.smartbuilding.model.BrightnessRegulation;
import fr.insat.smartbuilding.smartbuilding.model.SensorIot;
import fr.insat.smartbuilding.smartbuilding.model.TemperatureRegulation;
import fr.insat.smartbuilding.smartbuilding.model.VirtualRoom;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class Controller {
/*
 * Temperature : http://127.0.0.1:8001
 * Hvac : http://127.0.0.1:8002
 * Brightness : http://127.0.0.1:8003
 * Lamp : http://127.0.0.1:8004
 * Door : http://127.0.0.1:8005
 * Window : http://127.0.0.1:8006
 * Shutters : http://127.0.0.1:8007
 */

	public static Map<String, VirtualRoom> rooms = new HashMap<String, VirtualRoom>();
    public static VirtualRoom outside = new VirtualRoom("Outside");
    public static Float targetTemperature;
    public static Float targetBrightness;
    public Thread temperatureRegulation ;
    public Thread brightnessRegulation ;



    @PostMapping(path = "/addroom", consumes = "application/json", produces = "application/json")
    public void addRoom(@RequestBody Map<String, Object> payload) {
    	VirtualRoom newTempVirtualRoom = new VirtualRoom(payload.get("name").toString());
    	
    	newTempVirtualRoom.addSensor(URI.create("http://127.0.0.1:8001"),"Temperature",(float) 30.0);
    	newTempVirtualRoom.addActuator(URI.create("http://127.0.0.1:8002"),"Hvac",false);
    	newTempVirtualRoom.addSensor(URI.create("http://127.0.0.1:8003"),"Brightness",(float) 500.0);
    	newTempVirtualRoom.addActuator(URI.create("http://127.0.0.1:8004"),"Lamp",false);
    	newTempVirtualRoom.addActuator(URI.create("http://127.0.0.1:8005"),"Door",false);
    	newTempVirtualRoom.addActuator(URI.create("http://127.0.0.1:8006"),"Window",false);
    	newTempVirtualRoom.addActuator(URI.create("http://127.0.0.1:8007"),"Shutters",false);
    	rooms.put(payload.get("name").toString(),newTempVirtualRoom);
    	System.out.println("Room added : "+newTempVirtualRoom.toString());
    }
 
    /*We might need to figure out another way to do this on startup...*/
    @PutMapping("/outside/{temp}/{brightness}")
    public void instanciateOutside(@PathVariable("temp") Float outsidetemp, @PathVariable("brightness") Float outsidebrightness) {
    	outside.addSensor(URI.create("http://127.0.0.1:8001"),"Temperature",outsidetemp);
    	outside.addSensor(URI.create("http://127.0.0.1:8003"),"Brightness",outsidebrightness);

    }
    
	@PutMapping("/targettemp/{temp}")
    public void setTargetTemp(@PathVariable("temp") Float targettemp) {
    	targetTemperature = targettemp;
    	System.out.println("Starting Temperature Regulation");
    	if (temperatureRegulation == null) {
    		this.temperatureRegulation = new Thread(new TemperatureRegulation());
        	System.out.println(temperatureRegulation.toString());
        	this.temperatureRegulation.start();
    	} 
    }
    
	@PutMapping("/targetbrightness/{brightness}")
    public void setTargetBrightness(@PathVariable("brightness") Float targetbrightness) {
    	targetBrightness = targetbrightness;
    	System.out.println("Starting Brightness Regulation");
    	if (brightnessRegulation == null) {
    		this.brightnessRegulation = new Thread(new BrightnessRegulation());
        	System.out.println(brightnessRegulation.toString());
        	this.brightnessRegulation.start();
    	} 
    }
	
	@GetMapping("/getrooms/")
	public Map<String, Object> getRooms() {
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		for (String room : rooms.keySet()) {
			response.put("room",room);
		}
		return response;
	}
	
	@GetMapping("/getroom/{roomname}/actuators")
	public Map<String, Object> getActuators(@PathVariable("roomname") String roomname) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, ActuatorIot> actuators = rooms.get(roomname).getActuatorList();
		int i = 0;
		for (String actuator : actuators.keySet()) {
			response.put("actuator_"+i,actuator);
			i++;
		}
		return response;
	}
	
	@GetMapping("/getroom/{roomname}/sensors")
	public Map<String, Object> getSensors(@PathVariable("roomname") String roomname) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String,SensorIot> sensors = rooms.get(roomname).getSensorList();
		int i = 0;
		for (String sensor : sensors.keySet()) {
			response.put("sensors_"+i,sensor);
			i++;
		}
		return response;
	}
	
	@GetMapping("/getroom/{roomname}/readsensor/{sensor}")
	public Map<String, Object> getSensorValue(@PathVariable("roomname") String roomname, @PathVariable("sensor") String sensor) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		float value = rooms.get(roomname).readSensorValue(sensor);
		response.put(sensor,value);
		return response;
	}
	
	@GetMapping("/getroom/{roomname}/readactuator/{actuator}")
	public Map<String, Object> getActuatorValue(@PathVariable("roomname") String roomname, @PathVariable("actuator") String actuator) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		Boolean value = rooms.get(roomname).readActuatorStatus(actuator);
		response.put(actuator,value);
		return response;
	}

    @PutMapping("/lockbuilding/")
    public void lockBuilding() {
    	for (VirtualRoom room : rooms.values()) {
    		room.setActuatorStatus("Door",false);
    		room.setActuatorStatus("Window",false);
    		room.setActuatorStatus("Hvac",false);
    	}
    	/*stop threads as well*/
    }

    @PutMapping("/unlockbuilding/")
    public void unlockBuilding() {
    	for (VirtualRoom room : rooms.values()) {
    		room.setActuatorStatus("Door",true);
    	}
    }
}


