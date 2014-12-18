var contractPageView;
var user;
var router;

var router = new Backbone.Router({
	routes: {
		":branch/:page/:results/:sortBy/:ascending": "loadPage",  // #1/1/50/startDate/true
		":branch/:page/:results/:sortBy/:ascending/": "loadPage",  // #1/1/50/startDate/true
		":branch/:page/:results/:sortBy/:ascending/:status": "loadPageWithStatus"  // #1/1/50/startDate/true/IN_PROGRESS&OPEN
	}
});

router.on(
	"route:loadPage",
	function(branch, pageNumber, results, sortBy, ascending) {
		loadPage(branch, pageNumber, results, sortBy, ascending, null);
	}
);

router.on(
	"route:loadPageWithStatus",
	function(branch, pageNumber, results, sortBy, ascending, status) {
		loadPage(branch, pageNumber, results, sortBy, ascending, status);
	}
);

var adjustTableSize = function(){
	$(".contracts").height($(window).innerHeight() - ($("body").innerHeight() - $(".contracts").innerHeight() + $("footer").innerHeight()));
	/*var tableHeight = $(window).height();
	
	console.log("Window height : " + tableHeight);
	
	$(".navbar, .actions, .filters, .pager, footer").each(function(index, element){
		console.log("Removing " +  $(element).innerHeight() + " from " + element + " .");
		tableHeight = tableHeight - $(element).innerHeight();
	});
	
	console.log("Setting table height to " + tableHeight);
	
	$(".contracts").height(tableHeight);*/
};

var buildStatusStringForRouter = function(){		
	var statusString = "";
	var statusData = $(".status select").select2("data");
	
	if(statusData.length > 0){
		for(statusIndex in statusData){
			if(statusString != "") statusString += "&";
			statusString += statusData[statusIndex].id;
		}
	}
	
	return statusString;
};

var getBranchForRouter = function(){
	if(user.hasRole("ADMIN")){
		return $("select.branches").val();
	}else{
		return user.toJSON().branch.id;
	}
};

var loadPage = function(branch, pageNumber, results, sortBy, ascending, status){		
	var page = new ContractPage();
			
	// Setting branch
	if(user.hasRole("ADMIN")){
		page.branch = branch;
	}else{
		page.branch = user.toJSON().branch.id;
	}
	
	// Setting page number.
	page.page = pageNumber;
	
	// Setting page results.
	page.results = results;
	
	// Setting sortBy
	page.sortBy = sortBy;
	
	// Setting ascending
	page.ascending = ascending;
	
	// Settings the status.	
	if(status != null){
		var statusData = status.split("&");
		
		page.status = [];
		for(statusIndex in statusData){
			page.status.push(statusData[statusIndex]);
		}
	}
		
	// Loading contrat page view.
	contractPageView =	new ContractPageView({model : page});
};

$(document).ready(function(){
	$(".nav li.contract").addClass("active");
	
	// Retreives current user.
	user = new User();
	user.fetch({async : false});
	
	// Initializing the router.
	Backbone.history.start();
			
	$(".filters select").select2({width: 'resolve' });
	

	
	// If the user is an admin. load all branch list.
	if(user.hasRole("ADMIN")){
		var branchesComboBoxView = new BranchesComboBoxView({collection : new BranchList()}).collection.fetch({async: false});
	}else{
		// Hide combox box container.
		$(".branches").hide();
	}
	
	$(".status select, select.branches").on("change", function(e){
		router.navigate(
			getBranchForRouter() + "/0/" + 
				contractPageView.model.results + "/" +
				contractPageView.model.sortBy + "/" +
				contractPageView.model.ascending + "/" +
				buildStatusStringForRouter(), 
			{trigger: true}
		);
	});
	
	$(".reload button").click(function(){
		loadPage(
			getBranchForRouter(), 
			contractPageView.model.page, 
			contractPageView.model.results, 
			contractPageView.model.sortBy, 
			contractPageView.model.ascending, 
			buildStatusStringForRouter()
		);
	});
	
	if(location.hash == ""){
		router.navigate(
			getBranchForRouter() + "/0/50/startDate/true/OPEN&IN_PROGRESS", 
			{trigger: true}
		);
	}
	
	$(window).resize(function(){
		adjustTableSize();
	});
});