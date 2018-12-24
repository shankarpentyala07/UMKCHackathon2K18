
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="refresh" content="120">
<title>Home Page</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
	integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU"
	crossorigin="anonymous">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/annyang/2.6.0/annyang.min.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.8.10/themes/smoothness/jquery-ui.css"
	type="text/css">
<script type="text/javascript"
	src="//ajax.aspnetcdn.com/ajax/jquery.ui/1.8.10/jquery-ui.min.js"></script>
</head>

<script>
	$(document).ready(function() {
		$("#myBtn").click(function() {
			$("#myModal").modal();
		});

		$("#rb-no").click(function() {
			$("#mydropdown").show();
		})

		$("#rb-yes").click(function() {
			$("#mydropdown").hide();
		})

		$("#btn-close").click(function() {
			$("#myModal").dialog('close');
		});
	});

	function logout() {
		$("#homeForm").attr("action", "/DoctorQueue/logout").submit();
	}

	function changeAlertStatus() {
		$("#homeForm").attr("action", "/DoctorQueue/changeAlertStatus").submit();
	}

	function getDoctorDet(d) {
		var doctorName = d.getAttribute("data-doctorName");
		var doctorId = d.getAttribute("data-doctorId");
		var doctorNumber = d.getAttribute("data-doctorNumber");
		$('[name="doctorName"]').val(doctorName);
		$('[name="doctorId"]').val(doctorId);
		$('[name="doctorNumber"]').val(doctorNumber);
		$(".modalTitle").html("Appointment with - " + doctorName);
	}

	function joinUserQueue() {
		if (!$('[name="queueReason"]').val().trim()) {
			alert("All fields are required, Please Enter them");
		} else {
			$("#homeForm").attr("action", "/DoctorQueue/joinQueue").submit();
		}
	}
</script>

<style>
#projectIdeas.carousel.slide {
	width: 130%;
	max-width: 350px;
	!
	important
}

div.c-wrapper {
	width: 110%;
	margin: auto;
	align: center;
}
</style>

