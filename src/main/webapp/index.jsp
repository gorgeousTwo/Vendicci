<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="webjars/bootstrap/4.6.0/css/bootstrap.css">

    <title>Index</title>
</head>
<body>
<%
    boolean result = true;

    Connection conn = null;
    Context initCtx = new InitialContext();
    Context envCtx = (Context) initCtx.lookup("java:comp/env");
    DataSource ds = (DataSource) envCtx.lookup("jdbc/oracle");

    try {
        conn = ds.getConnection();
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }


    if (conn == null) {
        result = false;
    }
%>

<%=result%>

<script type="text/javascript" src="webjars/jquery/3.5.1/dist/jquery.js"></script>
<script type="text/javascript" src="webjars/bootstrap/4.6.0/js/bootstrap.js"></script>
<script type="text/javascript"></script>

</body>
</html>