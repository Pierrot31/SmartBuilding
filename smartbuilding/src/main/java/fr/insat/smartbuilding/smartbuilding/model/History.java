package fr.insat.smartbuilding.smartbuilding.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class History {

	private Map<String, String> history = new HashMap<String, String>();
	
	public History() {
		
	}
	
	public void put(String log) {
		/*https://stackabuse.com/how-to-get-current-date-and-time-in-java*/
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		this.history.put(formatter.format(date), log);
	}
	
	public Map<String, String> retrieve() {
		return this.history ;
	}
	
	public Map<String, String> getHistory() {
		return this.history;
	}
	
}
