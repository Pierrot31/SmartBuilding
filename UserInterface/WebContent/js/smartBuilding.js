setTimeout("window.location.reload()",5000);

// Page element retrieving
var canvas = document.getElementById("myCanvas");
var ctx = canvas.getContext("2d");
var modal = document.getElementById("myModal");
var modal2 = document.getElementById("myModalNoGraph");

// Devices coordinates
var tempSensorsCoords = [];
var heatersCoords = [];
var windowsCoords = [];


//Update checkboxes
function updateCheckboxes()
{
	// Toggle heater checkbox
	var nbHeaters = parseInt($("#hnb").text());
	for (var i=0; i<nbHeaters; i++) {
		if ($("#hvalheater"+i.toString()).text()==="ON") {
			$("#checkboxheater"+i.toString()).prop('checked', true);
		} else {
			$("#checkboxheater"+i.toString()).prop('checked', false);
		}
	}
	
	// Redraw heaters
	var heatersNb = parseInt($("#hnb").text());
	for (var i=0; i<heatersNb; i++) {
		var heatersId = $("#hid"+(i.toString())).text();		
		var x = parseInt($("#"+heatersId+"X").text());
		var y = parseInt($("#"+heatersId+"Y").text());
		if ($("#hvalheater"+i.toString()).text()==="OFF") {
			drawHeaterOff(x, y, i);
		} else {
			drawHeaterOn(x, y, i);				
		}	
	}
	
	// Toggle window checkbox
	var nbWindows = parseInt($("#wnb").text());
	for (var i=0; i<nbWindows; i++) {
		if ($("#wvalwindow"+i.toString()).text()==="OPEN") {
			$("#checkboxwindow"+i.toString()).prop('checked', true);
		} else {
			$("#checkboxwindow"+i.toString()).prop('checked', false);
		}
	}
	
	// Redraw windows
	var windowsNb = parseInt($("#wnb").text());
	for (var i=0; i<windowsNb; i++) {
		var windowsId = $("#wid"+(i.toString())).text();		
		var x = parseInt($("#"+windowsId+"X").text());
		var y = parseInt($("#"+windowsId+"Y").text());
		if ($("#wvalwindow"+i.toString()).text()==="CLOSED") {
			drawWindowClosed(x, y, i);
		} else {
			drawWindowOpen(x, y, i);				
		}	
	}
}
updateCheckboxes(); /////

// Update heater status on checkbox click
function updateHeater(element)
{
	var nbHeaters = parseInt($("#hnb").text());
	for (var i=0; i<nbHeaters; i++) {
		if (element.checked) {
			if(element.attributes.id.nodeValue === ("checkboxheater"+i.toString())) {
				console.log("Sending status on for heater"+i.toString());
				// Ajax call to servlet that will send put to API
				$.get("PostCallServlet?heaterId=heater"+i.toString()+"&state=true", function(responseText) {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
                    $("#somediv").text(responseText);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
                });
				// Update span text
				$("#hvalheater"+i.toString()).text("ON");
				// Update span class
				$("#hvalheater"+i.toString()).addClass('statusON').removeClass('statusOFF');
			}
		} else {
			if(element.attributes.id.nodeValue === ("checkboxheater"+i.toString())) {
				console.log("Sending status off for heater"+i.toString());
				$.get("PostCallServlet?heaterId=heater"+i.toString()+"&state=false", function(responseText) {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
                    $("#somediv").text(responseText);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
                });
				$("#hvalheater"+i.toString()).text("OFF");
				$("#hvalheater"+i.toString()).addClass('statusOFF').removeClass('statusON');
			}
		}
	}
	updateCheckboxes();  
}

//Update window status on checkbox click
function updateWindow(element)
{
	var nbWindows = parseInt($("#wnb").text());
	for (var i=0; i<nbWindows; i++) {
		if (element.checked) {
			if(element.attributes.id.nodeValue === ("checkboxwindow"+i.toString())) {
				console.log("Sending status on for window"+i.toString());
				// Ajax call to servlet that will send put to API
				$.get("PostCallServlet?windowId=window"+i.toString()+"&state=true", function(responseText) {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
                    $("#somediv").text(responseText);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
                });
				// Update span text
				$("#wvalwindow"+i.toString()).text("OPEN");
				// Update span class
				$("#wvalwindow"+i.toString()).addClass('statusOPEN').removeClass('statusCLOSED');
			}
		} else {
			if(element.attributes.id.nodeValue === ("checkboxwindow"+i.toString())) {
				console.log("Sending status off for window"+i.toString());
				$.get("PostCallServlet?windowId=window"+i.toString()+"&state=false", function(responseText) {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
                    $("#somediv").text(responseText);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
                });
				$("#wvalwindow"+i.toString()).text("CLOSED");
				$("#wvalwindow"+i.toString()).addClass('statusCLOSED').removeClass('statusOPEN');
			}
		}
	}
	updateCheckboxes();  
}

