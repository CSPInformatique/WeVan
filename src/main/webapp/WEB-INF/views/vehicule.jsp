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
				<th>Enregistrement</th>
				<th class="text-center"># Succursale</th>
				<th>&nbsp;</th>
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
					<td class="edit">
						<button class="btn btn-primary" data-id="<@= vehicule.id @>"><span class="glyphicon glyphicon-edit"></span></button>
					</td>
					<td class="delete">
						<button class="btn btn-danger" data-id="<@= vehicule.id @>"><span class="glyphicon glyphicon-remove"></span></button>
					</td>
				</tr>
			<@}); @>
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
    <label for="registration">Enregistrement</label>
    <input type="text" class="form-control" id="registration" placeholder="Enregistrement" value="<@= vehicule.registration @>">
  </div>
  <div class="branch form-group">
    <label for="registration">Numéro succursale</label>
    <input type="text" class="form-control" id="registration" placeholder="Numéro succursale" value="<@= vehicule.branch @>">
  </div> 
</script>
<div class="container">
	<div class="row vehicules">
		<h2>Véhicules</h2>
		<div id="vehiculeList-container"></div>
		<div class="new"><button class="btn btn-primary">Nouveau</button></div>
	</div>
</div>

<div class="modal fade">
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