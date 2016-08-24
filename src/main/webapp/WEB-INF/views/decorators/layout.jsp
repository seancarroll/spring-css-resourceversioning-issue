<%@ taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ include file="/WEB-INF/views/includes.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title><decorator:title default="Web App" /></title>

<link rel="stylesheet" type="text/css"
	href="<spring:url value="/resources/css/bootstrap.min.css" />" />
<link rel="stylesheet" type="text/css"
	href="<spring:url value="/resources/css/font-awesome.min.css" />" />

<decorator:head />
</head>
<body>
	<nav class="navbar navbar-default navbar-static-top">
		<div class="container"></div>
	</nav>
	<decorator:body />

	<script type="text/javascript"
		src="<spring:url value="/resources/js/jquery-1.11.3.min.js" />"></script>
	<script type="text/javascript"
		src="<spring:url value="/resources/js/bootstrap.min.js" />"></script>

	<script>
		
	</script>
</body>
</html>