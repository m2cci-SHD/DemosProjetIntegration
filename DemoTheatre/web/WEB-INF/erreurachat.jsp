<%-- 
    Document   : erreurachat
    Page d'erreur affichée lorsque l'utilisateur n'a pu effectuer son achat 
    parceque certaines des places qu'il voulait acheter viennent d'être vendues 
    (la méthode acheterPlace de la DAO PlaceDAO a levé une exception de type 
     AchatConcurrentException).
    Author     : Philippe Genoud - LIG Steamer - Université Grenoble Alpes
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
        <div>
            <ul>
                <li>
                    <p><a href="spectacle?numerospectacle=${param.numerospectacle}">Recommencez votre achat</a></p>
                </li>
                <li>
                    <p>Abandonner et <a href="/DemoTheatre">retourner à l'accueil</a></p>
                </li>
            </ul>
        </div>
    </body>
</html>
