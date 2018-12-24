<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Doctor Queue Login Page</title>
<meta charset="UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style>
.phn-num {
	margin-bottom: 30px;
}

.form-group {
	width: 50%;
}

.myform {
	padding-top: 130px;
}
</style>

</head>
<body
	style="background: url(https://truecolorsintl.com/dev/wp-content/uploads/2013/05/iStock_000028749234_Small.jpg); background-repeat: no-repeat; background-size: cover;">

	<script>
		function submitForm() {
			if(!$("#verificationCode").val().trim()){
				alert("Please Enter a Verification Code");
			} else{
			$("#loginForm").attr("action", "/DoctorQueue/doctorQueueHome")
					.submit();
			}
		}

		// Send Verification Code to Login
		function sendVerificationCode() {
			if(!$("#phoneNumber").val().trim()){
				alert("Please Enter a Number");
			} else{
			$("#loginForm").attr("action", "/DoctorQueue/getSmsVerificationCode")
					.submit();
			}
		}
	</script>
	<h1 style="margin-left: 40%;color: #dff0d8;">Doctor Queue Login Page</h1>	
	<div class="container-fluid">	
		<div class="row myform">
			<div class="col-lg-5"></div>
			<form id="loginForm" method="get">
				<div class="col-lg-5">
					<div class="row form-group phn-num">
						<label for="phoneNumber">Enter Your Phone Number</label>
						<c:if test="${isSmsVerify eq null}">
							<input class="form-control" type="text" name="phoneNumber"
								id="phoneNumber" placeholder="Enter Your Phone Number" />
						</c:if>
						<c:if test="${isSmsVerify eq true}">
							<input class="form-control" type="text" name="phoneNumber" id="phoneNumber"
								style="color: gray" value="${smsVerifyNumber}"
								readonly="readonly" />
						</c:if>
					</div>

					<c:if test="${isSmsVerify eq true}">
						<div class="row form-group phn-num">
							<label for="verificationCode">Enter Your Verification
								Code</label> <input type="text" name="verificationCode"
								id="verificationCode" class="form-control" />
							<c:if test="${codeWrong eq true}">
								<p style="color: red;">Please Enter Your Valid Verification
									Code</p>
							</c:if>
						</div>
					</c:if>

					<div class=>
						<c:if test="${isSmsVerify eq null}">
							<div class="col-lg-2">
								<button class="btn btn-primary"
									onclick="sendVerificationCode();">Get Verification
									Code</button>
							</div>
						</c:if>
						<c:if test="${isSmsVerify eq true}">
							<div class="col-lg-2">
								<button type="button" onclick="sendVerificationCode();"
									class="btn btn-primary">Resend</button>
							</div>
							<div class="col-lg-2">
								<button type="button" onclick="submitForm();"
									class="btn btn-primary">Login</button>
							</div>
						</c:if>
					</div>
				</div>

			</form>
		</div>
	</div>
</body>
</html>