<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.Data" %>  <!-- 替换为你的Data类的实际包路径 -->
<!DOCTYPE html>
<html>
<head>
    <title>Data List</title>
</head>
<body>
    <h1>Data List</h1>
    <%-- 检查dataList是否存在并且包含数据 --%>
    <%
        List<Data> dataList = (List<Data>) request.getAttribute("dataList");
        if (dataList != null && dataList.size() > 0) {
            for (Data data : dataList) {
    %>
        <p>ID: <%= data.getId() %> - Name: <%= data.getName() %></p>
    <%
            }
        } else {
    %>
        <p>No data found.</p>
    <%
        }
    %>
</body>
</html>
