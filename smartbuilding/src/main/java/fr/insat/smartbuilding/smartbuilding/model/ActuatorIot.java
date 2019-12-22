package fr.insat.smartbuilding.smartbuilding.model;

import java.net.URI;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

public class ActuatorIot {

	private URI gateway;
	private String room;
	private String name;
	

	public ActuatorIot(URI gateway, String room, String name) {
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

	public String getStatus() {
		
		String status = new String();
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

		//m2m:cin > con > obj > str > [state] 
		JSONArray objectArray = (JSONArray) ((JSONObject)((JSONObject)((JSONObject) jsonView.get("m2m:cin")).get("con")).get("obj")).get("str");
		
		for (int i=0; i< objectArray.length() ; i++) {
			JSONObject temp = (JSONObject) objectArray.get(i);
			
			if (temp.getString("name").equals("state")) {
				status=temp.getString("val");
			}
		}
		return status;
	}
	

	public void setStatus(String status) {
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
				"&lt;str name=&quot;state&quot; val=&quot;"+status+"&quot;/&gt;\n" +
				"&lt;/obj&gt;\n" +
				"</con>\n" +
				"</m2m:cin>" ;
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<>(xmlPayload,headers);
		restTemplate.postForEntity(this.gateway.toString()+"~/in-cse/in-name/"+this.room+"/"+this.name,request,String.class);
		
	}
	
}
