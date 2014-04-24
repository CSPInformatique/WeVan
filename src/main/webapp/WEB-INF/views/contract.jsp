<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
	var contractPageView;
	var user;
	var router;

	var router = new Backbone.Router({
		routes: {
			":branch/:page/:results/:sortBy/:ascending": "loadPage",  // #1/1/50/startDate/true
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
		$(".contracts").height($(window).innerHeight() - ($("body").innerHeight() - $(".contracts").innerHeight()));
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
			statusString += "/";
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
		
		// Initializing the router.
		Backbone.history.start();
				
		$(".filters select").select2({width: 'resolve' });
		
		// Retreives current user.
		user = new User();
		user.fetch({async : false});
		
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
					contractPageView.model.ascending +
					buildStatusStringForRouter(), 
				{trigger: true}
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
</script>

<script type="text/template" id="branchesComboBox-template">
	<select class="branches">
		<@ _.each(branchesList, function(branch) { @>
			<option value="<@= branch.id @>"><@= branch.name @></option>
		<@ }); @>
	</select>
</script>

<script type="text/template" id="contractPage-template">
	<ul class="pager">
<@	if(!page.firstPage){	@>
		<li class="previous"><a href="#">&larr; Précédent</a></li>
<@	}	@>
		<li>Page <@= page.number + 1 @>
<@	if(!page.lastPage){	@>
		<li class="next"><a href="#">Suivant &rarr;</a></li>
<@	}	@>
	</ul>
	<div class="contracts table-responsive">
		<table class="table table-hover">
			<thead>
				<tr class="active">
					<th>
						<a data-sortBy="id">#</a>
						<img class="ascending hide" src="<c:url value='/resources/img/wevan-ascending.png' />" />
						<img class="descending hide" src="<c:url value='/resources/img/wevan-descending.png' />" />
					</th>
					<th>
						<a data-sortBy="driver.corporateName">Raison sociale</a>
						<img class="ascending hide" src="<c:url value='/resources/img/wevan-ascending.png' />" />
						<img class="descending hide" src="<c:url value='/resources/img/wevan-descending.png' />" />
					</th>
					<th>
						<a data-sortBy="driver.lastName">Nom</a>
						<img class="ascending hide" src="<c:url value='/resources/img/wevan-ascending.png' />" />
						<img class="descending hide" src="<c:url value='/resources/img/wevan-descending.png' />" />
					</th>
					<th>
						<a data-sortBy="startDate">Début</a>
						<img class="ascending hide" src="<c:url value='/resources/img/wevan-ascending.png' />" />
						<img class="descending hide" src="<c:url value='/resources/img/wevan-descending.png' />" />
					</th>
					<th><a data-sortBy="endDate">Fin</a>
						<img class="ascending hide" src="<c:url value='/resources/img/wevan-ascending.png' />" />
						<img class="descending hide" src="<c:url value='/resources/img/wevan-descending.png' />" /></th>
					<th>
						<a data-sortBy="vehicule.name">Véhicule</a>
						<img class="ascending hide" src="<c:url value='/resources/img/wevan-ascending.png' />" />
						<img class="descending hide" src="<c:url value='/resources/img/wevan-descending.png' />" />
					</th>
					<th>
						<a data-sortBy="vehicule.registration">Immatriculation</a>
						<img class="ascending hide" src="<c:url value='/resources/img/wevan-ascending.png' />" />
						<img class="descending hide" src="<c:url value='/resources/img/wevan-descending.png' />" />
					</th>
					<th>Statut</th>
				</tr>
			</thead>
			
			<tbody>
				<@ _.each(page.content, function(contract) { @>	
					<tr data-contract-id="<@= contract.id @>">
						<td class="id"><@= contract.id @></td>
						<td class="corporateName"><@= contract.driver.corporateName @></td>
						<td class="lastName"><@= contract.driver.lastName @></td>
						<td class="startDate"><@= contract.startDate @></td>
						<td class="endDate"><@= contract.endDate @></td>
						<td class="vehicule"><@= contract.vehiculeName @></td>
						<td class="registration"><@= contract.vehiculeRegistration @></td>
						<td class="status">
<@	if(contract.startDate > +moment()){	@>
							En attente
<@	}else if(contract.endDate < +moment()){	@>
							Terminé
<@	}else{	@>
							En cours
<@	}	@>
						</td>
					</tr>
				<@}); @>
			</tbody>
		</table>
	</ul>
	</div>
</script>
<script type="text/template" id="newContract-template">
	<input class="id" type="hidden" value="<@= contract.id @>" />
<@	if(contract.reservationId != null && contract.reservationId != undefined){	@>
	<input class="reservationId" type="hidden" value="<@= contract.reservationId @>" />
<@	}	@>
	<input class="creationDate" type="hidden" value="<@= contract.creationDate @>" />
	<input class="editionDate" type="hidden" value="<@= contract.editionDate @>" />

	<div class="reservation form-group">
    	<label for="reservation">No réservation</label>
<@	if(contract.reservationId == null || contract.reservationId == undefined){	@>
		<div class="form-inline">Aucune</div>
<@	}else{	@>
		<div class="form-inline"><@= contract.reservationId @></div>
<@	}	@>
	</div>

	<div class="driver form-group">
    	<label for="driver">Conducteur Principal</label>
		<input type="text" class="form-control corporateName" placeholder="Société" value="<@= contract.driver.corporateName @>">
		<input type="text" class="form-control firstName" placeholder="Prénom" value="<@= contract.driver.firstName @>">
		<input type="text" class="form-control lastName" placeholder="Nom de famille" value="<@= contract.driver.lastName @>">
		<input type="text" class="form-control driverLicense" placeholder="Permis de conduire" value="<@= contract.driver.driverLicense @>">
	</div>
	<div class="additionalDrivers form-group">
		<label for="additionalDrivers">Conducteur Additionnel</label>
		<button class="btn btn-primary btn-xs addDriver">Ajouter</button>
		<div class="none form-inline">Aucun</div>
	</div>
  	<div class="startDate form-group">
    	<label for="startDate">Date de début</label>
		<input type="text" class="form-control startDate" placeholder="Date de début" value="<@= contract.startDate @>">
	</div>
  	<div class="endDate form-group">
    	<label for="endDate">Date de fin</label>
		<input type="text" class="form-control endDate" placeholder="Date de fin" value="<@= contract.endDate @>">
	</div>
  	<div class="kilometers form-group">
    	<label for="kilometers">Kilomètres</label>
		<input type="text" class="form-control kilometers" placeholder="Kilomètres" value="<@= contract.kilometersPackage @>">
	</div>
  	<div class="amountAlreadyPaid form-group">
    	<label for="totalAmount">Somme déjà versée</label>
		<input type="text" class="form-control amountAlreadyPaid" placeholder="Somme déjà versée" value="<@= contract.amountAlreadyPaid @>">
	</div>
  	<div class="totalAmount form-group">
    	<label for="totalAmount">Montant Total</label>
		<input type="text" class="form-control totalAmount" placeholder="Montant Total" value="<@= contract.totalAmount @>">
	</div>
  	<div class="vehicule form-group">
    	<label for="vehicule">Véhicule</label>
		<div>
			<select>
<@	_.each(vehiculeList, function(vehicule){
		if(vehicule.registration == contract.vehiculeRegistration){	@>
				<option value="<@= vehicule.id @>" selected="selected">
<@		}else{	@>
				<option value="<@= vehicule.id @>">
<@		}	@>
					<@= vehicule.name @> <@= vehicule.number @> - <@= vehicule.registration @>
				</option>
<@	});	@>
			</select>
		</div>
	</div>
	
  	<div class="deductible form-group">
    	<label for="deductible">Franchise</label>
		<input type="text" class="form-control deductible" placeholder="Franchise" value="<@= contract.deductible @>">
	</div>
	
  	<div class="deposit form-group">
    	<label for="deposit">Dépôt de garantie</label>
		<input type="text" class="form-control" placeholder="Dépôt de garantie" value="<@= contract.deposit @>">
	</div>
		
	<div class="options form-group">
		<label for="options">Options</label>		
		<button class="btn btn-primary btn-xs addOption">Ajouter</button>
		<div class="none form-inline">Aucun</div>
	</div>
	
	<div class="showOptionsPrices form-group">
		<label for="showOptionsPrices">Afficher prix des options :&nbsp;</label>
		<input 
			type="checkbox"
<@	if(contract.showOptionsPrices){	@>
			checked="checked"
<@	}	@>
		 />
	</div>
</script>

<script type="text/template" id="additionalDriver-template">
	<div class="driver form-inline" data-driver-id="<@= driver.id @>">
		<div class="form-group">
			<input type="text" class="form-control lastName" placeholder="Nom" value="<@= driver.lastName @>" />
		</div>
		<div class="form-group">
			<input type="text" class="form-control firstName" placeholder="Prénom" value="<@= driver.firstName @>" />
		</div>
		<div class="form-group">
			<input type="text" class="form-control driverLicense" placeholder="Permis de conduire" value="<@= driver.driverLicense @>" />
		</div>
		<div class="form-group">
			<button class="remove btn btn-danger btn-sm"><span class="glyphicon glyphicon-remove"></span></button>
		</div>
	</div>
</script>

<script type="text/template" id="option-template">
	<div class="option form-inline" data-option-id="<@= option.id @>">
		<div class="form-group">
			<input type="text" class="form-control optionLabel" placeholder="Description" value="<@= option.label @>" />
		</div>
		<div class="form-group">
			<input type="text" class="form-control amount" placeholder="Montant" value="<@= option.amount @>" />
		</div>
		<div class="form-group">
			<button class="remove btn btn-danger btn-sm"><span class="glyphicon glyphicon-remove"></span></button>
		</div>
	</div>
</script>

<script type="text/template" id="contractActions-template">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h2 class="modal-title">Contract <@= contract.id @></h2>
	</div>
	<div class="modal-body">
		<h4>Options</h4>
		<div><button data-contract-id="<@= contract.id @>" class="print btn btn-success">Imprimer le contrat</button></div>
		<div><button data-contract-id="<@= contract.id @>" class="edit btn btn-default">Editer le contrat</button></div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
	</div>
</script>


<div class="row filters">
	<div class="col-xs-12">
		<div class="branches">
			<h4>Succursale</h4>
			<div class="branchesComboBox-container"></div>
		</div>
	
		<div class="status">
			<h4>Statut</h4>
			<select multiple="multiple">
				<option value="OPEN">En attente</option>
				<option value="IN_PROGRESS">En cours</option>
				<option value="COMPLETED">Terminé</option>
			</select>
		</div>
		
		<div class="new"><button class="btn btn-primary">Nouveau</button></div>
		
		<div class="clearfix"></div>
	</div>
</div>

<div class="row contractPage">		
	<div class="col-xs-12" id="contractPage-container"></div>
</div>

<div class="editContract modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Contract <span></span></h4>
      </div>
      <div class="modal-body">
        
      </div>
      <div class="modal-footer">
        <button type="button" class="reset btn btn-danger pull-left">Réinitialiser</button>
        <img class="loading" src="<c:url value="/resources/img/loading.gif" />" alt="Loading" />
        <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
        <button type="button" class="print btn btn-success">Imprimer</button>
        <button type="button" class="save btn btn-primary">Enregistrer</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="contractActions modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
        
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->