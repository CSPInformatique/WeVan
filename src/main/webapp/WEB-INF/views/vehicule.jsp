<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<security:authentication property="principal.username" var="username" />

<script type="text/javascript">
	$(document).ready(function(){
		$(".nav li.vehicule").addClass("active");
		
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
</script>

<script type="text/template" id="branchesComboBox-template">
	<select class="branches">
		<@ _.each(branchesList, function(branch) { @>
			<option value="<@= branch.id @>"><@= branch.name @></option>
		<@ }); @>
	</select>
</script>

<script type="text/template" id="vehiculeList-template">
	<table class="table">
		<thead>
			<tr class="active">
				<th>Nom</th>
				<th class="text-center">#</th>
				<th>Modèle</th>
				<th>Immatriculation</th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
			</tr>
		</thead>
		<tbody>
<@	_.each(vehiculeList, function(vehicule) { @>
			<tr>
				<td class="name"><@= vehicule.name @></td>
				<td class="number text-center"><@= vehicule.number @></td>
				<td class="model"><@= vehicule.model @></td>
				<td class="registration"><@= vehicule.registration @></td>
				<td class="edit">
					<button class="btn btn-primary" data-id="<@= vehicule.id @>"><span class="glyphicon glyphicon-edit"></span></button>
				</td>
				<td class="delete">
					<button class="btn btn-danger" data-id="<@= vehicule.id @>"><span class="glyphicon glyphicon-remove"></span></button>
				</td>
			</tr>
<@	}); @>
		</tbody>
	</table>
</script>
<script type="text/template" id="newVehicule-template">
  <input class="id" type="hidden" value="<@= vehicule.id @>" />
  <div class="name form-group">
    <label for="name">Nom du véhicule</label>
    <input type="text" class="form-control" id="name" placeholder="Entrez le nom" value="<@= vehicule.name @>">
  </div>
  <div class="number form-group">
    <label for="number">Numéro</label>
    <input type="number" class="form-control" id="number" placeholder="Numéro" value="<@= vehicule.number @>">
  </div>
  <div class="model form-group">
    <label for="model">Modèle</label>
    <input type="text" class="form-control" id="model" placeholder="Modèle" value="<@= vehicule.model @>">
  </div>
  <div class="registration form-group">
    <label for="registration">Immatriculation</label>
    <input type="text" class="form-control" id="registration" placeholder="Enregistrement" value="<@= vehicule.registration @>">
  </div>
  <div class="branch form-group">
    <label for="branch">Succursale</label>
<@	if(user.hasRole("ADMIN")){	@>
    <select class="form-control" id="branch">
<@	}else{	@>
    <select class="form-control" id="branch" disabled="disabled">
<@	}
	_.each(branchList, function(branch) {
		if(	(vehicule.branch == "" && branch.id == selectedBranch) || 
			(vehicule.branch != "" && branch.id == vehicule.branch.id)
		){	@>
		<option value="<@= branch.id @>" selected="selected"><@= branch.name @></option>
<@		}	@>
		<option value="<@= branch.id @>"><@= branch.name @></option>
<@	});	@>
	</select>
  </div> 
</script>
<div class="row filters">
	<div class="col-xs-12">
		<div class="branches">
			<h4>Succursale</h4>
			<div class="branchesComboBox-container"></div>
		</div>
		<div class="new">
			<button class="btn btn-primary pull-right">Nouveau</button>
		</div>
	</div>
</div>

<div class="row vehicules">
	<div class="table-responsive">
		<div id="vehiculeList-container"></div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12 new"><button class="btn btn-primary">Nouveau</button></div>
</div>

<div class="modal fade vehicule">
  <div class="modal-dialog">
    <div class="modal-content">
      <form role="form">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Véhicule</h4>
        </div>
        <div class="modal-body">
          
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
          <button type="button" class="save btn btn-primary">Enregistrer</button>
        </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->