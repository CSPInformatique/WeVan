<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<security:authentication property="principal.username" var="username" />

<script type="text/javascript">
	$(document).ready(function(){
		$(".nav li.branches").addClass("active");

		var branches = new BranchList();
		
		var branchesView =	new BranchesView({collection : branches});
	});
</script>

<script type="text/template" id="branches-template">
	<table class="table">
		<thead>
			<tr class="active">
				<th>Nom</th>
				<th>Ville</th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
			</tr>
		</thead>
		<tbody>
<@	_.each(branches, function(branch) { @>
			<tr>
				<td class="name"><@= branch.name @></td>
				<td class="city"><@= branch.city @></td>

				<td class="edit">
					<button class="btn btn-primary" data-id="<@= branch.id @>"><span class="glyphicon glyphicon-edit"></span></button>
				</td>
				<td class="delete">
					<button class="btn btn-danger" data-id="<@= branch.id @>"><span class="glyphicon glyphicon-remove"></span></button>
				</td>
			</tr>
<@	}); @>
		</tbody>
	</table>
</script>
<script type="text/template" id="branch-template">
  <input class="id" type="hidden" value="<@= branch.id @>" />
  <input class="active" type="hidden" value="<@= branch.active @>" />
  <div class="name form-group">
    <label for="name">Nom de l'agence</label>
    <input type="text" class="form-control" id="name" placeholder="Entrez le nom" value="<@= branch.name @>">
  </div>
  <div class="addressNumber form-group">
    <label for="addressNumber">Numéro d'adresse</label>
    <input type="number" class="form-control" id="addressNumber" placeholder="Numéro" value="<@= branch.addressNumber @>">
  </div>
  <div class="addressStreet form-group">
    <label for="addressStreet">Rue</label>
    <input type="text" class="form-control" id="addressStreet" placeholder="Rue" value="<@= branch.addressStreet @>">
  </div>
  <div class="companyName form-group">
    <label for="companyName">Nom de la société</label>
    <input type="text" class="form-control" id="companyName" placeholder="Nom de la société" value="<@= branch.companyName @>">
  </div>
  <div class="companyType form-group">
    <label for="companyName">Type de société</label>
    <input type="companyType" class="form-control" id="companyType" placeholder="Type de société" value="<@= branch.companyType @>">
  </div>
  <div class="euVatNumber form-group">
    <label for="euVatNumber">Numéro de TVA européenne</label>
    <input type="text" class="form-control" id="euVatNumber" placeholder="Numéro de TVA européenne" value="<@= branch.euVatNumber @>">
  </div>
  <div class="headOffice form-group">
    <label for="headOffice">Siège social</label>
    <input type="text" class="form-control" id="headOffice" placeholder="Siège social" value="<@= branch.headOffice @>">
  </div>
  <div class="postalCode form-group">
    <label for="postalCode">Code postal</label>
    <input type="text" class="form-control" id="postalCode" placeholder="Code postal" value="<@= branch.postalCode @>">
  </div>
  <div class="city form-group">
    <label for="city">Ville</label>
    <input type="text" class="form-control" id="city" placeholder="Ville" value="<@= branch.city @>">
  </div>
  <div class="country form-group">
    <label for="country">Pays</label>
    <input type="text" class="form-control" id="country" placeholder="Pays" value="<@= branch.country @>">
  </div>
  <div class="phone form-group">
    <label for="phone">Numéro de téléphone</label>
    <input type="text" class="form-control" id="phone" placeholder="Numéro de téléphone" value="<@= branch.phone @>">
  </div>
  <div class="registration form-group">
    <label for="registration">Numéro d'enregistrement</label>
    <input type="text" class="form-control" id="registration" placeholder="Numéro d'enregistrement" value="<@= branch.registration @>">
  </div>
  <div class="registrationDate form-group">
    <label for="registrationDate">Date d'enregistrement</label>
    <input type="text" class="form-control" id="registrationDate" placeholder="Date d'enregistrement" value="<@= branch.registrationDate @>">
  </div>
  <div class="registrationLocation form-group">
    <label for="registrationLocation">Lieu d'enregistrement</label>
    <input type="text" class="form-control" id="registrationDate" placeholder="Lieu d'enregistrement" value="<@= branch.registrationLocation @>">
  </div>
  <div class="siret form-group">
    <label for="siret">Siret</label>
    <input type="text" class="form-control" id="siret" placeholder="Siret" value="<@= branch.siret @>">
  </div>
  <div class="googleEmailAccount form-group">
    <label for="googleEmailAccount">Compte email google</label>
    <input type="text" class="form-control" id="googleEmailAccount" placeholder="Compte email google" value="<@= branch.googleEmailAccount @>">
  </div>
</script>

<div class="row branches">
	<div class="table-responsive">
		<div id="branches-container"></div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12 new"><button class="btn btn-primary">Nouveau</button></div>
</div>

<div class="modal fade branch">
  <div class="modal-dialog">
    <div class="modal-content">
      <form role="form">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Agence</h4>
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