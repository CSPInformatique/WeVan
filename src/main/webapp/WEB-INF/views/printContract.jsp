<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<% pageContext.setAttribute("newLineChar", "\n"); %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="Content-type" content="text/html;charset=UTF-8" />
    
    <link href="<c:url value='/resources/lib/bootstrap/css/bootstrap.css' />" rel="stylesheet">
    <link href="<c:url value='/resources/css/contract-print.css' />" rel="stylesheet" media="screen">
    <link href="<c:url value='/resources/css/contract-print.css' />" rel="stylesheet" media="print">
    
    <script src="<c:url value="/resources/js/libs/jquery.js" />"></script>
    <script src="<c:url value="/resources/js/libs/html2canvas.js" />"></script>
    
	<script type="text/javascript">
		/*
		$(document).ready(function(){
			html2canvas(document.getElementsByClassName("content")[0], {
				onrendered : function(canvas){
					document.getElementsByClassName("print")[0].appendChild(canvas);
					$("body > .content").remove();
				}
			});
		});
		*/
	</script>
  </head>

  <body>
    <div class="content"> <!-- Content -->
      <div class="header"> <!-- Header -->
        <div class="leftSection"> <!-- Left section -->
          <div>
            <img class="logo" src="<c:url value='/resources/img/contract/logo-wevan-transp-medium-cropped.png' />">
          </div>
          <div class="title">
            <span class="capital">C</span><span>ONTRAT DE </span><span class="capital">L</span><span>OCATION</span>
          </div>
          <div>
            <span>N&ordm;</span>
            <span class="contractId">${contract.id}</span>
          </div>
        </div> <!-- Left section -->
        <div class="rightSection"><!-- Right section -->
          <div> <!-- Agence --> 
            <div class="pull-left yourAgency"><em>Votre agence</em></div>
            <div class="contactInfo agency">
              <div class="name title">${contract.branch.name}</div>
              <div>${contract.branch.phone}</div>
              <div>
              <c:if test="${contract.branch.addressNumber != '' }">
              	<span>${contract.branch.addressNumber},</span>
              </c:if>
              	<span>${contract.branch.addressStreet}</span>
              </div>
              <div>${contract.branch.postalCode} ${contract.branch.city}, ${contract.branch.country}</div>
            </div>
          </div> <!-- Agence --> 

          <div> <!-- Assistance --> 
            <div class="pull-left"><em>Votre assistance</em></div>
            <div class="contactInfo">
              <div>AXA assistance</div>
              <div>+33 (0) 1 55 92 14 54</div>
            </div>
          </div> <!-- Assistance --> 
        </div>
        <div class="clearfix"></div>
      </div> <!-- Header -->

      <div class="driversInfo"> <!-- Drivers info -->
        <div class="leftSection">
		  <div class="title">Le locataire &amp; les conducteurs</div>
          <div class="subtitle">Locataire (et premier conducteur)</div>
          <div>
            <table>
              <tbody>
                <tr>
                  <td>Raison sociale :</td>
                  <td class="input value">${contract.driver.corporateName}</td>
                </tr>
                <tr>
                  <td>Nom &amp; prénom :</td>
                  <td class="input value">${contract.driver.lastName} ${contract.driver.firstName}</td>
                </tr>
                <tr>
                  <td>Permis de conduire n&ordm; :</td>
                  <td class="input value">${contract.driver.driverLicense}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <div class="rightSection">
          <div class="subtitle">Conducteurs supplémentaires</div>
          <div>
            <table>
              <thead>
                <tr>
                  <th class="name">Nom &amp; prénoms</th>
                  <th class="license">Permis de conduire n&ordm;</th>
                </tr>
              </thead>
              <tbody class="value">
                <c:forEach items="${contract.additionalDrivers}" var="driver">
                  <tr>
                    <td class="name">${driver.lastName} ${driver.firstName}</td>
                    <td class="license">${driver.driverLicense}</td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
        <div class="clearfix"></div>
        <div class="spacer">&nbsp;</div>
      </div> <!-- Drivers info -->

      <div class="locationInfo"> <!-- Location -->
        <div class="dates"> <!-- Location Dates -->
          <div class="title">La location</div>
          <div class="content">
            <table>
              <tr>
                <td class="from">Location du</td>
                <td class="date value"><fmt:formatDate pattern="dd/MM/yyyy" value="${contract.startDate}" /></td>
                <td class="at">à</td>
                <td class="time value"><fmt:formatDate pattern="HH:mm" value="${contract.startDate}" /></td>
              </tr>
              <tr>
                <td class="text-right to">au</td>
                <td class="date value"><fmt:formatDate pattern="dd/MM/yyyy" value="${contract.endDate}" /></td>
                <td class="at">à</td>
                <td class="time value"><fmt:formatDate pattern="HH:mm" value="${contract.endDate}" /></td>
              </tr>
            </table>
          </div>
          <div>
            <span>Forfait kilométrique :</span>
            <span class="value">${contract.kilometersPackage}</span>
          </div>
        </div> <!-- Location Dates -->

        <div class="vehicule"> <!-- Vehicule -->
          <div class="title">&nbsp;</div>
          <table>
            <tbody>
              <tr>
                <td>Le véhicule :</td>
                <td class="value">${contract.vehicule.name} ${contract.vehicule.number}</td>
              </tr>
              <tr>
                <td>Modèle :</td>
                <td class="value">${contract.vehicule.model}</td>
              </tr>
              <tr>
                <td>Immatriculation :</td>
                <td class="value">${contract.vehicule.registration}</td>
              </tr>
            </tbody>
          </table>
        </div> <!-- Vehicule -->
        
        <div class="clearfix"></div>
      </div> <!-- Location -->

      <div class="options"> <!-- Options -->
        <div class="title">Les options</div>
        <div class="content value">
          <c:forEach items="${contract.options}" var="option">
          	<c:if test="${option.active}">
          	  <div>
          	  	<span>${option.label}</span>
				<c:if test="${contract.showOptionsPrices}">
          	  		<span> - ${option.amount} €</span>
          	  	</c:if>
          	  </div>
          	</c:if>
          </c:forEach>
        </div>
      </div>

      <div class="invoice"> <!-- Invoice -->
        <div class="title">La facturation</div>
        <table>
          <tbody>
          	<tr>
          		<td>Somme déjà versée :</td>
              	<td class="value"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${contract.amountAlreadyPaid}" /> &euro; TTC</td>
          	</td>
            <tr>
              <td>Montant total :</td>
              <td class="value"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${contract.totalAmount}" /> &euro; TTC</td>
            </tr>
            <tr>
              <td>Franchise :</td>
              <td class="value"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${contract.deductible}" /> &euro; TTC</td>
            </tr>
            <tr>
              <td>Dépôt de garantie :</td>
              <td class="value"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${contract.deposit}" /> &euro;</td>
            </tr>
          </tbody>
        </table>
      </div> <!-- Invoice -->
      
      <div class="clearfix"></div>
      
      <div class="conditions"> <!-- Conditions -->
        <ul>
          <li><img src="<c:url value='/resources/img/contract/V-s.png' />" />&nbsp;Les kilomètres supplémentaires sont facturés 0,3 &euro; / km</li>
          <li><img src="<c:url value='/resources/img/contract/V-s.png' />" />&nbsp;Le gazole est à la charge du client ; le défaut de gazole est facturé 2 &euro; / l</li>
          <li><img src="<c:url value='/resources/img/contract/V-s.png' />" />&nbsp;Les départs et retours se font à l'adresse de votre agence mentionnée sur le présent contrat</li>
          <li><img src="<c:url value='/resources/img/contract/V-s.png' />" />&nbsp;Les conducteurs doivent avoir 21 ans révolus et être détenteurs d'un permis B valide depuis plus de 24 mois</li>
        </ul>
      </div> <!-- Conditions -->

      <div class="endorsement"> <!-- Endorsement -->
        <div class="spacer">&nbsp;</div>

        <div class="statement">
          <div class="title">Par l'apposition de sa signature, le locataire :</div>
          <div>
            <ul>
              <li><img src="<c:url value='/resources/img/contract/checkmark.png' />" />&nbsp;<span class="bolder">Certifie</span> que les informations mentionnées ci-dessus sont exactes.</li>
              <li><img src="<c:url value='/resources/img/contract/checkmark.png' />" />&nbsp;<span class="bolder">Autorise</span> le loueur à recouvrer tout ou partie du montant de la franchise par débit de la caution.</li>
              <li><img src="<c:url value='/resources/img/contract/checkmark.png' />" />&nbsp;<span class="bolder">Reconnait</span> avoir pris connaissance des conditions stipulées ci-dessus ainsi que des conditions générales de location consultables en agence et sur <a href="http://www.we-van.com">www.we-van.com</a> , et de s'y conformer en tout point. Il reconnait également avoir pris connaissance et possession du document « État du Véhicule » joint à ce contrat.</li>
            </ul>
          </div>
        </div>

        <div class="signature">
          <div class="title">A ${contract.branch.city}, le <fmt:formatDate pattern="dd/MM/yyyy" value="${contract.startDate}" /></div>
          <div class="instructions">(Signature précédée de la mention « lu et approuvé »)</div>
        </div>
        <div class="clearfix"></div>
        <div class="spacer">&nbsp;</div>
      </div> <!-- Endorsement -->
      
      <div class="legal"> <!-- Legal notice -->
        ${contract.branch.companyType} immatriculée au ${contract.branch.registrationLocation} le <fmt:formatDate pattern="dd/MM/yyyy" value="${contract.branch.registrationDate}" /> sous le n°${contract.branch.registration} (SIRET : ${contract.branch.siret}) et dont le siège social se situe au ${contract.branch.headOffice}
      </div> <!-- Legal notice -->
    </div> <!-- Content -->
    
    <div class="print">
    
    </div>
  </body>
</html>