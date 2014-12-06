<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bucket Access Consumer</title>
<script src="jquery.js"></script>
<script type="text/javascript">
	var url = 'BaseBucketConsumerServlet'
	$(document).ready(function() {
		$.ajaxSetup({
			cache : false
		});
		setInterval(function() {
			$("#refresh").load(url);
		}, 10000);
	});
</script>

<style>
table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
}

th, td {
	padding: 5px;
}
</style>

</head>
<body>
	<div id="refresh">
		<jsp:include page="/BaseBucketConsumerServlet"></jsp:include>	
	</div>
</body>
</html>