package fr.insat.smartbuilding.smartbuilding.model;

import java.net.URI;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

public class SensorIot {

	private URI gateway;
	private String room;
	private String name;
	
	public SensorIot(URI gateway, String room, String name) {
		
		this.room = room;
		this.name = name;
		this.gateway = gateway;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache());
		headers.add("content-type","application/xml;ty=3");
		headers.add("Accept","application/xml");
		headers.add("x-m2m-origin","admin:admin");
		
		String xmlPayload="<m2m:cnt xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\""+this.name+"\">\n" +
			"</m2m:cnt>";
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<>(xmlPayload,headers);
		restTemplate.postForEntity(this.gateway.toString()+"~/in-cse/in-name/"+this.room,request,String.class);
		
	}
	
	public Float getValue() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache());
		headers.add("content-type","application/xml;ty=4");
		headers.add("Accept","application/xml");
		headers.add("x-m2m-origin","admin:admin");
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(this.gateway.toString()+"~/in-cse/in-name/"+this.room+"/"+this.name+"/la",HttpMethod.GET,request,String.class);
		
		// We have mixed content in the response (escaped XML and non escaped XML
		String cleanXml = HtmlUtils.htmlUnescape(response.getBody());
		
		// We have troubles with DOM manipulation, JSON seems easier
		JSONObject jsonView = XML.toJSONObject(cleanXml);

		//m2m:cin > con > obj > int > [state] 
		JSONObject jsonSubView = (JSONObject) ((JSONObject)((JSONObject)((JSONObject) jsonView.get("m2m:cin")).get("con")).get("obj")).get("float");
		
		float value = ((Double) jsonSubView.get("val")).floatValue();
		
		return value;
	}
	
	public void setValue(Float value, String unit) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache());
		headers.add("content-type","application/xml;ty=4");
		headers.add("Accept","application/xml");
		headers.add("x-m2m-origin","admin:admin");
		
		String xmlPayload="<m2m:cin xmlns:m2m=\"http://www.onem2m.org/xml/protocols\">\n" +
				"<cnf>message</cnf>\n" +
				"<con>\n" +
				"&lt;obj&gt;\n" +
				"&lt;str name=&quot;location&quot; val=&quot;"+room+"&quot;/&gt;\n" +
				"&lt;str name=&quot;category&quot; val=&quot;"+name+"&quot;/&gt;\n" +
				"&lt;float name=&quot;data&quot; val=&quot;"+value+"&quot;/&gt;\n" +
				"&lt;str name=&quot;unit&quot; val=&quot;"+unit+"&quot;/&gt;\n" +
				"&lt;/obj&gt;\n" +
				"</con>\n" +
				"</m2m:cin>" ;
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<>(xmlPayload,headers);
		restTemplate.postForEntity(this.gateway.toString()+"~/in-cse/in-name/"+this.room+"/"+this.name,request,String.class);
		
	}

	/*
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

	}*/
	
}
