package fr.insat.smartbuilding.iottempgw.controller;

import fr.insat.smartbuilding.helper.virtual.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.CDL;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class Controller {
	
	private final Map<UUID, VirtualSensor> sensors = new HashMap<UUID, VirtualSensor>();
	
	/*
	 * In order to add a new sensor, this request is to be received :
	 * curl -XPOST -H "Content-type: application/json" -d '{"location": "Room", "value": "35"}' '127.0.0.1:8001/add/'
	 * UUID of the sensor will be returned.
	 */
	@PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
	public Map<String, Object> add(@RequestBody Map<String, Object> payload) {
			
		VirtualSensor newTempSensor = new VirtualSensor(payload.get("location").toString(),Integer.parseInt((String) payload.get("value")));
		sensors.put(newTempSensor.getId(), newTempSensor);
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("id",newTempSensor.getId());
		return response ;
	}
	
	/*
	 * In order to read the value from an existing sensor, this request is to be received :
	 * curl -XGET -H "Content-type: application/json" '127.0.0.1:8001/read/{id}'
	 */
	@GetMapping("/read/{id}")
	public Map<String, Object> read(@PathVariable("id") UUID id) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("value",sensors.get(id).readValue());
		return response;
		
	}
	
	/*
	 * In order to set the value for an existing sensor, this request is to be received :
	 * curl -XPUT -H "Content-type: application/json" -d '{"value": "40"}' '127.0.0.1:8001/set/{id}'
	 */
	@PutMapping("/set/{id}")
	public Map<String, Object> set(@PathVariable("id") UUID id, @RequestBody Map<String, Object> payload)
	{
		sensors.get(id).setValue(Float.parseFloat((String) payload.get("value")));
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("value",sensors.get(id).readValue());
		return response;
	}
	
	/*
	 * In order to set the value for an existing sensor, this request is to be received :
	 * curl -XPUT -H "Content-type: application/json" -d '{"program": "[00, 01, 02, 03, 04, 05, 06, 07, 08, 09, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59]"}' '127.0.0.1:8001/set/{id}'
	 */
	@PutMapping("/program/{id}")
	public String program(@PathVariable("id") UUID id, @RequestBody String payload)
	{
		float[] program = new float[60];
		
		/* We cannot directly convert JSON Array to Float Array and cannot do the trick easily by manipulating the payload object as a simple HashMap. Unsafe code below be cause of static size of table, sorry*/
		JSONObject jsonObject = new JSONObject(payload);
		JSONArray jsonArray = new JSONArray(jsonObject.get("program").toString());
		int length=jsonArray.length();
	
		System.out.println("Content of jsonArray :"+jsonArray.toString());
		
		for (int i = 0; i < length; i++) {
			program[i]=Float.parseFloat(jsonArray.get(i).toString());
		}
		
		
		sensors.get(id).setProgram(program);
		
		return jsonObject.get("program").toString();
	}	
}


