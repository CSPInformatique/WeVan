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

    <style type="text/css">      
      body{
        color: #3E3E3E;
        font-family: Amaranth;
        font-size: 10pt;
        font-weight: bold;
      }

      table{
        width: 100%
      }

      .content{
        height: 1040;
        margin-left: auto;
        margin-right: auto;
        width: 720px;
      }

      .spacer{
        height: 5px;
      }

      .header .contactInfo{
        text-align: right;
      }

      .header .contactInfo.agency{
        padding-bottom: 20px;
      }

      .header .contactInfo.agency .name{
        font-size: 12pt; 
        font-weight: bold;
      }

      .header .leftSection{
        float: left;
        font-size: 14pt;
        width: 400px;
      }

      .header .rightSection{
        font-size: 9pt;
        font-style: italic;
        font-weight: bold;
        float: right;
        margin-top: 60px;
        width: 240px;
      }
      

      .header .title{
        font-size: 18pt;
      }

      .header .title .capital{
        font-size: 24pt;
      }

      .driversInfo, .locationInfo, .options, .invoice{
        border:solid 1px #2B4A77;
        -webkit-border-top-right-radius: 30px;
        -khtml-border-radius-topright: 30px;  
        -moz-border-radius-topright: 30px;
        border-top-right-radius: 30px;
        margin-top: 20px;
        padding: 5px;
      }

      .driversInfo table{
        font-size: 10pt;
      }

      .driversInfo > div, .locationInfo > div{
        padding-left: 10px;
        padding-right: 10px;
        width: 353px;
      }

      .driversInfo .leftSection, .locationInfo .dates{
        border-right: solid 1px #2B4A77;
        float: left;
      }

      .driversInfo .leftSection table td{
        margin-bottom: 20px;
      }

      .driversInfo .leftSection .input{
        font-weight: bold;
      }

      .driversInfo .rightSection, .locationInfo .vehicule{
        float: right;
      }

      .driversInfo .rightSection table .license{
        white-space: nowrap;
      }

      .driversInfo .rightSection table .name{
        width: 100%;
      }

      .driversInfo .rightSection .subtitle{
        text-align: right;
        margin-bottom: 10px;        
      }
      
      .driversInfo .rightSection tbody{
        font-size: 9pt;
        font-weight: bold;
      }

      .driversInfo .title, .locationInfo .title, .vehicule .title, .options .title, .invoice .title{
        color: #548ED4;
        font-size: 12pt;
        font-weight: bold;
        margin-bottom: 10px;
      }

      .driversInfo .subtitle{
        font-size: 11pt;
        font-weight: bold;
      }
      
      .legal{
        font-size: 8pt;
        font-weight: bold;
        margin-left: 5px;
        margin-top: 10px;
        margin-right: 5px;
        text-align: center;
      }

      .dates{
        font-size: 10pt;
      }

      .dates .subtitle{
        font-size: 11pt;
        font-weight: bold;
        margin-bottom: 10px;
      }
      
      .dates, .vehicule{
        height: 110px;
      }

      .dates table .from, .dates table .to{
        width: 80px;
      }

      .dates table .date{
        width: 80px;
        text-align: right;
      }

      .dates table .at{
        width: 40px;
        text-align: center;
      }

      .dates table .tile{
        text-align: center;
      }

      .options, .invoice {
        width: 350px;
      }

      .options{
        float:left;
      }

      .invoice, .vehicule{
        float: right;
      }
      
      .invoice, .options{
        height: 150px;
      }
      
      .invoice, .options .content{
        margin-left: 10px;
      }

      .conditions{
        font-size: 10pt;
        list-style-position: inside;
        list-style-image: url("<c:url value='/resources/img/contract/arrow.png' />");
        margin-top: 20px;
        margin-bottom: 20px;
        text-align: center;
      }

      .conditions ul{
        margin: 0px;
        padding: 0px;
      }

      .endorsement{
        border-bottom:solid 1px #2B4A77;
        border-top:solid 1px #2B4A77;
      }

      .endorsement ul{
        font-size: 8pt;
        font-weight: normal;
        margin: 0px;
        padding: 0px;
        text-align: justify;
      }

      .endorsement li{
        margin-top: 10px;
      }

      .endorsement .statement, .endorsement .signature{
        padding-left: 15px;
        padding-right: 15px;
        width: 360px;
      }

      .endorsement .statement{
        float: left;
        list-style-position: inside;
        list-style-image: url("<c:url value='/resources/img/contract/checkmark.png' />");
        border-right:solid 1px #2B4A77;
      }

      .endorsement .statement .title{
        font-size: 10pt;
        font-weight: bold;
      }

      .endorsement .statement .bolder{
        font-size: 9pt;
        font-weight: bolder;
      }

      .endorsement .signature{
        float: right;
        padding-left: 30px;
        padding-top: 5px;
      }

      .endorsement .signature .title{
        font-weight: bold;
      }

      .endorsement .signature .instructions{
        margin-top: 125px;
        font-size: 8pt;
      }
    </style>
  </head>

  <body>
    <div class="content"> <!-- Content -->
      <div class="header"> <!-- Header -->
        <div class="leftSection"> <!-- Left section -->
          <div>
            <img src="<c:url value='/resources/img/contract/wevan.png' />">
          </div>
          <div class="title">
            <span class="capital">C</span><span>ONTRAT DE </span><span class="capital">L</span><span>OCATION</span>
          </div>
          <div>
            <span>N&ordm;</span>
            <span>${contract.id}</span>
          </div>
        </div> <!-- Left section -->
        <div class="rightSection"><!-- Right section -->
          <div> <!-- Agence --> 
            <div class="pull-left">Votre agence</div>
            <div class="contactInfo agency">
              <div class="name">${contract.branch.name}</div>
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
            <div class="pull-left">Votre assistance</div>
            <div class="contactInfo">
              <div>AXA assistance</div>
              <div>+33 (0) 1 55 92 14 54</div>
            </div>
          </div> <!-- Assistance --> 
        </div>
        <div class="clearfix"></div>
      </div> <!-- Header -->

      <div class="driversInfo"> <!-- Drivers info -->
        <div class="spacer">&nbsp;</div>
        <div class="leftSection">
          <div class="title">Le locataire &amp; les conducteurs</div>
          <div class="subtitle">Locataire (et premier conducteur)</div>
          <div>
            <table>
              <tbody>
                <tr>
                  <td>Raison sociale :</td>
                  <td class="input">${contract.driver.corporateName}</td>
                </tr>
                <tr>
                  <td>Nom &amp; prénom :</td>
                  <td class="input">${contract.driver.lastName} ${contract.driver.firstName}</td>
                </tr>
                <tr>
                  <td>Permis de conduire n&ordm; :</td>
                  <td class="input">${contract.driver.driverLicense}</td>
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
              <tbody>
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
          <div class="subtitle">
            <table>
              <tr>
                <td class="text-right from">Location du</td>
                <td class="date"><fmt:formatDate pattern="dd/MM/yyyy" value="${contract.startDate}" /></td>
                <td class="at">à</td>
                <td class="time"><fmt:formatDate pattern="HH:mm" value="${contract.startDate}" /></td>
              </tr>
              <tr>
                <td class="text-right to">au</td>
                <td class="date"><fmt:formatDate pattern="dd/MM/yyyy" value="${contract.endDate}" /></td>
                <td class="at">à</td>
                <td class="time"><fmt:formatDate pattern="HH:mm" value="${contract.endDate}" /></td>
              </tr>
            </table>
          </div>
          <div>
            <span>Forfait kilométrique (km) :</span>
            <span>${contract.kilometers}</span>
            <span>km</span>
          </div>
        </div> <!-- Location Dates -->

        <div class="vehicule"> <!-- Vehicule -->
          <div class="title">&nbsp;</div>
          <table>
            <tbody>
              <tr>
                <td>Le véhicule :</td>
                <td>${contract.vehicule.name} ${contract.vehicule.number}</td>
              </tr>
              <tr>
                <td>Modèle :</td>
                <td>${contract.vehicule.model}</td>
              </tr>
              <tr>
                <td>Immatriculation :</td>
                <td>${contract.vehicule.registration}</td>
              </tr>
            </tbody>
          </table>
        </div> <!-- Vehicule -->
        
        <div class="clearfix"></div>
      </div> <!-- Location -->

      <div class="options"> <!-- Options -->
        <div class="title">Les options</div>
        <div class="content">
          ${fn:replace(contract.options, newLineChar, "<br/>")}
        </div>
      </div>

      <div class="invoice"> <!-- Invoice -->
        <div class="title">La facturation</div>
        <table>
          <tbody>
            <tr>
              <td>Montant total :</td>
              <td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${contract.totalAmount}" /> &euro; TTC</td>
            </tr>
            <tr>
              <td>Franchise :</td>
              <td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${contract.deductible}" /> &euro; TTC</td>
            </tr>
            <tr>
              <td>Dépôt de garantie :</td>
              <td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${contract.deposit}" /> &euro;</td>
            </tr>
          </tbody>
        </table>
      </div> <!-- Invoice -->
      
      <div class="clearfix"></div>
      
      <div class="conditions"> <!-- Conditions -->
        <ul>
          <li>Les kilomètres supplémentaires sont facturés 0,3 &euro; / km</li>
          <li>Le gazole est à la charge du client ; le défaut de gazole est facturé 2 &euro; / l</li>
          <li>Les départs et retours se font à l'adresse de votre agence mentionnée sur le présent contrat</li>
          <li>Les conducteurs doivent avoir 21 ans révolus et être détenteurs d'un permis B valide depuis plus de 24 mois</li>
        </ul>
      </div> <!-- Conditions -->

      <div class="endorsement"> <!-- Endorsement -->
        <div class="spacer">&nbsp;</div>

        <div class="statement">
          <div class="title">Par l'apposition de sa signature, le locataire :</div>
          <div>
            <ul>
              <li><span class="bolder">Certifie</span> que les informations mentionnées ci-dessus sont exactes.</li>
              <li><span class="bolder">Autorise</span> le loueur à recouvrer tout ou partie du montant de la franchise par débit de la caution.</li>
              <li><span class="bolder">Reconnait</span> avoir pris connaissance des conditions stipulées ci-dessus ainsi que des conditions générales de location consultables en agence et sur <a href="http://www.we-van.com">www.we-van.com</a> , et de s'y conformer en tout point. Il reconnait également avoir pris connaissance et possession du document « État du Véhicule » joint à ce contrat.</li>
            </ul>
          </div>
        </div>

        <div class="signature">
          <div class="title">A ${contract.branch.city}, le <fmt:formatDate pattern="dd/MM/yyyy" value="${date}" /></div>
          <div class="instructions">(Signature précédée de la mention « lu et approuvé »)</div>
        </div>
        <div class="clearfix"></div>
        <div class="spacer">&nbsp;</div>
      </div> <!-- Endorsement -->
      
      <div class="legal"> <!-- Legal notice -->
        ${contract.branch.companyType} immatriculée au ${contract.branch.registrationLocation} le <fmt:formatDate pattern="dd/MM/yyyy" value="${contract.branch.registrationDate}" /> sous le n°${contract.branch.registration} (SIRET : ${contract.branch.siret}) et dont le siège social se situe au ${contract.branch.headOffice}
      </div> <!-- Legal notice -->
    </div> <!-- Content -->
  </body>
</html>