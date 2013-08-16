<!-- Example row of columns -->
<script type="text/javascript">
	$(document).ready(function(){
		$(".nav li.vehicule").addClass("active");
		
		new VehiculeListView({ collection : new VehiculeList()});
	});
</script>
<script type="text/template" id="vehiculeList-template">
	<table>
		<tr>
			<th>#</th>
			<th>Nom</th>
			<th>Modèle</th>
			<th>Registration</th>
			<th># Succursale</th>
		</tr>
		<@ _.each(vehiculeList, function(vehicule) { @>
			<tr>
				<td class="id"><@= vehicule.id @></td>
				<td class="name"><@= vehicule.name @></td>
				<td class="model"><@= vehicule.model @></td>
				<td class="registration"><@= vehicule.registration @></td>
				<td class="branch"><@= vehicule.branch @></td>
				<td class="delete"><button class="delete" data-id="<@= vehicule.id @>">Delete</button></td>
			</tr>
		<@}); @>
	</table>
</script>
<script type="text/template" id="newVehicule-template">
	<tr class="newVehicule">
		<td>&nbsp;</td>
		<td class="name"><input type="text" /></td>
		<td class="model"><input type="text" /></td>
		<td class="registration"><input type="text" /></td>
		<td class="branch"><input type="text" /></td>
		<td class="save"><button>Save</button></td>
	</tr>
</script>

        
<div class="row vehicules">
	<h2>Véhicules</h2>
	<div id="vehiculeList-container"></div>
	<button>Add</button>
</div>