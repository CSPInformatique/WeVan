<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
	$(document).ready(function(){
		$(".nav li.contract").addClass("active");
		
		$(".filters select").select2({width: 'resolve' });
		
		// Retreives current user.
		var user = new User();
		
		user.fetch({async : false});
		
		// If the user is an admin. load all branch list.
		if(user.hasRole("ADMIN")){
			var branchesComboBoxView = new BranchesComboBoxView({collection : new BranchList()});
			
			branchesComboBoxView.collection.fetch({success : function(){
				var contractList = new ContractList();
				contractList.branch = $("select.branches").select2("val");
				
				var statusData = $(".status select").val();
				if(statusData.length > 0){
					contractList.status = [];
					for(statusIndex in statusData){
						contractList.status.push(statusData[statusIndex]);
					}
				}
				
				var contractListView =	new ContractListView({collection : contractList});

				$("select.branches").on("change", function(e){
					contractListView.collection.branch = e.val;
					contractListView.collection.fetch();
				});

				$(".status select").on("change", function(e){
					var statusData = $(".status select").select2("data");
					if(statusData.length > 0){
						contractListView.collection.status = [];
						for(statusIndex in statusData){
							contractListView.collection.status.push(statusData[statusIndex].id);
						}
					}else{
						contractListView.collection.status = null;
					}
					contractListView.collection.fetch();
				});
			}});
		}else{
			// Hide combox box container.
			$(".branches").hide();

			// If the user not an admin, load all contract for his branch.
			var contractList = new ContractList();
			contractList.branch = user.toJSON().branch.id;
			
			var statusData = $(".status select").select2("val");
			if(statusData.length > 0){
				contractList.status = [];
				for(statusIndex in statusData){
					contractList.status.push(statusData[statusIndex]);
				}
			}
			
			var contractListView = new ContractListView({ collection : contractList});	
			
			$(".status select").on("change", function(e){
				var statusData = $(".status select").select2("data");
				if(statusData.length > 0){
					contractListView.collection.status = [];
					for(statusIndex in statusData){
						contractListView.collection.status.push(statusData[statusIndex].id);
					}
				}else{
					contractListView.collection.status = null;
				}
				contractListView.collection.fetch();
			});
		}
	});
</script>

<script type="text/template" id="branchesComboBox-template">
	<select class="branches">
		<@ _.each(branchesList, function(branch) { @>
			<option value="<@= branch.id @>"><@= branch.name @></option>
		<@ }); @>
	</select>
</script>

<script type="text/template" id="contractList-template">
	<div class="table-responsive">
		<table class="table table-hover">
			<thead>
				<tr class="active">
					<th>#</th>
					<th>Nom</th>
					<th>Prénom</th>
					<th>Début</th>
					<th>Fin</th>
					<th>Véhicule</th>
					<th>Statut</th>
				</tr>
			</thead>
			<tbody>
				<@ _.each(contractList, function(contract) { @>	
					<tr data-contract-id="<@= contract.id @>">
						<td class="id"><@= contract.id @></td>
						<td class="lastName"><@= contract.driver.lastName @></td>
						<td class="firstName"><@= contract.driver.firstName @></td>
						<td class="startDate"><@= contract.startDate @></td>
						<td class="endDate"><@= contract.endDate @></td>
						<td class="vehicule"><@= contract.vehicule.name @> <@= contract.vehicule.number @></td>
						<td class="status">
<@	if(contract.status == "OPEN"){	@>
							Ouvert
<@	}else if(contract.status == "IN_PROGRESS"){	@>
							En cours
<@	}else if(contract.status == "COMPLETED"){	@>
							Terminé
<@	}else if(contract.status == "CANCELLED"){	@>
							Annulé
<@	}	@>
						</td>
					</tr>
				<@}); @>
			</tbody>
		</table>
	</div>
</script>
<script type="text/template" id="newContract-template">
	<input class="id" type="hidden" value="<@= contract.id @>" />
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
		<input type="text" class="form-control kilometers" placeholder="Kilomètres" value="<@= contract.kilometers @>">
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
		if(contract.vehicule != null && vehicule.id == contract.vehicule.id){	@>
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
    	<textarea class="form-control" cols="40" rows="8"><@= contract.options @></textarea>
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

<script type="text/template" id="contractActions-template">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h2 class="modal-title">Contract <@= contract.id @></h2>
	</div>
	<div class="modal-body">
		<h4>Actions</h4>
		<div><button data-contract-id="<@= contract.id @>" class="print btn btn-success">Imprimer le contrat</button></div>
<@	if(contract.status == "OPEN"){	@>
		<div><button data-contract-id="<@= contract.id @>" data-status="IN_PROGRESS" class="btn btn-primary">Démarrer le contrat</button></div>
<@	}
	if(contract.status == "IN_PROGRESS"){	@>
		<div><button data-contract-id="<@= contract.id @>" data-status="COMPLETED" class="btn btn-primary">Terminer le contrat</button></div>
<@	}
	if(contract.status != "CANCELLED"){	@>
		<div><button data-contract-id="<@= contract.id @>" data-status="CANCELLED" class="btn btn-danger">Annuler le contrat</button></div>
<@	}
	if(contract.status != "OPEN"){	@>
		<div><button data-contract-id="<@= contract.id @>" data-status="OPEN" class="btn-warning">Réinitialiser le contrat</button></div>
<@	}	@>
		<h4>Options</h4>
		<div><button data-contract-id="<@= contract.id @>" class="edit btn btn-default">Editer le contrat</button></div>
		<div><button data-contract-id="<@= contract.id @>" class="delete btn btn-danger">Supprimer le contrat</button></div>
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
				<option value="OPEN" selected="selected">Ouvert</option>
				<option value="IN_PROGRESS" selected="selected">En cours</option>
				<option value="COMPLETED">Terminé</option>
				<option value="CANCELLED">Annulé</option>
			</select>
		</div>
		
		<div class="clearfix"></div>
	</div>
</div>

<div class="row contractList">		
	<div class="col-xs-12" id="contractList-container"></div>
</div>

<div class="row">
	<div class="col-xs-12 new"><button class="btn btn-primary">Nouveau</button></div>
</div>

<div class="editContract modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Contract</h4>
      </div>
      <div class="modal-body">
        
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
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