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

public class ActuatorIot {

	private UUID id;
	private URI gateway;
	
	public ActuatorIot(URI gateway, String fqdn, Boolean status) {

		this.gateway = gateway;
		
		RestTemplate restTemplate = new RestTemplate();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("location", fqdn);
		jsonObject.put("status",status);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(),headers);
		
		
		System.out.println("Sending ADD request for Actuator :"+jsonObject.toString());
		
		ResponseEntity<String> response = restTemplate.postForEntity(this.gateway.toString()+"/add",request,String.class);
		
		jsonObject = new JSONObject(response.getBody());
		System.out.println("Done ADD request for Actuator :"+jsonObject.toString());
		this.id = UUID.fromString((String)jsonObject.get("id"));
		
	}
	
	public Boolean getStatus() {
		RestTemplate restTemplate = new RestTemplate();

		System.out.println("Sending STATUS request for Actuator :"+this.id.toString());

		ResponseEntity<String> response = restTemplate.getForEntity(this.gateway.toString()+"/status/"+this.id.toString(), String.class);
		JSONObject jsonObject = new JSONObject(response.getBody());
		
		System.out.println("Done STATUS request for Actuator : "+jsonObject.toString());

		return (Boolean) jsonObject.get("value");
	}
	/*	 * curl -XPUT -H "Content-type: application/json" -d '{"value": "true"}' '127.0.0.1:8002/status/{id}'
*/
	public void setStatus(Boolean status) {
		RestTemplate restTemplate = new RestTemplate();

	    Map<String, String> params = new HashMap<String, String>();
	    params.put("id", this.id.toString());
	    JSONObject jsonObject = new JSONObject();
		jsonObject.put("status",status); 
	    
		System.out.println("Sending STATUS update for Actuator :"+jsonObject.toString());

		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(),headers);

	    restTemplate.exchange(this.gateway.toString()+"/status/{id}",HttpMethod.PUT,request,String.class,params);
	    System.out.println("Done STATUS update for Actuator :"+this.id.toString());
	    
	}
	
	public UUID getId() {
		return this.id;
	}
}
