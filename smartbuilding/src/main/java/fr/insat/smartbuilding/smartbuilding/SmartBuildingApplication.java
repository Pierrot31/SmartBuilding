package fr.insat.smartbuilding.smartbuilding;

import java.net.URI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.insat.smartbuilding.smartbuilding.model.VirtualRoom;

@SpringBootApplication
public class SmartBuildingApplication {
	
	public static void main(String[] args) {
		
		/*Web Server handling client-side requests*/
		SpringApplication.run(SmartBuildingApplication.class, args);
		
		/*
		VirtualRoom testRoom = new VirtualRoom("testRoom",URI.create("http://127.0.0.1:8080"));
		testRoom.addSensor(URI.create("http://127.0.0.1:8080"), "Temperature");
		testRoom.setSensorValue("Temperature", (float) 20,"Degree");
		testRoom.addActuator(URI.create("http://127.0.0.1:8080"), "Hvac");
		testRoom.setActuatorStatus("Hvac", "ON");
		String result = testRoom.readActuatorStatus("Hvac");
		System.out.println("Actuator status : "+result);
		
		Float result2 = testRoom.readSensorValue("Temperature");
		System.out.println("Sensor status : "+result2);
		*/
	}

}
