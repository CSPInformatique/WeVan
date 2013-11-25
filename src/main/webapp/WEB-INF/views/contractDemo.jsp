<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="Content-type" content="text/html;charset=UTF-8" />
    
    <link href="resources/lib/bootstrap/css/bootstrap.css" rel="stylesheet">

    <style type="text/css">
      body{
        color: #3E3E3E;
        font-family: Amaranth;
        font-size: 10pt;
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

      .header .leftSection{
        float: left;
        font-size: 14pt;
        width: 300px;
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
        font-size: 19pt;
      }

      .header .title .capital{
        font-size: 24pt;
      }

      .driversInfo, .locationInfo, .vehicule, .invoice{
        border:solid 1px #2B4A77;
        -moz-border-radius-topright: 15px;
        -webkit-border-radius-topright: 15px;
        border-radius-topright: 15px;
        margin-top: 20px;
        padding: 5px;
      }

      .driversInfo table{
        font-size: 10pt;
      }

      .driversInfo > div{
        padding-left: 10px;
        padding-right: 10px;
        width: 353px;
      }

      .driversInfo .leftSection{
        float: left;
      }

      .driversInfo .leftSection table td{
        margin-bottom: 20px;
      }

      .driversInfo .leftSection .input{
        font-weight: bold;
      }

      .driversInfo .rightSection{
        border-left: solid 1px #2B4A77;
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
      }

      .driversInfo .rightSection tbody{
        font-size: 9pt;
        font-weight: bold;
      }

      .driversInfo .title, .locationInfo .title, .vehicule .title, .invoice .title{
        color: #548ED4;
        font-size: 12pt;
        font-weight: bold;
      }

      .driversInfo .subtitle{
        font-size: 11pt;
        font-weight: bold;
      }

      .locationInfo{
        font-size: 10pt;
      }

      .locationInfo .subtitle{
        font-size: 11pt;
        font-weight: bold;
        margin-bottom: 10px;
      }

      .vehicule, .invoice{
        width: 350px;
      }

      .vehicule{
        float:left;
      }

      .invoice{
        float: right;
      }

      .conditions{
        font-size: 11pt;
        list-style-position: inside;
        list-style-image: url("resources/img/contract/arrow.png");
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
        margin: 0px;
        padding: 0px;
        text-align: justify;
      }

      .endorsement li{
        margin-top: 10px;
      }

      .endorsement .statement, .endorsement .signature{
        padding-left: 7px;
        padding-right: 25px;
        width: 360px;
      }

      .endorsement .statement{
        float: left;
        list-style-position: inside;
        list-style-image: url("resources/img/contract/checkmark.png");
        border-right:solid 1px #2B4A77;
      }

      .endorsement .statement .title{
        font-size: 9pt;
        font-weight: bold;
      }

      .endorsement .statement .bold{
        font-weight: bold;
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
            <img src="resources/img/contract/wevan.png">
          </div>
          <div class="title">
            <span class="capital">C</span><span>ONTRAT DE </span><span class="capital">L</span><span>OCATION</span>
          </div>
          <div>
            <span>N&ordm;</span>
            <span>121123007</span>
          </div>
        </div> <!-- Left section -->
        <div class="rightSection"><!-- Right section -->
          <div> <!-- Agence --> 
            <div class="pull-left">Votre agence</div>
            <div class="contactInfo agency">
              <div>+33 (0) 1 47 35 69 96</div>
              <div>33, Avenue Léon Gambetta</div>
              <div>92120 Montrouge, France</div>
            </div>
          </div> <!-- Agence --> 

          <div> <!-- Assistance --> 
            <div class="pull-left">Votre assistance</div>
            <div class="contactInfo">
              <div>+33 (0) 1 55 92 14 54</div>
              <div>N&ordm; d'adhésion :</div>
              <div>FLT2000058 - 500375302</div>
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
                  <td class="input">Elliot Filmus</td>
                </tr>
                <tr>
                  <td>Nom &amp; prénom :</td>
                  <td class="input">Jean Jean gui</td>
                </tr>
                <tr>
                  <td>Permis de conduire n&ordm; :</td>
                  <td class="input">01324048399</td>
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
                <tr>
                  <td class="name">Boby Jones</td>
                  <td class="license">38392201231</td>
                </tr>
                <tr>
                  <td class="name">Gérard Boucher</td>
                  <td class="license">74820102312</td>
                </tr>
                <tr>
                  <td class="name">Armand Dubois</td>
                  <td class="license">12301239123</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="clearfix"></div>
        <div class="spacer">&nbsp;</div>
      </div> <!-- Drivers info -->

      <div class="locationInfo"> <!-- Location -->
        <div class="title">La location</div>
        <div class="subtitle">
          <span>Location du </span>
          <span>24/11/2013</span>
          <span>à</span>
          <span>12:30</span>
          <span>au</span>
          <span>24/11/2013</span>
          <span>à</span>
          <span>12:30</span>
        </div>
        <div>
          <span>Forfait kilométrique :</span>
          <span>3 000</span>
          <span>km</span>
        </div>
      </div>  <!-- Location -->

      <div class="vehicule"> <!-- Vehicule -->
        <div class="title">Le véhicule</div>
        <table>
          <tbody>
            <tr>
              <td>Appellation We-Van :</td>
              <td>Bessie 1</td>
            </tr>
            <tr>
              <td>Modèle :</td>
              <td>Transporter T5 Volkswagen</td>
            </tr>
            <tr>
              <td>Immatriculation :</td>
              <td>CH-113-JT</td>
            </tr>
          </tbody>
        </table>
      </div> <!-- Vehicule -->

      <div class="invoice"> <!-- Invoice -->
        <div class="title">La facturation</div>
        <table>
          <tbody>
            <tr>
              <td>Montant total :</td>
              <td>1 233,00 &euro; TTC</td>
            </tr>
            <tr>
              <td>Franchise :</td>
              <td>Rachat partiel (450 &euro; TTC)</td>
            </tr>
            <tr>
              <td>Dépôt de garantie :</td>
              <td>450 &euro;</td>
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
              <li><span class="bold">Certifie</span> que les informations mentionnées ci-dessus sont exactes.</li>
              <li><span class="bold">Autorise</span> le loueur à recouvrer tout ou partie du montant de la franchise par débit de la caution.</li>
              <li><span class="bold">Reconnait</span> avoir pris connaissance des conditions stipulées ci-dessus ainsi que des conditions générales de location consultables en agence et sur <a href="http://www.we-van.com">www.we-van.com</a> , et de s'y conformer en tout point. Il reconnait également avoir pris connaissance et possession du document « État du Véhicule » joint à ce contrat.</li>
            </ul>
          </div>
        </div>

        <div class="signature">
          <div class="title">A Montrouge, le 23/12/2013</div>
          <div class="instructions">(Signature précédée de la mention « lu et approuvé »)</div>
        </div>
        <div class="clearfix"></div>
        <div class="spacer">&nbsp;</div>
      </div> <!-- Endorsement -->
    </div> <!-- Content -->
  </body>
</html>