package fr.insat.smartbuilding.smartbuilding.model;

import fr.insat.smartbuilding.smartbuilding.controller.Controller;

public class TemperatureRegulation implements Runnable {
	
	/**/
	Thread thread;
	
	private Float targettemp;
	
	public TemperatureRegulation(Float targettemp) {
		this.targettemp = targettemp;
	}

	public void run() {

		while (true) {
			
			
			Float outsidetemp = Controller.outside.readSensorValue("Temperature");
			for (VirtualRoom room : Controller.rooms.values()) {
				Float currenttemp = room.readSensorValue("Temperature");
				if (currenttemp < targettemp) {
						if (outsidetemp < targettemp) {
							room.setActuatorStatus("Window",false);
							room.setActuatorStatus("Hvac",true);
						} else {
							room.setActuatorStatus("Window",true);
							room.setActuatorStatus("Hvac",false);
						}
				} else if (currenttemp > targettemp) {
					if (outsidetemp < targettemp) {
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
