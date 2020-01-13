package fr.insat.smartbuilding.smartbuilding;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.insat.smartbuilding.helper.virtual.ProgramList;
import fr.insat.smartbuilding.smartbuilding.model.VirtualRoom;

@SpringBootApplication
public class SmartBuildingApplication {
	
	public static void main(String[] args) {
		
		/*Web Server handling client-side requests*/
		SpringApplication.run(SmartBuildingApplication.class, args);
		
		/*Will contain the Master WS handling client-side requests*/
		
		/*Following code will end in Controller one day
		Map<String, VirtualRoom> rooms = new HashMap<String,VirtualRoom>();
		
		

		for (int i=0; i<2; i++) {
			VirtualRoom newVirtualRoom = new VirtualRoom("Room0"+i);
			
			newVirtualRoom.addActuator(URI.create("http://127.0.0.1:8002"), "HVAC", true);
			newVirtualRoom.addSensor(URI.create("http://127.0.0.1:8001"),"Thermometer",(float) 35);
			rooms.put("Room0"+i,newVirtualRoom);
		}*/
		
		
		/*System.out.println("Status of actuator Room01 : "+rooms.get("Room01").readActuatorStatus("HVAC").toString());
		rooms.get("Room01").setActuatorStatus("HVAC",false);
		System.out.println("Status of actuator Room01 : "+rooms.get("Room01").readActuatorStatus("HVAC").toString());
		
		System.out.println("Value of sensor Room01 : "+rooms.get("Room01").readSensorValue("Thermometer").toString());
		rooms.get("Room01").setSensorValue("Thermometer",(float)30);
		System.out.println("Value of sensor Room01 : "+rooms.get("Room01").readSensorValue("Thermometer").toString());
		ProgramList programhelper = new ProgramList();
		rooms.get("Room01").addSensorProgram("Thermometer", programhelper.getProgram("summertemp"));
		System.out.println("Value of sensor Room01 : "+rooms.get("Room01").readSensorValue("Thermometer").toString());

		rooms.get("Room01").setSensorProgram("Thermometer", false);
		System.out.println("Value of sensor Room01 : "+rooms.get("Room01").readSensorValue("Thermometer").toString());
		rooms.get("Room01").setSensorProgram("Thermometer", true);
		System.out.println("Value of sensor Room01 : "+rooms.get("Room01").readSensorValue("Thermometer").toString());
		 */
		
	}

}
