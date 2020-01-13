<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Smart Building Dashboard</title>

<!-- Bootstrap core CSS-->
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom fonts for this template-->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">

<!-- Page level plugin CSS-->
<link href="vendor/datatables/dataTables.bootstrap4.css"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="css/sb-admin.css" rel="stylesheet">

<link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">

</head>

<body id="page-top">

	<nav class="navbar navbar-expand static-top">

		<div class="title mr-1">Smart Building
			Dashboard</div>
			
		<button class="btn refresh-button" value="Refresh Page" onClick="window.location.reload()">Refresh</button>

	</nav>

	<div id="wrapper">
		<!-- Sidebar -->
		<ul class="sidebar navbar-nav">
			<li class="nav-item"><a class="nav-link" href="">
					<i class="fas fa-thermometer-half"></i> <span>&nbsp;&nbsp;Temperature</span>
			</a></li>
			
			
			<li>
				<div class="shadow">
					<div class="container">
						<p class="sidebar-element-title">Sensors</p>
						
						<ul>
						
							<p> test </p>
							
						</ul>
					</div>
				</div>
			</li>
			<li>
				<div class="shadow">
					<div class="container">
						<p class="sidebar-element-title">Heaters</p>
							
						<ul>
							<p> test </p>
						</ul>
					</div>
				</div>
			</li>
			
			<li class="nav-item"><a class="nav-link" href="">
					<i class="fas fa-door-open"></i> <span>&nbsp;&nbsp;Room status</span>
			</a></li>
			
			<li>
				<div class="shadow">
					<div class="container">
						<p class="sidebar-element-title">Windows</p>
							
						<ul>
							<p> test </p>
						</ul>
					</div>
				</div>
			</li>
			<li class="nav-item"><a class="nav-link" href="">
					<i class="fas fa-exclamation-circle"></i> <span>&nbsp;&nbsp;Alarms</span>
			</a></li>
			<li>
				<div class="shadow">
					<div class="container">
						<p class="sidebar-element-title">Alarms</p>
							
						<ul>
							<p> test </p>
						</ul>
					</div>
				</div>
			</li>	
			
			<li class="nav-item"><a class="nav-link" href="">
					<i class="fas fa-lightbulb"></i> <span>&nbsp;&nbsp;Orchestrator</span>
			</a></li>
			<li>
				<div class="shadow">
					<div class="container">
						<p class="sidebar-element-title">Orchestrator</p>
						
						<ul>
							<li> <span>Orchestrator</span> : <b><span id="valorchestrator" class="statusOFF">OFF</span></b> <br/>
							<label class="switch">
							<input onchange="updateOrchestrator(this);" id="checkboxorchestrator" type="checkbox">
							<span class="slider round"></span>
							</label>
							</li>

						</ul>
							
					</div>
				</div>
			</li>
		</ul>
		
		

		<div id="content-wrapper">

			<div class="container-fluid">
				<div class="centered">		
					<canvas id="myCanvas" width=1000 height="500" style="border:1px solid #0f0c29;"></canvas>
				
					 
					<div id="myModal" class="modal">
					  <div class="modal-content" style="height: 500px;">
					    <p><span id="modal-value-sensor"></span> : <b><span id="modal-value-value"></span></b></p><br/>
					    <div id="chartContainer" style=""></div>
					  </div>					
					</div>
					
					<div id="myModalNoGraph" class="modal">					
					  <div class="modal-content">
					    <p><span id="modal-value-actuator"></span> : <b><span id="modal-value-status"></span></b></p><br/>
					  </div>					
					</div> 
					
				</div>
				<div style="display:none;">
				  <img id="plan" src="img/plan.png"
				       width="1000" height="500">
				</div>

			</div>
		</div>
	</div>


	

	<!-- Bootstrap core JavaScript-->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Page level plugin JavaScript-->
	<script src="vendor/chart.js/Chart.min.js"></script>

	<script src="js/smartBuilding.js"></script>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>


</body>

</html>
