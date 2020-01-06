var serverResponse, responsestatus;

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
			break;
			case 'Window':
				this.responsestatus = serverResponse.Window;
			break;
			case 'Lamp':
				this.responsestatus = serverResponse.Lamp;
			break;
			case 'Shutter':
				this.responsestatus = serverResponse.Shutter;
			break;
			case 'Door':
				this.responsestatus = serverResponse.Door;
			break;
		}
		return this.responsestatus;
}

function displayRoom(roomName) {
	document.getElementById("desc").innerHTML = "";
	document.getElementById("displayRoom").innerHTML = "";

	var container = document.getElementById("desc");
	var newContent = document.createTextNode("Description for room "+roomName);
	var bold = document.createElement("b");
	bold.append(newContent);
	container.append(bold);

	var currentDiv = document.getElementById("displayRoom");
	var pDesc = document.createElement("p");
	currentDiv.append(pDesc);

	// Get all sensors and get their values, then display it
        var xhttpsensor = new XMLHttpRequest();
	xhttpsensor.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var serverResponse = JSON.parse(xhttpsensor.responseText);
			for (var i in serverResponse) {
				var textNode = document.createTextNode(serverResponse[i]+ " =>" + getSensorStatus(roomName,serverResponse[i]));
				currentDiv.append(textNode);
				var br = document.createElement("br");
				currentDiv.append(br);
			}
		}
	};
	xhttpsensor.open("GET", "http://127.0.0.1:8000/getroom/"+roomName+"/sensors", true);
	xhttpsensor.setRequestHeader("Content-type", "application/json");
	xhttpsensor.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttpsensor.send(null);

	
	// Get all actuators and get their status, then display it
	var xhttpactuator = new XMLHttpRequest();
	xhttpactuator.onreadystatechange = function() {
		var serverResponse = JSON.parse(xhttpactuator.responseText);
		for (var i in serverResponse) {
				var textNode = document.createTextNode(serverResponse[i]+ " =>" + getActuatorStatus(roomName,serverResponse[i]));
				currentDiv.append(textNode);
				var br = document.createElement("br");
				currentDiv.append(br);
		}
	};
	xhttpactuator.open("GET", "http://127.0.0.1:8000/getroom/"+roomName+"/actuators", false);
	xhttpactuator.setRequestHeader("Content-type", "application/json");
	xhttpactuator.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttpactuator.send();
}


function getHistory(){
	var xhttphistoric = new XMLHttpRequest();
	xhttphistoric.open("GET", "http://127.0.0.1:8000/logs/", false);
	xhttphistoric.setRequestHeader("Content-type", "application/json");
	xhttphistoric.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttphistoric.send();
	var serverResponse = JSON.parse(xhttphistoric.responseText);
	var serverResponseArray = JSON.stringify(serverResponse.history);
	var wtf = JSON.parse(JSON.stringify(JSON.parse(JSON.stringify(JSON.parse(xhttphistoric.responseText).history))));
	var currentDiv = document.getElementById("historyDiv");
	document.getElementById("historyDiv").innerHTML = "";

	for (var i in wtf) {
		var newP = document.createElement("p");
		newP.append(document.createTextNode(i+" =>"+wtf[i]));
		currentDiv.append(newP);
	}
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

function loadRooms() {
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

function loadBuilding() {
	loadRooms();
	getHistory();
}

function lockBuilding() {
	var xhttp = new XMLHttpRequest();
	xhttp.open("PUT", "http://127.0.0.1:8000/lockbuilding/", true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttp.send();
}

function unlockBuilding() {
	var xhttp = new XMLHttpRequest();
	xhttp.open("PUT", "http://127.0.0.1:8000/unlockbuilding/", true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttp.send();
}

function setOutsideParams() {
	//curl -XPUT -H "Content-type: application/json" '127.0.0.1:8000/outside/0/1000/'
	var outsideBrightness = document.getElementById("outsideBrightness").value;
	var outsideTemp = document.getElementById("outsideTemp").value;
	var xhttp = new XMLHttpRequest();
	xhttp.open("PUT", "http://127.0.0.1:8000/outside/"+outsideTemp+"/"+outsideBrightness+"/", true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttp.send();
}


function setInsideParams() {
	//curl -XPUT -H "Content-type: application/json" '127.0.0.1:8000/targettemp/30'
	
	var targetBrightness = document.getElementById("targetBrightness").value;
	var targetTemp = document.getElementById("targetTemp").value;


	var xhttp = new XMLHttpRequest();
	xhttp.open("PUT", "http://127.0.0.1:8000/targettemp/"+targetTemp, true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttp.send();

	var xhttp = new XMLHttpRequest();
	xhttp.open("PUT", "http://127.0.0.1:8000/targetbrightness/"+targetBrightness, true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttp.send();
}

function addRoom() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			addRoomElement(document.getElementById("roomName").value);
			displayRoom(document.getElementById("roomName").value);
	        }
	};
	xhttp.open("POST", "http://127.0.0.1:8000/addroom/", true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttp.send("{\"name\": \""+document.getElementById("roomName").value+"\"}");
}

