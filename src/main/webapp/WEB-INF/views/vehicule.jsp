<!-- Example row of columns -->
<script type="text/javascript">
	$(document).ready(function(){
		$(".nav li.vehicule").addClass("active");
		
		new VehiculeListView({ collection : new VehiculeList()});
	});
</script>
<script type="text/template" id="vehiculeList-template">
	<table class="table">
		<thead>
			<tr>
				<th>Nom</th>
				<th class="text-center">#</th>
				<th>Modèle</th>
				<th>Registration</th>
				<th class="text-center"># Succursale</th>
				<th>&nbsp;</th>
			</tr>
		</thead>
		<tbody>
			<@ _.each(vehiculeList, function(vehicule) { @>
				<tr>
					<td class="name"><@= vehicule.name @></td>
					<td class="number text-center"><@= vehicule.number @></td>
					<td class="model"><@= vehicule.model @></td>
					<td class="registration"><@= vehicule.registration @></td>
					<td class="branch text-center"><@= vehicule.branch @></td>
					<td class="delete"><button class="delete" data-id="<@= vehicule.id @>">Delete</button></td>
				</tr>
			<@}); @>
		</tbody>
	</table>
</script>
<script type="text/template" id="newVehicule-template">
	<tr class="newVehicule">
		<td class="name"><input class="medium" type="text" /></td>
		<td class="text-center number"><input class="small" type="text" /></td>
		<td class="model"><input class="large" type="text" /></td>
		<td class="registration"><input class="medium" type="text" /></td>
		<td class="text-center branch"><input class="small" type="text" /></td>
		<td class="save"><button class="medium">Save</button></td>
	</tr>
</script>
<div class="container">
	<div class="row vehicules">
		<h2>Véhicules</h2>
		<div id="vehiculeList-container"></div>
		<button>Add</button>
	</div>
</div>