<body>

	<form id="homeForm" action="get">
		<!--Navigation Bar-->
		<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand " id="userNameDisplay"
						style="color: lightgreen">Welcome ${phoneNumber}</a>
				</div>
				<ul class="nav navbar-nav">
					<li class="active"><a href="#application" data-toggle="pill">Home
							Page</a></li>
					<li><a href="#settings" data-toggle="pill">Info & Settings</a></li>
					<li><a href="#about" data-toggle="pill">About</a>
					<li><a href="#maps" data-toggle="pill">Hospital Locations</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<button class="btn btn-danger navbar-btn" onclick="logout()">
						<span class="glyphicon glyphicon-log-out"></span>Logout
					</button>
				</ul>
			</div>
		</nav>

		<div class="container">
			<div class="tab-content">
				<div id="application" class="tab-pane fade in active">
					<div class="row first-row">
						<div class="well well-sm" style="margin-left: 10%; width: 70%">
							&nbsp;&nbsp;This page auto re-loads every 2 minutes to show you
							your changed Wait time.You can see your Requests (If any) Below</div>

						<c:forEach var="docDetail" items="${doctorQueueDoctors}">
							<div class="col-lg-4">
								<div class="card">
									<img src="${docDetail.doctorURL}"
										style="width: 80%; height: 300px">
									<h3>${docDetail.doctorName}</h3>
									<h5>Specialization : ${docDetail.doctorSpecialization}</h5>
									<h5 class="title">
										<b>${docDetail.doctorHospital}</b>
									</h5>
									<p>Waiting Time : ${docDetail.visitorsWaitTime}</p>
									<p>Current Number of Visitors :
										${docDetail.numberOfVisitors}</p>
									<p>
										<button type="button" class="joinQueue btn btn-primary"
											data-doctorName="${docDetail.doctorName}"
											data-doctorId="${docDetail.doctorId}"
											data-doctorNumber="${docDetail.doctorPhone}"
											data-toggle="modal" onclick="getDoctorDet(this)"
											data-target="#joinQueue">Join Queue</button>
									</p>
								</div>
							</div>
						</c:forEach>

						<div class="modal fade" id="joinQueue" role="dialog">
							<div class="modal-dialog modal-lg">

								<!-- Modal content-->
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close btn-cross"
											data-dismiss="modal">&times;</button>
										<h4 class="modalTitle modal-title">Modal Header</h4>
									</div>
									<div class="modal-body">
										<input type="hidden" name="doctorId" /> <input type="hidden"
											name="doctorName" /> <input type="hidden"
											name="doctorNumber" /> <span class="app-toggle">Is
											Appointment for you</span>
										<div class="radio">
											<label><input id="rb-yes" value="Y" type="radio"
												name="optradio" checked>Yes</label> <label><input
												id="rb-no" type="radio" value="N" name="optradio">No</label>
										</div>
										<br />
										<div class="form-control">
											<label for="queueReason">Enter Reason : </label> <input
												type="text" placeholder="Enter Reasons" name="queueReason"
												required="required" /> <br />
										</div>
										<br />
										<div class="form-control" id="mydropdown"
											style="display: none; width: auto">
											Please Select a Relationship <select name="relationShip"
												required="required">
												<option value="Spouse">Spouse</option>
												<option value="Children">Children</option>
												<option value="Relative">Relative</option>
												<option value="Friend">Friend</option>
												<option value="Other">Other</option>
											</select>
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" onclick="joinUserQueue()"
											class="btn btn-success btn-join">Join</button>
										<button type="button" class="btn btn-default btn-close"
											data-dismiss="modal">Close</button>
									</div>
								</div>

							</div>
						</div>

					</div>
					<br />
					<div style="align: center;">
						<c:if test="${! empty allDoctorQueueWaitTimesByPhn}">
							<div class="well well-lg">
								<strong><u>Your Requests</u></strong>
								<ol>
									<c:forEach var="docQueueDetail"
										items="${allDoctorQueueWaitTimesByPhn}">
										<li>Doctor Name : ${docQueueDetail.doctorName} <br />
											Reason : ${docQueueDetail.reason} <br /> Wait Time :
											${docQueueDetail.waitTime} <br /> For Whom ? :
											${docQueueDetail.relationship}
										</li>
										<hr
											style="height: 1px; border: none; color: #333; background-color: #333;" />
									</c:forEach>
								</ol>
							</div>
						</c:if>
					</div>
				</div>

				<div id="about" class="tab-pane fade ">
					<div class="col-sm-12">
						<h2 style="color: black" align="center">
							<strong>About Page</strong>
						</h2>
						<div class="media-body">
							<h3 style="color: black">
								<strong> <i>Application Objective </i>
								</strong>
							</h3>
							<p style="color: chocolate; font-size: 18px;"
								class="well  well-md" align="justify">
								<i>Developing the most interactive app which allows the
									patients to join an online virtual queue. For this application,
									patient can login by providing the phone number and entering
									the verification code which will be sent to the entered mobile
									number. A previlige for choosing the doctor (Details of the
									doctor will be provided too) and joining their queue will be
									given for the patients. Once the patient he/she will be getting
									updates about the queue.</i>
							</p>
							<h3 style="color: black">
								<strong><i>Application Motivation</i></strong>
							</h3>
							<p style="color: chocolate; font-size: 18px;"
								class="well  well-md" align="justify">
								<i>To reduce the efforts of patients by introducing smart
									queue option if they are planning to opt for walk in
									emergencies, If a sick person needs to see a doctor for herself
									or for her child, the only option for her is to go to a clinic,
									get her name on the list and wait for her turn patiently.
									Sometimes the wait can last for several hours. It is not only
									inconvenient for patients to sit in a crowded and dingy waiting
									room, it creates unnecessary cross contamination and infection.
									Clinics need a way to reduce or eliminate wait times and
									minimize the discomfort of patients. Patients need an easier
									way to get information on actual wait times, choose the right
									doctor and checkin from their mobile device, and even pay for
									services directly from their mobile device.</i>
							</p>
						</div>
					</div>

				</div>

				<div id="settings" class="tab-pane fade">
					<div class="col-sm-6">
						<div class="tab-content">
							<div class="media-body ">
								<h4 style="color: chocolate">Your Settings</h4>
								<p class="well">You can change your settings.</p>
							</div>
							<p>
								Your Current Alert Status : <strong>${alertStatus}</strong>
							</p>
							<div class="col-sm-2">
								<label>Alerts? </label>
							</div>
							<label><input type="radio" name="alert_yes_no" value="Y"
								id="yes" checked> Yes</label> <label><input type="radio"
								name="alert_yes_no" value="N" id="no"> No</label>

							<div class="col-xs-12">
								<br>
								<button class="btn btn-md btn-success"
									onclick="changeAlertStatus()">
									<i class="glyphicon glyphicon-ok-sign"></i> Save
								</button>
							</div>
						</div>

					</div>
					<div class="col-sm-6">
						<div class="media-body ">
							<h4 style="color: chocolate"
								class="glyphicon  glyphicon-info-sign">Information</h4>
							<div class="well well-sm">
								Send <strong>'Y'</strong> to <b>+16203749149</b> to subscribe to
								get alerts
							</div>
							<div class="well well-sm">
								Send <strong>'N'</strong> to <b>+16203749149</b> to un-subscribe
								for alerts
							</div>
							<div class="well well-sm">
								Send <strong>'W'</strong> to <b>+16203749149</b> to get your
								waiting time in the queue
							</div>
							<div class="well well-sm">Queue Times will stop at 6PM and
								resumes at 9AM.</div>
							<div class="well well-sm">This page auto re-loads every 2
								minutes to show you your changed Wait time.</div>

						</div>
					</div>
				</div>

				<div id="maps" class="tab-pane fade">
					<div id="map"
						style="width: 100%; height: 100%; position: absolute;"
						class="col-sm-6"></div>
					<script>
            var map, marker, i;
            function initMap() {
               // Try HTML5 geolocation.
                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition(function (position) {
                        $(document).ready(function (){
                            $.get('https://api.foursquare.com/v2/venues/search?client_id=XUE5OHFJNUDACMIFWVF5XMCUFUKPSWXTBBTSO1PHBFL0BGDK&client_secret=Z0RUZIJ4W5M3YLFHDQRL1VL2WQWDJHYSPJKXO2SKFALLHU0U&v=20180223&limit=3&ll=' + position.coords.latitude + ',' + position.coords.longitude + '&query=pharmacy', function (data, status) {
                                console.log(data.response.venues);
                                var location = new google.maps.LatLng(data.response.venues[0].location.lat, data.response.venues[0].location.lng);
                                var options = {
                                    center: location,
                                    zoom: 13,
                                    zoomControl: true

                                };
                                var map = new google.maps.Map(document.getElementById('map'), options);
                                for (i = 0; i < data.response.venues.length; i++) {
                                    var position = new google.maps.LatLng(data.response.venues[i].location.lat, data.response.venues[i].location.lng);
                                    var mstoreMarker = new google.maps.Marker({
                                        position: position,
                                        label:{
                                            text: data.response.venues[i].location.address,
                                            color: 'black',
                                            fontSize: "20px"
                                    }
                                    });
                                    mstoreMarker.setMap(map);
                                    var latlng = {lat: data.response.venues[i].location.lat,lng: data.response.venues[i].location.lat};
                                    var title = data.response.venues[i].location.address;
                                }



                            });
                        });
                     });
                }}
            </script>
					<script async defer
						src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDdlkNxOOHudIwmmfJXoUQyPKcPmtYBbFY&callback=initMap">
        </script>
				</div>

			</div>
		</div>
	</form>
</body>