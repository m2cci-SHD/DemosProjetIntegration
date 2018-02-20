<%-- 
    Document   : erreurachat
    Page d'erreur affichée lorsque l'utilisateur n'a pu effectuer son achat 
    parceque certaines des places qu'il voulait acheter viennent d'être vendues 
    (la méthode acheterPlace de la DAO PlaceDAO a levé une exception de type 
     AchatConcurrentEXception).
    Author     : genoud
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CyberTheatre</title>
    </head>
    <body>
        <h1>Votre achat a échoué</h1>
        <p>Certaines des places que vous désiriez acheter viennent d'être vendues.</p>
        <p><a href="spectacle?numerospectacle=${param.numerospectacle}">Recommencez votre achat</a></p>
    </body>
</html>
