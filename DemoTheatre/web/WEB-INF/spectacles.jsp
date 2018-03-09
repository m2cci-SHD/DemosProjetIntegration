<%-- 
    Document   : index
    Author     : Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
--%>
<%-- 
    Document   : erreurachat
    Affiche dans une liste déroulante une liste de spectacles. L'utilisateur peut en
    sélectionner un parmi ceux-ci et par le bouton 'Acheter' aller sur une page qui
    lui permettra de chosir et d'acheter des places pour ce spectacle.
    Author     : Philippe Genoud - LIG Steamer - Université Grenoble Alpes
--%>
<%@page import="java.util.List"%>
<%@page import="im2ag.m2pcci.theatre.model.Spectacle"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Accueil</title>
    </head>
    <body>
        <div class='container'>
            <h1>Choisissez un spectacle</h1>
            <form action='spectacle'>
                <select name="nospectacle">
                    <%
                        List<Spectacle> listeSpectacles = (List<Spectacle>) request.getAttribute("spectacles");
                        for (Spectacle spect : listeSpectacles) {%>
                    <option value='<%=spect.getId()%>'><%=spect.getTitre()%></option>
                    <%  }%>
                </select>
                <input type="submit" value="Acheter" />
            </form>
        </div>
    </body>
</html>
