package fr.insat.smartbuilding.smartbuilding.model;

import fr.insat.smartbuilding.smartbuilding.controller.Controller;

public class BrightnessRegulation implements Runnable {
	
	/**/
	Thread thread;	

	
	public BrightnessRegulation() {

	}
	
	public void run() {

		while (true) {

			Float outsidebrightness = Controller.outside.readSensorValue("Brightness");
			Float targetBrightness = Controller.targetBrightness;
			for (VirtualRoom room : Controller.rooms.values()) {
				System.out.println("Inside Brightness Regulation Thread, target : "+targetBrightness);
				Float currentbrightness = room.readSensorValue("Brightness");
				if (currentbrightness < targetBrightness) {
						if (outsidebrightness < targetBrightness) {
							room.setActuatorStatus("Shutter","OFF");
							room.setActuatorStatus("Lamp","ON");
						} else {
							room.setActuatorStatus("Shutter","ON");
							room.setActuatorStatus("Lamp","OFF");
						}
				} else if (currentbrightness > targetBrightness) {
					if (outsidebrightness < targetBrightness) {
						room.setActuatorStatus("Shutter","OFF");
						room.setActuatorStatus("Lamp","OFF");
					} else {
						room.setActuatorStatus("Shutter","ON");
						room.setActuatorStatus("Lamp","OFF");
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
