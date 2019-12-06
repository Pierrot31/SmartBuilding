package fr.insat.smartbuilding.helper.virtual;

import java.util.UUID;
import java.util.Random;

public abstract class VirtualSensor {

	private UUID id;
	private String location;
	private Random value;
	private float[] program;
	
	public VirtualSensor(String location){
		this.id = UUID.randomUUID();
		this.location = location;
		this.value = new Random();
	}
	
	public UUID getId(){
		return this.id;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public float[] getProgram() {
		return this.program;
	}
	
	public void setProgram(float[] program) {
		this.program = program;
	}
	
	public abstract float readValue();
	
	public abstract void setValue();
	
}
