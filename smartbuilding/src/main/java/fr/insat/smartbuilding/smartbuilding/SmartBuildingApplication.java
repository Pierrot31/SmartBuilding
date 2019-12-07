package fr.insat.smartbuilding.smartbuilding;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.insat.smartbuilding.smartbuilding.model.VirtualRoom;

@SpringBootApplication
public class SmartBuildingApplication {
	
	public static void main(String[] args) {
		
		/*Web Server handling client-side requests*/
		SpringApplication.run(SmartBuildingApplication.class, args);
		
		/*Will contain the Master WS handling client-side requests*/
		
		/*Following code will end in Controller one day*/
		Map<String, VirtualRoom> rooms = new HashMap<String,VirtualRoom>();

		for (int i=0; i<9; i++) {
			VirtualRoom newVirtualRoom = new VirtualRoom("Room0"+i);
			
			newVirtualRoom.addActuator(URI.create("http://127.0.0.1:8002"), "HVAC", true);
			rooms.put("Room0"+i,newVirtualRoom);
		}
		
	}

}
