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
	   	<link href="resources/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	    <link href="resources/lib/bootstrap-datetimepicker/css/datetimepicker.css" rel="stylesheet">
	    <link href="resources/lib/select2/css/select2.css" rel="stylesheet">
	
	    <!-- Custom styles for this template -->
	    <link href="resources/css/wevan.css" rel="stylesheet">
	    
		<script src="resources/js/libs/jquery.js"></script>
		<script src="resources/js/libs/underscore.js"></script>
	    <script src="resources/js/libs/backbone.js"></script>
	    <script src="resources/lib/bootstrap/js/bootstrap.min.js"></script>
	    <script src="resources/lib/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
	    <script src="resources/lib/jquery.floatThead/js/jquery.floatThead.min.js"></script>
	    <script src="resources/lib/moment/js/moment-with-langs.js"></script>
	    <script src="resources/js/libs/multi-sort.collection.js"></script>
	    <script src="resources/lib/select2/js/select2.min.js"></script>
	    
	  	<!-- Models -->
	  	<script src="resources/js/models/branch.js"></script>
	  	<script src="resources/js/models/contract.js"></script>
	  	<script src="resources/js/models/user.js"></script>
	  	<script src="resources/js/models/vehicule.js"></script>
	  	
	  	<!-- Views -->
	  	<script src="resources/js/views/branchViews.js"></script>
	  	<script src="resources/js/views/contractViews.js"></script>
	  	<script src="resources/js/views/vehiculeViews.js"></script>
    
    	<script type="text/javascript">
    		var ctx = "${pageContext.servletContext.contextPath}";
    		
    		var user = new User();
    		user.fetch({async : false});
    		
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
			<tiles:insertAttribute name="content" />
			<tiles:insertAttribute name="footer" />
		</div>
	</body>
</html>