package fr.insat.smartbuilding.smartbuilding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartBuildingApplication {
	
	public static void main(String[] args) {
		
		/*Web Server handling client-side requests*/
		SpringApplication.run(SmartBuildingApplication.class, args);

	}

}
