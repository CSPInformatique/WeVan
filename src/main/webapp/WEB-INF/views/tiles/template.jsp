<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<spring:url var="cssUrl" value="/resources/css" />
<spring:url var="jsUrl" value="/resources/js" />

<!DOCTYPE html>
<html lang="en">
	<head>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <meta name="description" content="">
	    <meta name="author" content="">
	
	    <title>WeVan - Gestionnaire de véhicule</title>
	
	    <!-- Bootstrap core CSS -->
	    <link href="resources/css/libs/bootstrap.css" rel="stylesheet">
	
	    <!-- Custom styles for this template -->
	    <link href="resources/css/wevan.css" rel="stylesheet">
	    
		<script src="resources/js/libs/jquery.js"></script>
		<script src="resources/js/libs/underscore.js"></script>
	    <script src="resources/js/libs/backbone.js"></script>
	    <script src="resources/js/libs/bootstrap.min.js"></script>
    
    	<script type="text/javascript">
	    	_.templateSettings = {
	    	    interpolate: /\<\@\=(.+?)\@\>/gim,
	    	    evaluate: /\<\@([\s\S]+?)\@\>/gim,
	    	    escape: /\<\@\-(.+?)\@\>/gim
	    	};
    	</script>
	</head>

	<body>
		<tiles:insertAttribute name="header" />
		<tiles:insertAttribute name="menu" />
	
		<div class="container">
			<div class="body-content">
				<tiles:insertAttribute name="content" />
				<tiles:insertAttribute name="footer" />
			</div>
		</div>
	  	
	  	<!-- Models -->
	  	<script src="resources/js/models/vehicule.js"></script>
	  	
	  	<!-- Views -->
	  	<script src="resources/js/views/vehiculeViews.js"></script>
	</body>
</html>