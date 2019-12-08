package fr.insat.smartbuilding.smartbuilding.model;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SensorIot {

	private UUID id;
	private URI gateway;
	
	public SensorIot(URI gateway, String fqdn, Float range) {

		this.gateway = gateway;
		
		RestTemplate restTemplate = new RestTemplate();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("location", fqdn);
		jsonObject.put("value",range);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(),headers);
		
		System.out.println("Sending ADD request for Sensor :"+jsonObject.toString());
		
		ResponseEntity<String> response = restTemplate.postForEntity(this.gateway.toString()+"/add",request,String.class);
		
		jsonObject = new JSONObject(response.getBody());
		System.out.println("Done ADD request for Sensor :"+jsonObject.toString());
		this.id = UUID.fromString((String)jsonObject.get("id"));
		
	}
	
	public Float getValue() {
		RestTemplate restTemplate = new RestTemplate();

		System.out.println("Sending Measurement request for Sensor :"+this.id.toString());

		ResponseEntity<String> response = restTemplate.getForEntity(this.gateway.toString()+"/read/"+this.id.toString(), String.class);
		JSONObject jsonObject = new JSONObject(response.getBody());
		
		System.out.println("Done Measurement request for Sensor : "+jsonObject.toString());

		return Float.parseFloat(jsonObject.get("value").toString());
	}
	
	public void setValue(Float value) {
		RestTemplate restTemplate = new RestTemplate();

	    Map<String, String> params = new HashMap<String, String>();
	    params.put("id", this.id.toString());
	    JSONObject jsonObject = new JSONObject();
		jsonObject.put("value",value); 
	    
		System.out.println("Sending Value update for Sensor :"+jsonObject.toString());
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(),headers);

	    restTemplate.exchange(this.gateway.toString()+"/set/{id}",HttpMethod.PUT,request,String.class,params);
	    System.out.println("Done Value update for Sensor :"+this.id.toString());
	    
	}

	public void addProgram(float[] program) {

		RestTemplate restTemplate = new RestTemplate();

	    Map<String, String> params = new HashMap<String, String>();
	    params.put("id", this.id.toString());
	    JSONObject jsonObject = new JSONObject();
		jsonObject.put("program",program); 
	    
		System.out.println("Sending Program update for Sensor :"+jsonObject.toString());
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(),headers);

	    restTemplate.exchange(this.gateway.toString()+"/program/{id}",HttpMethod.PUT,request,String.class,params);
	    System.out.println("Done Program update for Sensor :"+this.id.toString());
	}
	
	public void setProgram(Boolean activate) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", activate);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(),headers);
		
		System.out.println("Sending Program Status update for Sensor :"+jsonObject.toString());
		
		restTemplate.postForEntity(this.gateway.toString()+"/program/"+this.id.toString(),request,String.class);
		
		System.out.println("Done Program Status update for Sensor :"+jsonObject.toString());

	}
	
	
	
	public UUID getId() {
		return this.id;
	}
	
}
