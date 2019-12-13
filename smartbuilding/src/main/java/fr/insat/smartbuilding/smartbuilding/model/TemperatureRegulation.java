package fr.insat.smartbuilding.smartbuilding.model;

import fr.insat.smartbuilding.smartbuilding.controller.Controller;

public class TemperatureRegulation implements Runnable {
	
	/**/
	Thread thread;	

	
	public TemperatureRegulation() {

	}
	
	public void run() {

		while (true) {

			Float outsidetemp = Controller.outside.readSensorValue("Temperature");
			Float targetTemperature = Controller.targetTemperature;
			for (VirtualRoom room : Controller.rooms.values()) {
				System.out.println("Inside Temperature Regulation Thread, target : "+targetTemperature);
				Float currenttemp = room.readSensorValue("Temperature");
				if (currenttemp < targetTemperature) {
						if (outsidetemp < targetTemperature) {
							room.setActuatorStatus("Window",false);
							room.setActuatorStatus("Hvac",true);
						} else {
							room.setActuatorStatus("Window",true);
							room.setActuatorStatus("Hvac",false);
						}
				} else if (currenttemp > targetTemperature) {
					if (outsidetemp < targetTemperature) {
						room.setActuatorStatus("Window",true);
						room.setActuatorStatus("Hvac",false);
					} else {
						room.setActuatorStatus("Window",false);
						room.setActuatorStatus("Hvac",false);
					}
				}
				try {
					Thread.sleep(1000*60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
