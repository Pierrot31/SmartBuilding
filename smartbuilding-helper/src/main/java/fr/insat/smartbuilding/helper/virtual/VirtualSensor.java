package fr.insat.smartbuilding.helper.virtual;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.Random;

public class VirtualSensor {

	private UUID id;
	private String location;
	private float value;
	private float[] program = null;
	private Boolean programActivated = false;
	
	public VirtualSensor(String location, int range){
		this.id = UUID.randomUUID();
		this.location = location;
		this.value = new Random().nextFloat()*range;
	}
	
	public UUID getId(){
		return this.id;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public void setProgram(float[] program) {
		this.program = program;
		programActivated = true;
	}
	
	public void startProgram() {
		if (program != null) {
			programActivated = true;
		}
	}
	
	public void stopProgram() {
		programActivated = false;
	}
	
	public float readValue() {
		float data = value;
		if (programActivated) {
			data = program[LocalDateTime.now().getMinute()];	
		}
		return data;
	}
	
	public void setValue(float value) {
		this.value = value;
	};
	
}
