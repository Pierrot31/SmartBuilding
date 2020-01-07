package fr.insat.smartbuilding.smartbuilding.model;

import java.time.LocalDateTime;

import fr.insat.smartbuilding.smartbuilding.controller.Controller;

public class WeatherSimulation implements Runnable {
	
	Thread thread;
	
	public WeatherSimulation() {
	}

	public void run() {
		
		while (true) {
		
			Controller.outside.setSensorValue("Temperature",Controller.programs.getProgram(Controller.outsideTempSimulation)[LocalDateTime.now().getMinute()],"Degree");
			Controller.outside.setSensorValue("Brightness",Controller.programs.getProgram("daylight")[LocalDateTime.now().getMinute()],"Lux");

			try {
				Thread.sleep(1000*60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
}
	