//Update orchestrator status on checkbox click
function updateOrchestrator(element)
{
	if (element.checked) {
		console.log("Sending orchestration request");
		// Update span text
		$("#valorchestrator").text("ON");
		// Update span class
		$("#valorchestrator").addClass('statusON').removeClass('statusOFF');
		
	} else {
		console.log("Orchestration off");
		// Update span text
		$("#valorchestrator").text("OFF");
		// Update span class
		$("#valorchestrator").addClass('statusOFF').removeClass('statusON');
	}
	updateChexboxOrchestrator();  
	
}

function updateChexboxOrchestrator() {
	if (document.getElementById("checkboxorchestrator").checked === true) {
		$("#valorchestrator").text("ON");	
		$("#valorchestrator").addClass('statusON').removeClass('statusOFF');
		
		// Send orchestartion request
		$.get("OrchestrationRequestservlet", function(responseText) {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
            $("#somediv").text(responseText);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
        });
	} else {
		$("#valorchestrator").text("OFF");
		$("#valorchestrator").addClass('statusOFF').removeClass('statusON');
	}
}
updateChexboxOrchestrator();
	

// Draw Image in canvas
var img = new Image();
img.onload = function() {
	// Image drawing
	ctx.drawImage(img, 0, 0);

	// Temperature sensors drawing
	var tempSensorsNb = parseInt($("#tsnb").text());
	for (var i=0; i<tempSensorsNb; i++) {
		var tempSensorsId = $("#tsid"+(i.toString())).text();		
		var x = parseInt($("#"+tempSensorsId+"X").text());
		var y = parseInt($("#"+tempSensorsId+"Y").text());
		
		tempSensorsCoords.push({x: x, y: y, i: i});
		drawTempSensor(x, y, i);		
	}
	
	// Heaters drawing
	var heatersNb = parseInt($("#hnb").text());
	for (var i=0; i<heatersNb; i++) {
		var heatersId = $("#hid"+(i.toString())).text();		
		var x = parseInt($("#"+heatersId+"X").text());
		var y = parseInt($("#"+heatersId+"Y").text());
		
		heatersCoords.push({x: x, y: y, i: i});
		
		if ($("#hvalheater"+i.toString()).text()==="OFF") {
			drawHeaterOff(x, y, i);
		} else {
			drawHeaterOn(x, y, i);				
		}	
	}
	
	// Windows drawing
	var windowsNb = parseInt($("#wnb").text());
	for (var i=0; i<windowsNb; i++) {
		var windowsId = $("#wid"+(i.toString())).text();		
		var x = parseInt($("#"+windowsId+"X").text());
		var y = parseInt($("#"+windowsId+"Y").text());
		
		windowsCoords.push({x: x, y: y, i: i});
		
		if ($("#wvalwindow"+i.toString()).text()==="CLOSED") {
			drawWindowClosed(x, y, i);
		} else {
			drawWindowOpen(x, y, i);				
		}	
	}

	// Legend drawing
	drawLegend();
	if ($("#alarmstatus").text()==="ON") {
		drawAlarms();
	}
};
img.src = "img/plan.png";50

// Mouse click management
function getMousePos(canvas, evt) {
	var rect = canvas.getBoundingClientRect();
	return {
		x : evt.clientX - rect.left,
		y : evt.clientY - rect.top
	};
}
canvas.addEventListener('click', function(evt) {
	var mousePos = getMousePos(canvas, evt);
	var message = 'Mouse position: ' + mousePos.x + ',' + mousePos.y;
	console.log(message);
}, false);


