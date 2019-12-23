//document.getElementById("roomName").value = "Room02";

function getActuatorStatus(roomName,actuatorName) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var serverResponse = JSON.parse(xhttp.responseText);
			for (var i in serverResponse) {
				return serverResponse[i] ;
			}
	        }
	};
	xhttp.open("GET", "http://127.0.0.1:8000/getroom/"+roomName+"/readactuator/"+actuatorName, true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttp.send();
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
				newP.append(document.createTextNode(serverResponse[i]));
				currentDiv.append(newP);
			}
		}
	};
	xhttpsensor.open("GET", "http://127.0.0.1:8000/getroom/"+roomName+"/sensors", true);
	xhttpsensor.setRequestHeader("Content-type", "application/json");
	xhttpsensor.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttpsensor.send();


        var xhttpactuator = new XMLHttpRequest();
	xhttpactuator.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var serverResponse = JSON.parse(xhttpactuator.responseText);
			var pDesc = document.createElement("p");
			var bold = document.createElement("b");
			bold.append("Sensors:");
			pDesc.append(bold);
			currentDiv.append(pDesc);
			for (var i in serverResponse) {
				var newP = document.createElement("p");
				newP.append(document.createTextNode(serverResponse[i]+" =>"+getActuatorStatus(roomName,serverResponse[i])));
				currentDiv.append(newP);
			}
		}
	};
	xhttpactuator.open("GET", "http://127.0.0.1:8000/getroom/"+roomName+"/actuators", true);
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

