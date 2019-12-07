package fr.insat.smartbuilding.smartbuilding.model;

import java.net.URL;
import java.util.UUID;

import org.springframework.web.client.RestTemplate;

public class SensorIot {

	private UUID id;
	private URL gateway;
	
	public SensorIot( URL gateway) {

		this.gateway = gateway;
		
		RestTemplate restTemplate = new RestTemplate();
		
		
        /*String result = restTemplate.postForObject(gateway/, "add, responseType");
        
        String personResultAsJsonStr = restTemplate.postForObject(createPersonUrl, request, String.class);*/
	}
	
		     
	
}
