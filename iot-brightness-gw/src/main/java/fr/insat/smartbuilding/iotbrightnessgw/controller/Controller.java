package fr.insat.smartbuilding.iotbrightnessgw.controller;

import fr.insat.smartbuilding.helper.virtual.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

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
			
		VirtualSensor newBrightnessSensor = new VirtualSensor(payload.get("location").toString(),(int) payload.get("value"));
		sensors.put(newBrightnessSensor.getId(), newBrightnessSensor);
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("id",newBrightnessSensor.getId());
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
		int newvalue= (int) payload.get("value");
		sensors.get(id).setValue((float) newvalue);
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("value",sensors.get(id).readValue());
		return response;
	}
	
	/*
	 * In order to set the value for an existing sensor, this request is to be received :
	 * curl -XPUT -H "Content-type: application/json" -d '{"program": "[1,2,3,4,5,6,7,8,9,10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59]"}' '127.0.0.1:8001/program/{id}'
	 */
	@PutMapping("/program/{id}")
	public String program(@PathVariable("id") UUID id, @RequestBody String payload)
	{
		float[] program = new float[60];
		
		/* We cannot directly convert JSON Array to Float Array and cannot do the trick easily by manipulating the payload object as a simple HashMap. Unsafe code below be cause of static size of table, sorry*/
		JSONObject jsonObject = new JSONObject(payload);
		JSONArray jsonArray = new JSONArray(jsonObject.get("program").toString());
		int length=jsonArray.length();
			
		for (int i = 0; i < length; i++) {
			program[i]=Float.parseFloat(jsonArray.get(i).toString());
		}
		
		sensors.get(id).setProgram(program);
		
		return jsonObject.get("program").toString();
	}
	
	/*
	 * In order to start or stop a program :
	 * curl -XPOST -H "Content-type: application/json" -d '{"status": "true"}' '127.0.0.1:8001/program/{id}'
	 * UUID of the sensor will be returned.
	 * !!!!!!
	 * We should have used a PUT verb here as this action is idempotent, but we do not have time 
	 * to refactor this and combine this method and the one above in one single method..
	 * !!!!!!
	 */
	@PostMapping(path = "/program/{id}", consumes = "application/json", produces = "application/json")
	public Map<String, Object> program(@PathVariable("id") UUID id, @RequestBody Map<String, Object> payload) {
		
		if ((boolean) payload.get("status")) {
			sensors.get(id).startProgram();
		} else {
			sensors.get(id).stopProgram();
		}
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status",payload.get("status"));
		return response ;
	}
}