// Element hovering management
window.onmousemove = function(e) {	
	
	// Correct mouse position:
	var rect = canvas.getBoundingClientRect();
	var x = e.clientX - rect.left;
	var y = e.clientY - rect.top;
	var i = 0;
	var r;
	
	// Variables needed to display modal & chart
	var toggle = false;
	var toggle2 = false;
	var chartSensorId = "";
	var chartValues = "";
	var chartSensorValues = [];

	// Hovering color (Temperature sensors)
	while(r = tempSensorsCoords[i++]) {
		if ((x>r.x-20) && (x<(r.x+20)) && (y>r.y-20) && (y<(r.y+20))) {
			drawHoveredTemperatureSensor(r.x, r.y, r.i);
			toggle = true;
			
			// Compute jQuery requests to gather values
			var tempSensorsId = $("#tsid"+((i-1).toString())).text();
			var tempSensorVal = $("#tsval"+tempSensorsId).text();			
			$("#modal-value-sensor").text(tempSensorsId);		
			$("#modal-value-value").text(tempSensorVal);
			
			// Get values for graph
			var tempSensorNbValues = $("#tsnbpoints"+tempSensorsId).text();
			for (var i=0; i<tempSensorNbValues; i++) {
				var tempTimestamp = $("#tspointtime"+tempSensorsId+i.toString()).text();
				var tempValue = $("#tspointval"+tempSensorsId+i.toString()).text();
				chartSensorValues.push({x: new Date(tempTimestamp), y: Math.round(tempValue)});
			}
			
			// Assign variables values with selected sensor for graph
			chartSensorId = tempSensorsId;		
			
		} else {
			drawTempSensor(r.x, r.y, r.i);
		}
	}
	
	var s;
	i=0;
	
	// Hovering color (heaters)
	while(s = heatersCoords[i++]) {
		if ((x>s.x-18) && (x<(s.x+18)) && (y>s.y-18) && (y<(s.y+18))) {
			drawHoveredHeater(s.x, s.y, s.i);
			toggle2 = true;
			
			// Compute jQuery requests to gather values
			var heaterId = $("#hid"+((i-1).toString())).text();
			var heaterVal = $("#hval"+heaterId).text();			
			$("#modal-value-actuator").text(heaterId);		
			$("#modal-value-status").text(heaterVal);
		} else {
			if ($("#hvalheater"+(i-1).toString()).text()==="OFF") {
				drawHeaterOff(s.x, s.y, s.i);
			} else {
				drawHeaterOn(s.x, s.y, s.i);				
			}
		}
	}
	
	var s;
	i=0;
	
	// Hovering color (windows)
	while(s = windowsCoords[i++]) {
		if ((x>s.x-18) && (x<(s.x+18)) && (y>s.y-18) && (y<(s.y+18))) {
			drawHoveredWindow(s.x, s.y, s.i);
			toggle2 = true;
			
			// Compute jQuery requests to gather values
			var windowId = $("#wid"+((i-1).toString())).text();
			var windowVal = $("#wval"+windowId).text();			
			$("#modal-value-actuator").text(windowId);		
			$("#modal-value-status").text(windowVal);
		} else {
			if ($("#wvalwindow"+(i-1).toString()).text()==="CLOSED") {
				drawWindowClosed(s.x, s.y, s.i);
			} else {
				drawWindowOpen(s.x, s.y, s.i);				
			}
		}
	}

	// Modal management
	if (toggle) {
		modal.style.display = "block";
		canvasOpen=true;
		
		//--- Defining graph
		
		var chart = new CanvasJS.Chart("chartContainer", {
			width: 1010,
			animationEnabled: true,
			axisY: {
				title: "Temperature (in °C)",
				includeZero: false,
				suffix: " °C"
			},
			data: [{
				name: chartSensorId,
				type: "spline",
				yValueFormatString: "#0.## °C",
				dataPoints: chartSensorValues
			}]
		});
		chart.render();
		
		function toggleDataSeries(e){
			if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
				e.dataSeries.visible = false;
			}
			else{
				e.dataSeries.visible = true;
			}
			chart.render();
		}
		
		//---
	} else {
		modal.style.display = "none";
	}
	
	if (toggle2) {
		modal2.style.display = "block";
	} else {
		modal2.style.display = "none";		
	}
};


/* ------------ Functions to draw stuff in the canvas ------------- */

