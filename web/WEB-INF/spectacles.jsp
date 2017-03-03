<%-- 
    Document   : index
    Created on : 2 mars 2017, 16:58:55
    Author     : Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
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

    </body>
</html>
