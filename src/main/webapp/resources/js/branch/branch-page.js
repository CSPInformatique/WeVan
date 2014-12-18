$(document).ready(function(){
	$(".nav li.branches").addClass("active");

	var branches = new BranchList();
	
	var branchesView =	new BranchesView({collection : branches});
});