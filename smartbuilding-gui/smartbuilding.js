//document.getElementById("roomName").value = "Room02";

function addRoomElement(roomName) {
	var newDiv = document.createElement("div");
	newDiv.setAttribute("class","room");
	var newContent = document.createTextNode(roomName);
	newDiv.appendChild(newContent);
	var currentDiv = document.getElementById('roomLists');
	document.body.insertBefore(newDiv, currentDiv);
}


function addRoom() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
	        	// Here call a function that adds a room display
			addRoomElement(document.getElementById("roomName").value);
	        }
	};
	xhttp.open("POST", "http://127.0.0.1:8000/addroom/", true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhttp.send("{\"name\": \""+document.getElementById("roomName").value+"\"}");
}

