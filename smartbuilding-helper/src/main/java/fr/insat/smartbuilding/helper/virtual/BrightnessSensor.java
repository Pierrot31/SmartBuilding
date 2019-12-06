package fr.insat.smartbuilding.helper.virtual;

import java.util.Random;
import java.util.UUID;

public class BrightnessSensor extends VirtualSensor {
	
	private UUID id;
	private String location;
	private Random value;
	private float[] program;
	
	public BrightnessSensor(String location){
		super(location);
	}

	@Override
	public float readValue() {
		
		return 0;
	}

	@Override
	public void setValue() {
		
	}
}
