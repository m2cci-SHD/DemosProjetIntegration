<%-- 
    Document   : achatPlaces
    Author     : Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>JSC Demo</title>
        <meta charset="UTF-8">
        <link href='http://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
        <link href="js/jQuery-Seat-Charts/jquery.seat-charts.css" rel="stylesheet" type="text/css"/>
        <link href="css/styleTheatre.css" rel="stylesheet" type="text/css"/>
    </head>

    <body>
        <div class="wrapper">
            <h1>
                Spectacle ${spectacle.titre}
            </h1>
            <div id="map-container">
                <div id="seat-map">
                    <div class="front-indicator">Scène</div>
                </div>
                <div id="commande">
                    <div id="legend"></div>
                    <h3>Votre sélection</h3>
                    <p>Nbre de places: <strong><span id="nbplaces">0</span></strong></p>
                    <p>Prix Total: <strong><span id="prixtotal">0</span> €</strong></p>
                    <button id="achatBtn" disabled>Acheter</button>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script src="js/jQuery-Seat-Charts/jquery.seat-charts.min.js" type="text/javascript"></script>
        <script src="js/achatPlaces.js" type="text/javascript"></script>
    </body>
