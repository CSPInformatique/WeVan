<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE html>
<html lang="en">
	<head>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <meta name="description" content="">
	    <meta name="author" content="">
	
	    <title>WeVan - Gestionnaire de véhicule</title>
	
	    <!-- Bootstrap core CSS -->
	   	<link href="<c:url value="/resources/lib/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
	    <link href="<c:url value="/resources/lib/bootstrap-datetimepicker/css/datetimepicker.css" />" rel="stylesheet">
	    <link href="<c:url value="/resources/lib/select2/css/select2.css" />" rel="stylesheet">
	
	    <!-- Custom styles for this template -->
	    <link href="<c:url value="/resources/css/wevan.css" />" rel="stylesheet">
	    
		<script src="<c:url value="/resources/js/libs/jquery.js" />"></script>
		<script src="<c:url value="/resources/js/libs/underscore.js" />"></script>
	    <script src="<c:url value="/resources/js/libs/backbone.js" />"></script>
	    <script src="<c:url value="/resources/lib/bootstrap/js/bootstrap.min.js" />"></script>
	    <script src="<c:url value="/resources/lib/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" />"></script>
	    <script src="<c:url value="/resources/lib/jquery.floatThead/js/jquery.floatThead.min.js" />"></script>
	    <script src="<c:url value="/resources/lib/moment/js/moment-with-langs.js" />"></script>
	    <script src="<c:url value="/resources/js/libs/multi-sort.collection.js" />"></script>
	    <script src="<c:url value="/resources/lib/select2/js/select2.min.js" />"></script>
	    
	  	<!-- Models -->
	  	<script src="<c:url value="/resources/js/models/branch.js" />"></script>
	  	<script src="<c:url value="/resources/js/models/contract.js" />"></script>
	  	<script src="<c:url value="/resources/js/models/user.js" />"></script>
	  	<script src="<c:url value="/resources/js/models/vehicule.js" />"></script>
	  	
	  	<!-- Views -->
	  	<script src="<c:url value="/resources/js/views/branchViews.js" />"></script>
	  	<script src="<c:url value="/resources/js/views/contractViews.js" />"></script>
	  	<script src="<c:url value="/resources/js/views/vehiculeViews.js" />"></script>
    
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