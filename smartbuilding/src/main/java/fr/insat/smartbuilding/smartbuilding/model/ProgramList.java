package fr.insat.smartbuilding.smartbuilding.model;

import java.util.HashMap;

public class ProgramList {
	
	HashMap <String,float[]> programs = new HashMap <String,float[]>();
	
	/* Here we assume the following :
	 * A full day is 60 minutes
	 * We approximate (2*pi)/60 to be 0.105
	 * Room with lamps on will be around 200 Lux
	 * Room natural will be around 100 Lux
	 * Night time will be around 10 Lux
	 * */
	
	public ProgramList(){
		
		float[] buffer1 = new float[60];
		float[] buffer2 = new float[60];
		float[] buffer3 = new float[60];

		
		/* Day Luminance */
		for (int i = 0; i < 59; i++) {
			buffer1[i] = 200*(((float) Math.sin(i*0.105)+1)/2);
		}
		this.programs.put("daylight", buffer1);
		
		/* Winter Temperature */
		for (int i = 0; i < 59; i++) {
			buffer2[i] = 10*(((float) Math.sin(i*0.105)+1)/2);
		}
		this.programs.put("wintertemp", buffer2);
		
		/* Summer Temperature */
		for (int i = 0; i < 59; i++) {
			buffer3[i] = 35*(((float) Math.sin(i*0.105)+1)/2);
		}
		this.programs.put("summertemp", buffer3);
	}
	
	
	public float[] getProgram(String name) {
		System.out.println("program retrieved: "+name);
		System.out.println(programs.get(name).toString());
		return programs.get(name);
	}
}