function drawTempSensor(x, y, nb) {
	drawSensor(x, y, nb, 'rgb(255, 255, 255)', 'rgb(20, 82, 20)');
}

function drawHoveredTemperatureSensor(x, y, nb) {
	drawSensor(x, y, nb, 'rgb(20, 82, 20)', 'rgb(20, 82, 20)');
}

function drawAlarm(x, y) {
	ctx.beginPath();
	ctx.arc(x, y, 32, 0, 2 * Math.PI, false);
	ctx.fillStyle = "rgb(200, 0, 0)"; 
	ctx.fill();
	ctx.lineWidth = 5;
	ctx.strokeStyle = "rgb(70, 0, 0)";
	ctx.stroke();

	ctx.fillStyle = "rgb(70, 0, 0)";
	ctx.font = "bold 32px Arial";
	ctx.fillText("!!", x-10, y+10); 
}

function drawSensor(x, y, nb, color1, color2) {
	ctx.beginPath();
	ctx.arc(x, y, 20, 0, 2 * Math.PI, false);
	ctx.fillStyle = color1; 
	ctx.fill();
	ctx.lineWidth = 5;
	ctx.strokeStyle = color2;
	ctx.stroke();

	ctx.fillStyle = color2;
	ctx.font = "16px Arial";
	ctx.fillText(nb.toString(), x-4, y+6); 
}

function drawHoveredHeater(x, y, nb) {
	drawActuator(x, y, nb, 'rgb(255, 0, 0)', 'rgb(255, 0, 0)');
}

function drawHeaterOff(x, y, nb) {
	drawActuator(x, y, nb, 'rgb(255, 255, 255)', 'rgb(102, 0, 0)');
}

function drawHeaterOn(x, y, nb) {
	drawActuator(x, y, nb, 'rgb(255, 255, 255)', 'rgb(255, 0, 0)');
}

function drawHoveredWindow(x, y, nb) {
	drawActuator(x, y, nb, 'rgb(0, 102, 255)', 'rgb(0, 102, 255)');
}

function drawWindowClosed(x, y, nb) {
	drawActuator(x, y, nb, 'rgb(255, 255, 255)', 'rgb(0, 20, 51)');
}

function drawWindowOpen(x, y, nb) {
	drawActuator(x, y, nb, 'rgb(255, 255, 255)', 'rgb(0, 102, 255)');
}

function drawActuator(x, y, nb, color1, color2) {
	ctx.fillStyle = color1 ;
	ctx.fillRect(x-18, y-18, 36, 36);
	
	ctx.beginPath();
	ctx.lineWidth = 5;
	ctx.strokeStyle = color2;
	ctx.rect(x-18, y-18, 36, 36);
	ctx.stroke();
	

	ctx.fillStyle = color2;
	ctx.font = "16px Arial";
	ctx.fillText(nb.toString(), x-4, y+6); 
}

function drawLegend() {	
	
	// Title
	ctx.fillStyle = 'rgb(0,0,0)';
	ctx.font = "bold 16px Arial";
	ctx.fillText("Sensors :", 30, 60); 
	
	// Temp sensor
	ctx.fillStyle = 'rgb(0,0,0)';
	ctx.font = "16px Arial";
	ctx.fillText("Temperature :", 40, 100); 	
	drawTempSensor(180, 95, 0);
	
	// Title
	ctx.fillStyle = 'rgb(0,0,0)';
	ctx.font = "bold 16px Arial";
	ctx.fillText("Actuators :", 30, 145); 
	
	// Heater 
	ctx.fillStyle = 'rgb(0,0,0)';
	ctx.font = "16px Arial";
	ctx.fillText("Heaters:", 40, 185); 
	ctx.fillText("OFF :", 50, 215); 
	ctx.fillText("ON :", 50, 270); 
	drawHeaterOff(180, 210, 0);
	drawHeaterOn(180, 275, 0);
	
	//Windows
	ctx.fillStyle = 'rgb(0,0,0)';
	ctx.font = "16px Arial";
	ctx.fillText("Windows:", 40, 320); 
	ctx.fillText("CLOSED :", 50, 355); 
	ctx.fillText("OPEN :", 50, 415); 
	drawWindowClosed(180, 350, 0);
	drawWindowOpen(180, 410, 0);
}



function drawAlarms() {
	drawAlarm(880, 80);
}
