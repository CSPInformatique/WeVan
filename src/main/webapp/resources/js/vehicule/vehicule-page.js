$(document).ready(function(){
	$(".nav li.vehicule").addClass("active");

	// Retreives current user.
	user = new User();
	user.fetch({async : false});
	
	// If the user is an admin. load all branch list.
	if(user.hasRole("ADMIN")){
		var branchesComboBoxView = new BranchesComboBoxView({collection : new BranchList()});
		
		branchesComboBoxView.collection.fetch({success : function(){
			var vehiculeList = new VehiculeList();
			vehiculeList.branch = $("select.branches").select2("val");
			
			var vehiculeListView =	new VehiculeListView({collection : vehiculeList});

			$("select.branches").on("change", function(e){
				vehiculeListView.collection.branch = e.val;
				vehiculeListView.collection.fetch();
			});
		}});
	}else{
		// Hide combox box container.
		$(".branchesComboBox-container").hide();
		
		// If the user not an admin, load all vehicules for his branch.
		var vehiculeList = new VehiculeList();
		vehiculeList.branch = user.toJSON().branch.id;
		
		new VehiculeListView({ collection : vehiculeList});	
	}
});