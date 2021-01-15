<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.javaex.dao.PhoneDao" %>
<%@ page import="com.javaex.vo.PersonVo" %>

<%
	/*********수정 전********
	해당 personId를 가져와서 해당 정보를 불러오기 위해 새로 메모리를 올리고 또 쿼리를 사용하여 불러왔었음
	
	---->여기부터
	int personId = Integer.parseInt(request.getParameter("id"));
	System.out.println(personId);	
	
	PhoneDao phoneDao = new PhoneDao();
	PersonVo personVo = phoneDao.getPerson(personId);	
	여기까지 controller에서 해결함
	
	*/
	
	PersonVo updatePerson = (PersonVo)request.getAttribute("pList");
	System.out.println("=====list.jsp======");
	System.out.println(updatePerson);
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/phonebook2/pbc" method="post">
		이름(name):<input type="text" name="name" value="<%=updatePerson.getName()%>"><br>
		핸드폰(hp):<input type="text" name="hp" value="<%=updatePerson.getHp()%>"><br>
		회사(company):<input type="text" name="company" value="<%=updatePerson.getCompany()%>"><br>
		
		<!-- 아래 두 영역은 히든 처리로 노출하지 않는 부분 test 때만 text로 두고 확인한다 -->
		id: <input type="text" name="id" value="<%=updatePerson.getPersonId() %>">		
		action: <input type="text" name="action" value="update"><br>
		<button type="submit">등록</button>
	</form>

	
</body>
</html>