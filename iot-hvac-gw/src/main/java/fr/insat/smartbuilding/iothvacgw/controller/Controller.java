package fr.insat.smartbuilding.iothvacgw.controller;

import fr.insat.smartbuilding.helper.virtual.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class Controller {
	
	private final Map<UUID, VirtualActuator> actuators = new HashMap<UUID, VirtualActuator>();
	
	/*
	 * In order to add a new actuator, this request is to be received :
	 * curl -XPOST -H "Content-type: application/json" -d '{"location": "Room", "status": "true"}' '127.0.0.1:8002/add/'
	 * UUID of the actuator will be returned.
	 * Convention : true means actuator is on, e.g. HVAC is ON, Door is Open, Window is Open etc.
	 */
	@PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
	public Map<String, Object> add(@RequestBody Map<String, Object> payload) {
			
		VirtualActuator newTempActuator = new VirtualActuator(payload.get("location").toString(),(Boolean) payload.get("status"));
		actuators.put(newTempActuator.getId(), newTempActuator);
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("id",newTempActuator.getId());
		return response ;
	}
	
	/*
	 * In order to read the status from an existing actuator, this request is to be received :
	 * curl -XGET -H "Content-type: application/json" '127.0.0.1:8002/status/{id}'
	 */
	@GetMapping("/status/{id}")
	public Map<String, Object> read(@PathVariable("id") UUID id) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("value",actuators.get(id).getStatus());
		return response;
		
	}
	
	/*
	 * In order to set the value for an existing actuator, this request is to be received :
	 * curl -XPUT -H "Content-type: application/json" -d '{"value": "true"}' '127.0.0.1:8002/status/{id}'
	 */
	@PutMapping("/status/{id}")
	public Map<String, Object> set(@PathVariable("id") UUID id, @RequestBody Map<String, Object> payload)
	{
		actuators.get(id).setStatus(Boolean.parseBoolean((String) payload.get("value")));
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("value",actuators.get(id).getStatus());
		return response;
	}
	
}


