package fr.insat.smartbuilding.smartbuilding.controller;

import fr.insat.smartbuilding.smartbuilding.model.TemperatureRegulation;
import fr.insat.smartbuilding.smartbuilding.model.VirtualRoom;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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
    
    /*I'm */
    @PutMapping("/targettemp/{temp}")
    public void setTargetTemp(@PathVariable("temp") Float targettemp) {
    	Thread t = new Thread(new TemperatureRegulation(targettemp));
    	t.start();
    }
    
    /*@PutMapping("/targetbrightness/{brightness}")
    public void setTargetBrightness(@PathVariable("brightness") Float brightness) {
       	Thread t = new Thread(new BrightnessRegulation(targetbrightness));
    	t.start();
    }*/

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


