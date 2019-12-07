package fr.insat.smartbuilding.helper.virtual;

import java.util.HashMap;

public class ProgramList {
	
	HashMap <String,float[]> programs = new HashMap <String,float[]>();
	
	/* Here we assume the following :
	 * A full day is 60 minutes
	 * We approximate (2*pi)/60 to be 0.105
	 * Room with lamps on will be around 500 Lux
	 * Room natural will be around 250 Lux
	 * Night time will be around 10 Lux
	 * */
	
	ProgramList(){
		
		float[] buffer = new float[60];
		
		/* Day Luminance */
		for (int i = 0; i < 59; i++) {
			buffer[i] = 500*(((float) Math.sin(i*0.105)+1)/2);
		}
		this.programs.put("daylight", buffer);
		
		/* Winter Temperature */
		for (int i = 0; i < 59; i++) {
			buffer[i] = 10*(((float) Math.sin(i*0.105)+1)/2);
		}
		this.programs.put("daylight", buffer);
		
		/* Summer Temperature */
		for (int i = 0; i < 59; i++) {
			buffer[i] = 35*(((float) Math.sin(i*0.105)+1)/2);
		}
		this.programs.put("daylight", buffer);
	}
	
	
	float[] getProgram(String name) {
		return programs.get(name);
	}
}
