<%-- 
    Document   : categoria
    Created on : 30/05/2018, 10:52:06
    Author     : Jaque
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <select name="pergunta1" class="frmCmb1">
            <option value="">..</option>
            <c:forEach var="artilheiros" items="${artilheiros}">
                <option value="${artilheiros.codigoArtilheiro}">
                    ${artilheiros.nomeArtilheiro}
                </option>
            </c:forEach>
        </select>
    </body>
</html>
