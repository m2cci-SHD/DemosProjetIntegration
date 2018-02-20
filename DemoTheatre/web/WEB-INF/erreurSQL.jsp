<%-- 
    Document   : erreurSQL
    Page d'erreur  afffcihé lorsqu'un problème avec la BD est intervenu
    Author     : genoud
--%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CyberTheatre</title>
    </head>
    <body>
        <h1>Erreur du serveur CyberTheatre</h1>
        <p>Un problème est survenu avec la base de données</p>
        <p>Réessayez plus tard ou contactez l'administrateur de l'application</p>
        <p>
            Message:
<%=exception.getMessage()%>
StackTrace:
<%
	StringWriter stringWriter = new StringWriter();
	PrintWriter printWriter = new PrintWriter(stringWriter);
	exception.printStackTrace(printWriter);
	out.println(stringWriter);
	printWriter.close();
	stringWriter.close();
%>
        </p>
    </body>
</html>
