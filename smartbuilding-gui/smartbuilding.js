//document.getElementById("roomName").value = "Room02";


var serverResponse, responsestatus, global;
/*function getActuatorStatus(roomName,actuatorName) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		
		 if(this.readyState == 4 && this.status == 200) {
			serverResponse = JSON.parse(xhttp.responseText);
			for (var i in serverResponse) {
				this.responsestatus = serverResponse[i];
				console.log("In for " + this.responsestatus);
				document.createTextNode(this.responsestatus);
			}
		}
	};
	xhttp.open("GET", "http://127.0.0.1:8000/getroom/"+roomName+"/readactuator/"+actuatorName, true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttp.send(null);	
}*/

function getSensorStatus(roomName,sensorName)
{   
	var xhttpSensorStatus = new XMLHttpRequest();
	xhttpSensorStatus.open("GET", "http://127.0.0.1:8000/getroom/"+roomName+"/readsensor/"+sensorName,  false); 
	xhttpSensorStatus.setRequestHeader("Content-type", "application/json");
	xhttpSensorStatus.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttpSensorStatus.send(null);
   
   serverResponse = JSON.parse(xhttpSensorStatus.responseText);
		switch (sensorName){
			case 'Temperature':
				this.responsestatus = serverResponse.Temperature + " °C";
				console.log(this.responsestatus);
			break;
			case 'Brightness':
				this.responsestatus = serverResponse.Brightness + " lux";
				console.log(this.responsestatus);
			break;
		}
		return this.responsestatus;
}

function getActuatorStatus(roomName,actuatorName)
{   
	var xhttpactuatorStatus = new XMLHttpRequest();
	xhttpactuatorStatus.open("GET", "http://127.0.0.1:8000/getroom/"+roomName+"/readactuator/"+actuatorName,  false); 
	xhttpactuatorStatus.setRequestHeader("Content-type", "application/json");
	xhttpactuatorStatus.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttpactuatorStatus.send(null);
   
   serverResponse = JSON.parse(xhttpactuatorStatus.responseText);
		switch (actuatorName){
			case 'Hvac':
				this.responsestatus = serverResponse.Hvac;
				console.log(this.responsestatus);
			break;
			case 'Window':
				this.responsestatus = serverResponse.Window;
				console.log(this.responsestatus);
			break;
			case 'Lamp':
				this.responsestatus = serverResponse.Lamp;
				console.log(this.responsestatus);
			break;
			case 'Shutter':
				this.responsestatus = serverResponse.Shutter;
				console.log(this.responsestatus);
				
			break;
			case 'Door':
				this.responsestatus = serverResponse.Door;
				console.log(this.responsestatus);
			break;
		}
		return this.responsestatus;
}

function displayRoom(roomName) {
	document.getElementById("displayRoom").innerHTML = "";
	var currentDiv = document.getElementById("displayRoom");

	var newContent = document.createTextNode("Description for room "+roomName);
	currentDiv.append(newContent);

        var xhttpsensor = new XMLHttpRequest();
	xhttpsensor.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var serverResponse = JSON.parse(xhttpsensor.responseText);
			var pDesc = document.createElement("p");
			var bold = document.createElement("b");
			bold.append("Sensors:");
			pDesc.append(bold);
			currentDiv.append(pDesc);
			for (var i in serverResponse) {
				var newP = document.createElement("p");
				newP.append(document.createTextNode(serverResponse[i]+ " =>" + getSensorStatus(roomName,serverResponse[i])));
				currentDiv.append(newP);
			}
		}
	};
	xhttpsensor.open("GET", "http://127.0.0.1:8000/getroom/"+roomName+"/sensors", true);
	xhttpsensor.setRequestHeader("Content-type", "application/json");
	xhttpsensor.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttpsensor.send(null);

	
		var xhttpactuator = new XMLHttpRequest();
	xhttpactuator.onreadystatechange = function() {
		var serverResponse = JSON.parse(xhttpactuator.responseText);
		var pDesc = document.createElement("p");
		var bold = document.createElement("b");
		bold.append("Actuators:");
		pDesc.append(bold);
		currentDiv.append(pDesc);
		for (var i in serverResponse) {
			var newP = document.createElement("p");
			newP.append(document.createTextNode(serverResponse[i]+" =>" + getActuatorStatus(roomName,serverResponse[i])));
			console.log("Actuator" + getActuatorStatus(roomName,serverResponse[i]));
			currentDiv.append(newP);
		}
	};
	xhttpactuator.open("GET", "http://127.0.0.1:8000/getroom/"+roomName+"/actuators", false);
	xhttpactuator.setRequestHeader("Content-type", "application/json");
	xhttpactuator.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttpactuator.send();
}





function addRoomElement(roomName) {
	var newDiv = document.createElement("div");
	newDiv.setAttribute("class","room");
	var temp = roomName;
	newDiv.setAttribute("onclick","displayRoom(\""+roomName+"\")");
	var newContent = document.createTextNode(roomName);
	newDiv.appendChild(newContent);
	var currentDiv = document.getElementById("roomLists");
	currentDiv.append(newDiv);
}

function loadBuilding() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var serverResponse = JSON.parse(xhttp.responseText);
			for (var i in serverResponse) {
				addRoomElement(serverResponse[i]);
			}
	        }
	};
	xhttp.open("GET", "http://127.0.0.1:8000/getrooms/", true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttp.send();
}

function addRoom() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
	        // Here call a function that adds a room display
			addRoomElement(document.getElementById("roomName").value);
			displayRoom(document.getElementById("roomName").value);
	        }
	};
	xhttp.open("POST", "http://127.0.0.1:8000/addroom/", true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttp.send("{\"name\": \""+document.getElementById("roomName").value+"\"}");
}

